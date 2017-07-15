package MapEditor.MenuBar.Menu;

import Addresses.Addresses;
import Button.RegularButton;
import GameEvent.Events;
import GameEvent.GameEvent;
import Player.Player;
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
    private RegularButton save = new RegularButton("Save");
    private RegularButton load = new RegularButton("Load");
    private RegularButton cancel = new RegularButton("Cancel");
    private JLabel label = new JLabel("All players must have a capital.");

    private Timer clearCapitalLabel = new Timer(1000, e -> {
        remove(label);
        repaint();
    });

    public Menu(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setSize(GameFrame.Frame.width/3, GameFrame.Frame.height/2);

        addQuit();
        addSave();
        addLoad();
        addCancel();

        label.setSize(getWidth()/2, getHeight()/12);
        label.setLocation(getWidth()/2 - label.getWidth()/2, save.getY() - label.getHeight());
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        label.setOpaque(false);
        clearCapitalLabel.setRepeats(false);
//        repaint();
    }

    private void addQuit() {
        quit.setSize(getWidth() - 100, getHeight()/5 - getHeight()/12);
        quit.setLocation(50, getHeight()/24);
        quit.addActionListener(this);
        add(quit);
    }

    private void addSave() {
        save.setSize(getWidth() - 100, getHeight()/5 - getHeight()/12);
        save.setLocation(50, quit.getY() + quit.getHeight() + getHeight()/12);
        save.addActionListener(this);
        add(save);
    }

    private void addLoad() {
        load.setSize(getWidth() - 100, getHeight()/5 - getHeight()/12);
        load.setLocation(50, save.getY() + save.getHeight() + getHeight()/12);
        load.addActionListener(this);
        add(load);
    }

    private void addCancel() {
        cancel.setSize(getWidth() - 100, getHeight()/5 - getHeight()/12);
        cancel.setLocation(50, load.getY() + load.getHeight() + getHeight()/5 + getHeight()/12);
        cancel.addActionListener(this);
        add(cancel);
    }

    private boolean hasAllCapital() {
        for (Player player : Addresses.board.getPlayers())
            if (player.getCapital() == null)
                return false;

        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(quit)) {
            Addresses.frame.setContentPane(new BackPanel(null));
            Addresses.frame.getContentPane().add(new ButPanel());
            Addresses.panel.dispatchEvent(new GameEvent(quit, Events.actionOff));
            Addresses.frame.revalidate();
        } else if (source.equals(save)) {
            if (hasAllCapital()) {
                FileChooser fileChooser = new FileChooser("resources\\maps\\saves");
                fileChooser.showSaveDialog(this);
            } else {
                add(label, 0);
                repaint();
                clearCapitalLabel.start();
            }
        } else if (source.equals(load)) {
            FileChooser fileChooser = new FileChooser("resources\\maps\\saves");
            fileChooser.showOpenDialog(this);
        } else if (source.equals(cancel)) {
            Addresses.panel.dispatchEvent(new GameEvent(cancel, Events.actionOff));
        }
    }
}
