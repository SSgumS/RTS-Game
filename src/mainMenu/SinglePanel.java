package mainMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import Addresses.Addresses;
import GameEvent.Events;
import GameFrame.Frame;
import MapEditor.HUD.MiniMap.MiniMap;
import MapEditor.MenuBar.Menu.FileChooser;

public class SinglePanel extends ForPanel implements ActionListener {

	private MyButton findmap, play, back;
	private MiniMap minimap;

	public SinglePanel() {
		super();

		findmap = new MyButton("Find Map",wid,high);
	    findmap.setLocation(Frame.width/8, Frame.height/2 - 3*high - high/2);
        findmap.addActionListener(this);
        findmap.setOpaque(false);
        add(findmap);

        play = new MyButton("Play!",wid,high);
        play.setLocation(findmap.getX() + wid, findmap.getY());
        play.addActionListener(this);
        play.setOpaque(false);

        minimap = new MiniMap(null, true);

        back = new MyButton("Back",wid,high);
	    back.setLocation(Frame.width/8, findmap.getY() + 3*2*high);
        back.addActionListener(this);
        back.setOpaque(false);
        add(back);

		Addresses.panel = this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

        if (source.equals(findmap)) {
            FileChooser fileChooser = new FileChooser("resources\\maps\\saves");
            fileChooser.addActionListener(this);
            fileChooser.showOpenDialog(this);
        } else if(source.equals(back)) {
            Addresses.frame.getContentPane().add(new ButPanel());
            Addresses.frame.getContentPane().remove(this);
            Addresses.frame.repaint();
        } else if (source instanceof FileChooser && e.getActionCommand().equals(FileChooser.APPROVE_SELECTION)) {
            JLabel label = new JLabel("This map needs " + Addresses.board.getPlayers().length + " player!");
            label.setSize(wid,high);
            label.setLocation(Frame.width/8, findmap.getY() + high);
            label.setOpaque(false);
//    		label.setForeground(Color.RED);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 15));
            add(label);

            add(play);

            minimap.setLocation(back.getX() + wid/2 - minimap.getWidth()/2, back.getY() - (back.getY() - label.getY() - high)/2 - minimap.getHeight()/2);
            add(minimap);

            Addresses.frame.repaint();
        }
	}

	@Override
	protected void processComponentEvent(ComponentEvent e) {
		super.processComponentEvent(e);

		if(e.getID() == Events.load)
			minimap.dispatchEvent(e);
	}

}
