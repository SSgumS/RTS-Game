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

    private BufferedImage menuBar;
    private BufferedImage hud;

    public BackPanel(LayoutManager layout) {
        super(layout);

//        try {
//            image = ImageIO.read(new File("resources\\images\\ui\\background\\editor\\background.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            menuBar = ImageIO.read(new File("resources\\images\\ui\\menu bar\\map editor\\menu bar.png"));
            hud = ImageIO.read(new File("resources\\images\\ui\\HUD\\HUD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(menuBar, 0, 0, null);
        g.drawImage(hud, 0, MainFrame.height - hud.getHeight(), null);
    }

}
