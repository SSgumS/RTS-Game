package mainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Addresses.Addresses;
import GameFrame.Frame;
import MapEditor.MainFrame.MainFrame;

public class ButPanel extends ForPanel implements ActionListener{

	private MyButton exit, splayer, mplayer, mapeditor;
	
	public ButPanel() {
        super();

        splayer=new MyButton("Single Player",wid,high);
        splayer.setLocation(Frame.width/8, Frame.height/2 - 3*high - high/2);
		splayer.addActionListener(this);
        splayer.setOpaque(false);
		add(splayer);

        mplayer=new MyButton("MultiPlayer",wid,high);
        mplayer.setLocation(Frame.width/8, splayer.getY() + 2*high);
		mplayer.addActionListener(this);
        mplayer.setOpaque(false);
		add(mplayer);

        mapeditor=new MyButton("Map Editor",wid,high);
        mapeditor.setLocation(Frame.width/8, mplayer.getY() + 2*high);
		mapeditor.addActionListener(this);
        mapeditor.setOpaque(false);
		add(mapeditor);

        exit=new MyButton("Exit",wid,high);
        exit.setLocation(Frame.width/8, mapeditor.getY() + 2*high);
        exit.addActionListener(this);
        exit.setOpaque(false);
        add(exit);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
	    JButton source = (JButton) e.getSource();

        if (source.equals(exit))
            System.exit(0);
        else if(source.equals(mapeditor)) {
            new MainFrame();
            Addresses.frame.revalidate();
        } else if(source.equals(splayer)) {
            Addresses.frame.getContentPane().add(new SinglePanel());
            Addresses.frame.getContentPane().remove(this);
            Addresses.frame.repaint();
        }
    }
	
}
