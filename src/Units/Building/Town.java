package Units.Building;

import Addresses.Addresses;
import GameEvent.Events;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;

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

    static {
        File[] files = new File("resources\\images\\town\\town").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Town(GameCell cell, Player owner) {
        super(cell, owner, 4);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = 143;
        originalYHint = 351;

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

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        switch (e.getID()) {
            case Events.setKind:
                if (owner.getCapital() == null)
                    owner.setCapital(this);
                else
                    return;
                break;
            case Events.clearKind:
                owner.setCapital(null);
                break;
        }

        super.processComponentEvent(e);
    }
}
