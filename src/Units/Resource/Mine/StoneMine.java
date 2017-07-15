package Units.Resource.Mine;

import Addresses.Addresses;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Resource.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class StoneMine extends Resource {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = -6;
    private static int staticOriginalYHint = 2;
    public static int staticXHint = -6;
    public static int staticYHint = 2;

    static {
        File[] files = new File("resources\\images\\stone mine").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StoneMine(GameCell cell, Player owner) {
        super(cell, owner, 1);

        originalColor = new Color(165, 165, 165);
        color = originalColor;

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = -6;
        originalYHint = 2;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);

        imageNumber = new Random().nextInt(4);
    }

    @Override
    public BufferedImage getEditorImage(Season season) {
        return images[imageNumber];
    }

    public static BufferedImage getStaticEditorImage(Season season) {
        return images[0];
    }

    public static void setStaticHints() {
        staticXHint = (int) (Addresses.board.zoom*staticOriginalXHint);
        staticYHint = (int) (Addresses.board.zoom*staticOriginalYHint);
    }

    @Override
    public BufferedImage getImage() {
        return images[imageNumber];
    }

}
