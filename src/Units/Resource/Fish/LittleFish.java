package Units.Resource.Fish;

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
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class LittleFish extends Resource {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = 260;
    private static int staticOriginalYHint = 209;
    public static int staticXHint = 260;
    public static int staticYHint = 209;

    static {
        File[] files = new File("resources\\images\\fish\\little").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LittleFish(GameCell cell, Player owner) {
        super(cell, owner, 1);

        originalColor = new Color(255, 50, 55);
        color = originalColor;

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.Dessert, Terrain.Grass, Terrain.Ice, Terrain.Snow));

        originalXHint = 260;
        originalYHint = 209;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);
    }

    @Override
    public BufferedImage getEditorImage(Season season) {
        return images[7];
    }

    public static BufferedImage getStaticEditorImage(Season season) {
        return images[7];
    }

    public static void setStaticHints() {
        staticXHint = (int) (Addresses.board.zoom*staticOriginalXHint);
        staticYHint = (int) (Addresses.board.zoom*staticOriginalYHint);
    }

    @Override
    public BufferedImage getImage() {
        if (Addresses.board.season != Season.Winter) {
            BufferedImage image = images[imageNumber/2];
            imageNumber++;
            if (imageNumber/2 == 49)
                imageNumber = 0;
            return image;
        }

        return null;
    }

}
