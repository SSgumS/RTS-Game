package Units.Human;

import Addresses.Addresses;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class Worker extends Human {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = -34;
    private static int staticOriginalYHint = -1;
    public static int staticXHint = -34;
    public static int staticYHint = -1;

    static {
        File[] files = new File("resources\\images\\worker\\editor").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Worker(GameCell cell, Player owner) {
        super(cell, owner, 1);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = -34;
        originalYHint = -1;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);
    }

    @Override
    public BufferedImage getEditorImage(Season season) {
        return images[0];
    }

    public static BufferedImage getStaticEditorImage(Season season) {
        return images[0];
    }

    public static void setStaticHints() {
        staticXHint = (int) (Addresses.board.zoom*staticOriginalXHint);
        staticYHint = (int) (Addresses.board.zoom*staticOriginalYHint);
    }

}
