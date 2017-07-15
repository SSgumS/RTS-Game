package Game.MenuBar.Menu;

import Addresses.Addresses;
import Button.RegularButton;
import GameEvent.Events;
import GameEvent.GameEvent;
import mainMenu.BackPanel;
import mainMenu.MenuPanels.Main.ButPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Saeed on 5/22/2017.
 */
public class Menu extends JPanel implements ActionListener {

    private RegularButton quit = new RegularButton("Quit to Main Menu");
    private RegularButton cancel = new RegularButton("Cancel");

    public Menu(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setSize(GameFrame.Frame.width/3, GameFrame.Frame.height/2);

        addQuit();
        addCancel();
    }

    private void addQuit() {
        quit.setSize(getWidth() - 100, getHeight()/5 - getHeight()/12);
        quit.setLocation(50, getHeight()/24);
        quit.addActionListener(this);
        add(quit);
    }

    private void addCancel() {
        cancel.setSize(getWidth() - 100, getHeight()/5 - getHeight()/12);
        cancel.setLocation(50, quit.getY() + 3*quit.getHeight() + getHeight()/5 + 3*getHeight()/12);
        cancel.addActionListener(this);
        add(cancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(quit)) {
            Addresses.frame.setContentPane(new BackPanel(null));
            Addresses.frame.getContentPane().add(new ButPanel());
            Addresses.panel.dispatchEvent(new GameEvent(cancel, Events.actionOff));
            Addresses.frame.revalidate();
        } else if (source.equals(cancel)) {
            Addresses.panel.dispatchEvent(new GameEvent(cancel, Events.actionOff));
        }
    }

}
