package MapEditor.MenuBar;

import MapEditor.MainFrame.MainFrame;

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
    private JButton menuButton;

    public MenuBar(LayoutManager layout) {
        super(layout);

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\menu bar\\map editor\\menu bar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(image.getWidth(), image.getHeight());
        setLocation(0, 0);

        addMenuButton();
    }

    private void addMenuButton() {
        menuButton = new JButton("Menu");
        menuButton.setSize(100, getHeight() - 10);
        menuButton.setLocation(getWidth() - 10 - menuButton.getWidth(), 5);
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
