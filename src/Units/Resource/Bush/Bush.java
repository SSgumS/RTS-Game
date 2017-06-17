package Units.Resource.Bush;

import Addresses.Addresses;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Resource.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class Bush extends Resource {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = (int) -4.5;
    private static int staticOriginalYHint = 15;
    public static int staticXHint = (int) -4.5;
    public static int staticYHint = 15;

    static {
        File[] files = new File("resources\\images\\bush\\editor").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bush(GameCell cell, Player owner) {
        super(cell, owner, 1);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = (int) -4.5;
        originalYHint = 15;

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
