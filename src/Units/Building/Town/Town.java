package Units.Building.Town;

import Addresses.Addresses;
import GameEvent.Events;
import GameEvent.GameEvent;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Building.Building;

import javax.imageio.ImageIO;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class Town extends Building {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = 143;
    private static int staticOriginalYHint = 351;
    public static int staticXHint = 143;
    public static int staticYHint = 351;
    private static BufferedImage[] construction;
    private static BufferedImage[] destroyed;

    static {
        File[] files = new File("resources\\images\\town\\town").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\unfinished building\\large").listFiles();
        construction = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                construction[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\destroyed building\\very large").listFiles();
        destroyed = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                destroyed[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Town(GameCell cell, Player owner) {
        super(cell, owner, 4);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = -13;
        originalYHint = 4;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);

        constructionSpeed = 5;
    }

    @Override
    protected void setHints() {
        originalXHint = -1;
        originalYHint = 207;

        super.setHints();
    }

    @Override
    public BufferedImage getEditorImage(Season season) {
        if (!hintExecuted) {
            originalXHint = -1;
            originalYHint = 207;

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

        originalXHint = -22;
        originalYHint = -2;
        zoom();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        switch (e.getID()) {
            case Events.setKind:
            case Events.setKindStack:
                if (owner.getCapital() == null)
                    owner.setCapital(this);
                else
                    return;
                break;
            case Events.clearKind:
                owner.setCapital(null);
                break;
            case Events.createUnit:
                synchronized (this) {
                    queued++;
                    queuedUnits.addElement("Worker");
                }
                if (!thread.isAlive())
                    thread.start();
                break;
        }

        super.processComponentEvent(e);
    }

}
