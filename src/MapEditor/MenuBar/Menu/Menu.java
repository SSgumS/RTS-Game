package MapEditor.MenuBar.Menu;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.MainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Saeed on 5/22/2017.
 */
public class Menu extends JPanel implements ActionListener {

    private JButton quit = new JButton("Quit to Main Menu");
    private JButton save = new JButton("Save");
    private JButton load = new JButton("Load");
    private JButton cancel = new JButton("Cancel");

    public Menu(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setSize(MainFrame.width/3, MainFrame.height/2);

        addQuit();
        addSave();
        addLoad();
        addCancel();

//        repaint();
    }

    private void addQuit() {
        quit.setSize(getWidth() - 100, getHeight()/5 - 50);
        quit.setLocation(50, 25);
        quit.addActionListener(this);
        add(quit);
    }

    private void addSave() {
        save.setSize(getWidth() - 100, getHeight()/5 - 50);
        save.setLocation(50, quit.getY() + quit.getHeight() + 50);
        save.addActionListener(this);
        add(save);
    }

    private void addLoad() {
        load.setSize(getWidth() - 100, getHeight()/5 - 50);
        load.setLocation(50, save.getY() + save.getHeight() + 50);
        load.addActionListener(this);
        add(load);
    }

    private void addCancel() {
        cancel.setSize(getWidth() - 100, getHeight()/5 - 50);
        cancel.setLocation(50, load.getY() + load.getHeight() + getHeight()/5 + 50);
        cancel.addActionListener(this);
        add(cancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(quit)) {
            System.exit(0);
        } else if (source.equals(save)) {
            FileChooser fileChooser = new FileChooser("resources\\maps\\saves");
            fileChooser.showSaveDialog(this);
        } else if (source.equals(load)) {
            FileChooser fileChooser = new FileChooser("resources\\maps\\saves");
            fileChooser.showOpenDialog(this);
        } else if (source.equals(cancel)) {
            Addresses.panel.dispatchEvent(new GameEvent(cancel, Events.actionOff));
        }
    }
}
