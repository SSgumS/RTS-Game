package MapEditor.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/20/2017.
 */
public class BackPanel extends JPanel {

    private BufferedImage image;

    public BackPanel(LayoutManager layout) {
        super(layout);

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\background\\background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);
    }
}
