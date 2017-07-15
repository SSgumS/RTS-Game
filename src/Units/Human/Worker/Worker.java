package Units.Human.Worker;

import Addresses.Addresses;
import GameEvent.*;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Building.Building;
import Units.Building.LumberCamp.LumberCamp;
import Units.Building.MiningCamp.MiningCamp;
import Units.Building.Town.Town;
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
 * Created by Saeed on 6/5/2017.
 */
public class Worker extends Human {

    private static BufferedImage[] images;
    private static int staticOriginalXHint = -34;
    private static int staticOriginalYHint = 13;
    public static int staticXHint = -34;
    public static int staticYHint = 13;
    private static BufferedImage[][][][] originalImageTable = new BufferedImage[3][6][8][15];
    private transient BufferedImage[][][][] imageTable = new BufferedImage[3][6][8][15];
    private static BufferedImage[][] mineGo = new BufferedImage[8][15];
    private static BufferedImage[][] goldReturn = new BufferedImage[8][15];
    private static BufferedImage[][] stoneReturn = new BufferedImage[8][15];
    private static BufferedImage[][] woodGo = new BufferedImage[8][15];
    private static BufferedImage[][] woodReturn = new BufferedImage[8][15];
    private static BufferedImage[][] die = new BufferedImage[8][15];
    private static BufferedImage[][] fade = new BufferedImage[8][5];
    private int barCapacity = 30;
    private int bar = 0;

    static {
        File[] files = new File("resources\\images\\worker\\editor").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\stand\\war").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                        originalImageTable[2][0][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\stand\\wood").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[2][1][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\stand\\mine").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++) {
                    originalImageTable[2][2][i][j] = ImageIO.read(files[i * 15 + j]);
                    originalImageTable[2][3][i][j] = ImageIO.read(files[i * 15 + j]);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\stand\\food").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[2][4][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\stand\\build").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[2][5][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\attack").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[0][0][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\cut").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[0][1][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\mine").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++) {
                    originalImageTable[0][2][i][j] = ImageIO.read(files[i*15 + j]);
                    originalImageTable[0][3][i][j] = ImageIO.read(files[i*15 + j]);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\pick").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[0][4][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\build").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[0][5][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\war").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[1][0][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\wood\\return").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++) {
                    originalImageTable[1][1][i][j] = ImageIO.read(files[i*15 + j]);
                    woodReturn[i][j] = originalImageTable[1][1][i][j];
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\wood\\go").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    woodGo[i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\gold\\return").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++) {
                    originalImageTable[1][2][i][j] = ImageIO.read(files[i*15 + j]);
                    goldReturn[i][j] = originalImageTable[1][2][i][j];
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\stone\\return").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++) {
                    originalImageTable[1][3][i][j] = ImageIO.read(files[i*15 + j]);
                    stoneReturn[i][j] = originalImageTable[1][3][i][j];
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\gold\\go").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    mineGo[i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\fruit").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[1][4][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\walk\\build").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    originalImageTable[1][5][i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\die").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 15; j++)
                    die[i][j] = ImageIO.read(files[i*15 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        files = new File("resources\\images\\worker\\fade").listFiles();
        try {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 5; j++)
                    fade[i][j] = ImageIO.read(files[i*5 + j]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Worker(GameCell cell, Player owner) {
        super(cell, owner, 1);

        abandonTerrains = new Vector<>(Arrays.asList(Terrain.DeepWater, Terrain.Ice, Terrain.Water));

        originalXHint = -34;
        originalYHint = 13;

        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 6; j++)
                for (int k = 0; k < 8; k++)
                    for (int l = 0; l < 15; l++)
                        imageTable[i][j][k][l] = originalImageTable[i][j][k][l];

        statsPanel = new GameHUD.StatsSection.Human.Worker.Worker(null, this);
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
            if (imageNumber/2 == 15)
                imageNumber = 0;
            return image;
        } else if (!fadeFlag) {
            BufferedImage image = die[direction][imageNumber/2];
            imageNumber++;
            if (imageNumber/2 == 15) {
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
                } else if (unit instanceof Resource) {
                    workPlaceI = targetX;
                    workPlaceJ = targetY;

                    if (unit instanceof Tree) {
                        for (int k = 0; k < 8; k++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][1][k][j] = woodGo[k][j];

                        job = 1;
                    } else if (unit instanceof GoldMine) {
                        for (int k = 0; k < 8; k++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][2][k][j] = mineGo[k][j];

                        job = 2;
                    } else if (unit instanceof StoneMine) {
                        for (int k = 0; k < 8; k++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][3][k][j] = mineGo[k][j];

                        job = 3;
                    } else if (unit instanceof Bush)
                        job = 4;
                } else if (unit instanceof Building)
                    job = 5;
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

    private void lumber(int I,int J) {
        Units unit = Addresses.board.cells[I][J].getKind();

        if(bar<barCapacity) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            unit.dispatchEvent(new GameEvent(this, Events.use));
            bar+=10;
        } else if(bar==barCapacity) {
            if(unit instanceof Resource) {
                switch (job) {
                    case 1:
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][1][i][j] = woodReturn[i][j];
                        break;
                    case 2:
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][2][i][j] = goldReturn[i][j];
                        break;
                    case 3:
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][3][i][j] = stoneReturn[i][j];
                        break;
                }
                workPlaceI=targetX;
                workPlaceJ=targetY;
                findNearUnit();
            } else if(unit instanceof Town) {
                switch (job) {
                    case 1:
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][1][i][j] = woodGo[i][j];

                        owner.dispatchEvent(new GameEvent(this, Events.wood));
                        break;
                    case 2:
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][2][i][j] = mineGo[i][j];

                        owner.dispatchEvent(new GameEvent(this, Events.gold));
                        break;
                    case 3:
                        for (int i = 0; i < 8; i++)
                            for (int j = 0; j < 15; j++)
                                imageTable[1][3][i][j] = mineGo[i][j];

                        owner.dispatchEvent(new GameEvent(this, Events.stone));
                        break;
                    case 4:
                        owner.dispatchEvent(new GameEvent(this, Events.food));
                        break;
                }
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

    private void findNearUnit() {
        Vector<Units> units = new Vector<>();

        for (Units u : owner.getUnits()) {
            switch (job) {
                case 1:
                    if(u instanceof Town || u instanceof LumberCamp)
                        units.add(u);
                    break;
                case 2:
                    if(u instanceof Town || u instanceof MiningCamp)
                        units.add(u);
                    break;
                case 3:
                    if(u instanceof Town || u instanceof MiningCamp)
                        units.add(u);
                    break;
                case 4:
                    if(u instanceof Town)
                        units.add(u);
                    break;
                case 5:
                    if (u instanceof Building && u.getHealth() < u.getHealthCapacity())
                        units.add(u);
                    break;
            }
        }

        try {
            double temp = Math.hypot(units.get(0).getOriginalOriginX() - (originalOriginX + board.originalWidth/2),units.get(0).getOriginalOriginY()- (originalOriginY + board.originalHeight/2));
            Units unit = null;
            for (Units u : units)
                if(temp > Math.hypot(u.getOriginalOriginX() - (originalOriginX + board.originalWidth/2),u.getOriginalOriginY()- (originalOriginY + board.originalHeight/2))) {
                    targetX = (int) (u.getOriginalOriginX()+u.getUnitSize()/2*board.originalWidth);
                    targetY = (int) (u.getOriginalOriginY()+u.getUnitSize()*board.originalHeight);
                    if(findWay()) {
                        unit = u;
                        temp = Math.hypot(u.getOriginalOriginX() - (originalOriginX + board.originalWidth/2),u.getOriginalOriginY()- (originalOriginY + board.originalHeight/2));
                    }
                }
            if (unit != null) {
                targetX = (int) (unit.getOriginalOriginX()+unit.getUnitSize()/2*board.originalWidth);
                targetY = (int) (unit.getOriginalOriginY()+unit.getUnitSize()*board.originalHeight-1);

                state = 1;
            } else
                state = 2;
        } catch (ArrayIndexOutOfBoundsException e) {
            state = 2;
        }
    }

    @Override
    protected void work() {
        lumber(endI, endJ);
    }

    @Override
    protected void build(int I, int J) {
        Building unit = (Building) Addresses.board.cells[I][J].getKind();

        if(unit.getHealth() < unit.getHealthCapacity()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            unit.dispatchEvent(new GameEvent(this, Events.build));
        } else
            findNearUnit();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setUnitImages:
                imageTable = new BufferedImage[3][6][8][15];
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 6; j++)
                        for (int k = 0; k < 8; k++)
                            for (int l = 0; l < 15; l++)
                                imageTable[i][j][k][l] = originalImageTable[i][j][k][l];
                break;
        }
    }
}
