package Units.Resource.Tree;

import Addresses.Addresses;
import GameEvent.Events;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Resource.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ComponentEvent;
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
    private static BufferedImage[] brokenImages;
    private static int staticOriginalXHint = 0;
    private static int staticOriginalYHint = 135;
    public static int staticXHint = 0;
    public static int staticYHint = 135;
    private static int originalXHintSpring = 0;
    private static int originalXHintSummer = -7;
    private static int originalXHintAutumn = -5;
    private static int originalXHintWinter = (int) -9.5;
    private static int originalYHintSpring = 135;
    private static int originalYHintSummer = 150;
    private static int originalYHintAutumn = 147;
    private static int originalYHintWinter = 115;

    static {
        File[] files = new File("resources\\images\\tree\\tree").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\tree\\broken").listFiles();
        brokenImages = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                brokenImages[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tree(GameCell cell, Player owner) {
        super(cell, owner, 1);

        originalColor = new Color(0, 100, 0);
        color = originalColor;

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

    public BufferedImage getImage() {
        if (health == healthCapacity)
            return images[imageNumber];
        else
            return brokenImages[0];
    }

    @Override
    protected void use() {
        super.use();

        originalXHint = -2;
        originalYHint = -6;
        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.seasonChanged:
                if (health == healthCapacity)
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
                break;
        }
    }

}
