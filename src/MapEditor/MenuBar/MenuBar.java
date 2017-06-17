package MapEditor.MenuBar;

import Addresses.Addresses;
import MapEditor.Button.Button;
import GameEvent.Events;
import GameEvent.GameEvent;
import MapEditor.MenuBar.EditorButtons.EditorButtons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MenuBar extends JPanel implements ActionListener {

    private BufferedImage image;
    private EditorButtons editorButtons;
    private Button menuButton;

    public MenuBar(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\menu bar\\map editor\\menu bar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(image.getWidth(), image.getHeight());

        addEditorButtons();
        addMenuButton();
    }

    private void addEditorButtons() {
        editorButtons = new EditorButtons(null);
        editorButtons.setSize(getWidth()/2, getHeight());
        editorButtons.setLocation(0, 0);
        add(editorButtons);
    }

    private void addMenuButton() {
        menuButton = new Button("Menu");
        menuButton.setLocation(getWidth() - 10 - menuButton.getWidth(), 3);
        menuButton.addActionListener(this);
        add(menuButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Addresses.panel.dispatchEvent(new GameEvent(menuButton, Events.actionOn));
    }

}
