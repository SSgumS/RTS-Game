package Units.Human.Boat;

import Addresses.Addresses;
import GameEvent.*;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Building.Building;
import Units.Building.Seaport.Seaport;
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
public class Boat extends Human {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = -11;
    private static int staticOriginalYHint = 45;
    public static int staticXHint = -11;
    public static int staticYHint = 45;
    private static BufferedImage[][][][] originalImageTable = new BufferedImage[3][7][8][1];
    private transient BufferedImage[][][][] imageTable = new BufferedImage[3][7][8][1];
    private static BufferedImage[] fade = new BufferedImage[5];
    private int barCapacity = 30;
    private int bar = 0;

    static {
        File[] files = new File("resources\\images\\boat\\editor").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\boat\\boat").listFiles();
        try {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 8; j++)
                    originalImageTable[i][6][j][0] = ImageIO.read(files[j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\boat\\die").listFiles();
        try {
            for (int j = 0; j < 5; j++)
                fade[j] = ImageIO.read(files[j]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boat(GameCell cell, Player owner) {
        super(cell, owner, 1);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.Ice, Terrain.Snow, Terrain.Grass, Terrain.Dessert));

        originalXHint = -11;
        originalYHint = 45;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);

        for (int i = 0; i < 3; i++)
            for (int k = 0; k < 8; k++)
                imageTable[i][6][k][0] = originalImageTable[i][6][k][0];

        job = 6;

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
            return image;
        } else {
            BufferedImage image = fade[imageNumber/5];
            imageNumber++;
            if (imageNumber/5 == 5) {
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

                if (unit instanceof Resource) {
                    workPlaceI = targetX;
                    workPlaceJ = targetY;

                    job = 6;
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

    private void fishing(int I,int J) {
        Units unit=Addresses.board.cells[I][J].getKind();

        if(bar<barCapacity) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Addresses.board.season != Season.Winter) {
                unit.dispatchEvent(new GameEvent(this, Events.use));
                bar+=10;
            }
        } else if(bar==barCapacity) {
            if(unit instanceof Resource) {
                workPlaceI=targetX;
                workPlaceJ=targetY;
                findNearUnit(endI,endJ);
            } else if(unit instanceof Seaport) {
                owner.dispatchEvent(new GameEvent(this, Events.food));
                bar=0;
                targetX=workPlaceI;
                targetY=workPlaceJ;
                if(findWay())
                    state=1;
                else
                    state=2;
            }
        }
    }

    private void findNearUnit(int I,int J) {
        Vector<Units> units = new Vector<>();
        for (Units u :Units.getUnits())
            if(u instanceof Seaport && u.getOwner()==this.getOwner())
                units.add(u);
        double temp=Math.hypot(units.get(0).getOriginalOriginX() - (originalOriginX + board.originalWidth/2),units.get(0).getOriginalOriginY()- (originalOriginY + board.originalHeight/2));
        Units unit=units.get(0);
        for (Units u :units)
            if(temp>Math.hypot(u.getOriginalOriginX() - (originalOriginX + board.originalWidth/2),u.getOriginalOriginY()- (originalOriginY + board.originalHeight/2))) {
                targetX=(int) (u.getOriginalOriginX()+u.getUnitSize()*board.originalWidth/2);
                targetY=(int) (u.getOriginalOriginY()+u.getUnitSize()*board.originalHeight);
                if(findWay()) {
                    System.out.println("found!!");
                    unit=u;
                    temp=Math.hypot(u.getOriginalOriginX() - (originalOriginX + board.originalWidth/2),u.getOriginalOriginY()- (originalOriginY + board.originalHeight/2));
                }
            }
        targetX=(int) (unit.getOriginalOriginX()+unit.getUnitSize()*board.originalWidth/2);
        targetY=(int) (unit.getOriginalOriginY()+unit.getUnitSize()*board.originalHeight-1);
        if(findWay())
            state=1;
        else
            state=2;
    }

    @Override
    protected void work() {
        fishing(endI, endJ);
    }

    @Override
    protected void walk() {
        if (Addresses.board.season != Season.Winter)
            super.walk();
    }

    @Override
    protected void death() {
        super.death();

        originalXHint = -9;
        originalYHint = -20;
        zoom();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setUnitImages:
                imageTable = new BufferedImage[3][7][8][1];
                for (int i = 0; i < 3; i++)
                        for (int k = 0; k < 8; k++)
                                imageTable[i][6][k][0] = originalImageTable[i][6][k][0];
                break;
        }
    }

}
