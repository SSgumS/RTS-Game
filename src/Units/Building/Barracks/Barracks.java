package Units.Building.Barracks;

import Addresses.Addresses;
import GameEvent.*;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Building.Building;
import Units.Human.Soldier.Soldier;
import Units.Units;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Saeed on 7/10/2017.
 */
public class Barracks extends Building {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = 111;
    private static int staticOriginalYHint = 211;
    public static int staticXHint = 111;
    public static int staticYHint = 211;
    private static BufferedImage[] construction;
    private static BufferedImage[] destroyed;

    static {
        File[] files = new File("resources\\images\\barracks").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\unfinished building\\medium").listFiles();
        construction = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                construction[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\destroyed building\\large").listFiles();
        destroyed = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                destroyed[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Barracks(GameCell cell, Player owner) {
        super(cell, owner, 3);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = -8;
        originalYHint = 11;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);

        constructionSpeed = 10;
    }

    @Override
    protected void setHints() {
        originalXHint = 15;
        originalYHint = 115;

        super.setHints();
    }

    @Override
    public BufferedImage getEditorImage(Season season) {
        if (!hintExecuted) {
            originalXHint = 15;
            originalYHint = 115;

            xHint = (int) (Addresses.board.zoom*originalXHint);
            yHint = (int) (Addresses.board.zoom*originalYHint);

            hintExecuted = true;
        }

        return images[0];
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
        if (!constructed) {
            if (health > 66)
                return construction[2];
            else if (health > 33)
                return construction[1];
            else
                return construction[0];
        } else if (alive)
            return images[0];
        else {
            BufferedImage image = destroyed[0];
            imageNumber++;
            if (imageNumber/2 == 10)
                dispatchEvent(new GameEvent(this, Events.clearKind));
            return image;
        }
    }

    @Override
    protected void death() {
        super.death();

        originalXHint = -11;
        originalYHint = -6;
        zoom();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.createUnit:
                synchronized (this) {
                    queued++;
                    queuedUnits.addElement("Soldier");
                }
                if (!thread.isAlive())
                    thread.start();
                break;
        }
    }
}
