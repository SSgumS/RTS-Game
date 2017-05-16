package MapEditor.MenuBar;

import MapEditor.Button.Button;
import MapEditor.MenuBar.EditorButtons.EditorButtons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MenuBar extends JPanel {

    private BufferedImage image;
    private EditorButtons editorButtons;
    private Button menuButton;

    public MenuBar(LayoutManager layout) {
        super(layout);

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\menu bar\\map editor\\menu bar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(image.getWidth(), image.getHeight());
        setLocation(0, 0);

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
        add(menuButton);
    }

    public BufferedImage getImage() {
        BufferedImage dbImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dbImage.getGraphics();

        g.drawImage(image, 0, 0, null);
        paintComponents(g);

        return dbImage;
    }

}
