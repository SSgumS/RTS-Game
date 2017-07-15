package Units.Human.Soldier;

import Addresses.Addresses;
import GameEvent.*;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Building.Building;
import Units.Human.Human;
import Units.Resource.Bush.Bush;
import Units.Resource.Mine.GoldMine;
import Units.Resource.Mine.StoneMine;
import Units.Resource.Resource;
import Units.Resource.Tree.Tree;
import Units.Units;

import javax.imageio.ImageIO;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Saeed on 7/10/2017.
 */
public class Soldier extends Human {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = -15;
    private static int staticOriginalYHint = 17;
    public static int staticXHint = -15;
    public static int staticYHint = 17;
    private static BufferedImage[][][][] originalImageTable = new BufferedImage[3][1][8][15];
    private transient BufferedImage[][][][] imageTable = new BufferedImage[3][1][8][15];
    private static BufferedImage[][] die = new BufferedImage[8][10];
    private static BufferedImage[][] fade = new BufferedImage[8][5];

    static {
        File[] files = new File("resources\\images\\soldier\\editor").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\soldier\\fight").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 10; j++)
                    originalImageTable[0][0][i][j] = ImageIO.read(files[i*10 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\soldier\\walk").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[1][0][i][j] = ImageIO.read(files[i*10 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\soldier\\stand").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 10; j++)
                    originalImageTable[2][0][i][j] = ImageIO.read(files[i*10 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\soldier\\die").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 10; j++)
                    die[i][j] = ImageIO.read(files[i*10 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\soldier\\fade").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 5; j++)
                    fade[i][j] = ImageIO.read(files[i*5 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Soldier(GameCell cell, Player owner) {
        super(cell, owner, 1);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = -15;
        originalYHint = 17;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);

        for (int i = 0; i < 3; i++)
                for (int k = 0; k < 8; k++)
                    for (int l = 0; l < 15; l++)
                        imageTable[i][0][k][l] = originalImageTable[i][0][k][l];

        statsPanel = new GameHUD.StatsSection.Human.Human(null, this);
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
    public BufferedImage getImage() {
        if (alive) {
            BufferedImage image = imageTable[state][job][direction][imageNumber/2];
            imageNumber++;
            if (image == null || imageNumber/2 == 15)
                imageNumber = 0;
            return image;
        } else if (!fadeFlag) {
            BufferedImage image = die[direction][imageNumber/2];
            imageNumber++;
            if (imageNumber/2 == 10) {
                imageNumber = 0;
                fadeFlag = true;
            }
            return image;
        } else {
            BufferedImage image = fade[direction][imageNumber/3];
            imageNumber++;
            if (imageNumber/3 == 5) {
                imageNumber = 0;
                dispatchEvent(new GameEvent(this, Events.clearKind));
            }
            return image;
        }
    }

    @Override
    protected void findJob() {
        for(Terrain terrain : abandonTerrains)
            if(terrain == Addresses.board.getSelectedCell().getTerrain()) {
                state = 2;
                return;
            }

        originalTarget = target = Addresses.board.getSelectedCell();
        startX = getOriginalCenterX();
        startY = getOriginalCenterY();
        state = 1;

//        int targetX = (int) ((double) (Addresses.panel.mouseX - Addresses.board.xo)/Addresses.board.zoom);
//        int targetY = (int) ((double) (Addresses.panel.mouseY - Addresses.board.getY() - Addresses.board.yo)/Addresses.board.zoom);
        targetX = (int) ((double) (Addresses.panel.mouseX - Addresses.board.xo)/Addresses.board.zoom);
        targetY = (int) ((double) (Addresses.panel.mouseY - Addresses.board.getY() - Addresses.board.yo)/Addresses.board.zoom);

        for(int i = Units.getUnits().size() - 1; i >= 0; i--) {
            Units unit = Units.getUnits().elementAt(i);

            if(unit.getShape().contains(Addresses.board.zoom*targetX, Addresses.board.zoom*targetY)) {
                originalTarget = target = unit;
//                state = 0;

                if ((unit instanceof Building || unit instanceof Human) && unit.getOwner() != Addresses.board.getCurrentPlayer()) {
                    job = 0;
                }
            }
        }

//        if (target instanceof Units) {
//            endI = findCellI((int) ((Units) target).getOriginalCenterX(), (int) ((Units) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//            endJ = findCellJ((int) ((Units) target).getOriginalCenterX(), (int) ((Units) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//        } else {
//            endI = findCellI((int) ((GameCell) target).getOriginalCenterX(), (int) ((GameCell) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//            endJ = findCellJ((int) ((GameCell) target).getOriginalCenterX(), (int) ((GameCell) target).getOriginalCenterY(), 0, Addresses.board.mapSize);
//        }
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setUnitImages:
                imageTable = new BufferedImage[3][1][8][15];
                for (int i = 0; i < 3; i++)
                        for (int k = 0; k < 8; k++)
                            for (int l = 0; l < 15; l++)
                                imageTable[i][0][k][l] = originalImageTable[i][0][k][l];
                break;
        }
    }

}
