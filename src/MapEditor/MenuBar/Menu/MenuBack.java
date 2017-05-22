package MapEditor.MenuBar.Menu;

import MapEditor.MainFrame.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/22/2017.
 */
public class MenuBack extends JPanel {

    private BufferedImage image;

    public MenuBack(LayoutManager layout) {
        super(layout);
        setSize(MainFrame.width/3, MainFrame.height/2);
        setLocation(MainFrame.width/2 - getWidth()/2, MainFrame.height/2 - getHeight()/2);

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\menu\\menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(new Menu(null));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);
    }

}
