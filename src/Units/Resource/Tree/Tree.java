package Units.Resource.Tree;

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
public class Tree extends Resource {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = (int) 5.5;
    private static int staticOriginalYHint = 138;
    public static int staticXHint = (int) 5.5;
    public static int staticYHint = 138;
    private static int originalXHintSpring = (int) 5.5;
    private static int originalXHintSummer = 3;
    private static int originalXHintAutumn = 3;
    private static int originalXHintWinter = (int) -9.5;
    private static int originalYHintSpring = 138;
    private static int originalYHintSummer = 156;
    private static int originalYHintAutumn = 152;
    private static int originalYHintWinter = 118;
    private int imageNumber;

    static {
        File[] files = new File("resources\\images\\tree\\tree").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tree(GameCell cell, Player owner) {
        super(cell, owner, 1);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        switch (Addresses.board.season) {
            case Spring:
                originalXHint = originalXHintSpring;
                originalYHint = originalYHintSpring;
                xHint = (int) (Addresses.board.zoom*originalXHint);
                yHint = (int) (Addresses.board.zoom*originalYHint);
                imageNumber = 0;
                break;
            case Summer:
                originalXHint = originalXHintSummer;
                originalYHint = originalYHintSummer;
                xHint = (int) (Addresses.board.zoom*originalXHint);
                yHint = (int) (Addresses.board.zoom*originalYHint);
                imageNumber = 1;
                break;
            case Autumn:
                originalXHint = originalXHintAutumn;
                originalYHint = originalYHintAutumn;
                xHint = (int) (Addresses.board.zoom*originalXHint);
                yHint = (int) (Addresses.board.zoom*originalYHint);
                imageNumber = 2;
                break;
            case Winter:
                originalXHint = originalXHintWinter;
                originalYHint = originalYHintWinter;
                xHint = (int) (Addresses.board.zoom*originalXHint);
                yHint = (int) (Addresses.board.zoom*originalYHint);
                imageNumber = 3;
                break;
        }
    }

    @Override
    public BufferedImage getEditorImage(Season season) {
        return images[imageNumber];
    }

    public static BufferedImage getStaticEditorImage(Season season) {
        switch (season) {
            case Spring:
                staticOriginalXHint = originalXHintSpring;
                staticOriginalYHint = originalYHintSpring;
                staticXHint = (int) (Addresses.board.zoom*originalXHintSpring);
                staticYHint = (int) (Addresses.board.zoom*originalYHintSpring);
                return images[0];
            case Summer:
                staticOriginalXHint = originalXHintSummer;
                staticOriginalYHint = originalYHintSummer;
                staticXHint = (int) (Addresses.board.zoom*originalXHintSummer);
                staticYHint = (int) (Addresses.board.zoom*originalYHintSummer);
                return images[1];
            case Autumn:
                staticOriginalXHint = originalXHintAutumn;
                staticOriginalYHint = originalYHintAutumn;
                staticXHint = (int) (Addresses.board.zoom*originalXHintAutumn);
                staticYHint = (int) (Addresses.board.zoom*originalYHintAutumn);
                return images[2];
            case Winter:
                staticOriginalXHint = originalXHintWinter;
                staticOriginalYHint = originalYHintWinter;
                staticXHint = (int) (Addresses.board.zoom*originalXHintWinter);
                staticYHint = (int) (Addresses.board.zoom*originalYHintWinter);
                return images[3];
        }

        return images[0];
    }

    public static void setStaticHints() {
        staticXHint = (int) (Addresses.board.zoom*staticOriginalXHint);
        staticYHint = (int) (Addresses.board.zoom*staticOriginalYHint);
    }

}
