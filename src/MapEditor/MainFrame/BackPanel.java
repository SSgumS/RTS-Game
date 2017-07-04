package MapEditor.MainFrame;

import GameFrame.Back;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/20/2017.
 */
public class BackPanel extends Back {

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

}
