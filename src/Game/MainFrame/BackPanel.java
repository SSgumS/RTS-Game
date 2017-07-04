package Game.MainFrame;

import GameFrame.Back;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 6/22/2017.
 */
public class BackPanel extends Back {

    public BackPanel(LayoutManager layout) {
        super(layout);

        try {
            menuBar = ImageIO.read(new File("resources\\images\\ui\\menu bar\\game\\menu bar.png"));
            hud = ImageIO.read(new File("resources\\images\\ui\\HUD\\HUD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
