package MapEditor.HUD;

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
public class HUD extends JPanel {

    private BufferedImage image;

    public HUD(LayoutManager layout) {
        super(layout);

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\HUD\\HUD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(image.getWidth(), image.getHeight());
        setLocation(0, MainFrame.height - getHeight());
    }

    public BufferedImage getImage() {
        BufferedImage dbImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dbImage.getGraphics();

        g.drawImage(image, 0, 0, null);

        return dbImage;
    }

}
