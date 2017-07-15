package Units;

import Addresses.Addresses;
import Game.HUD.StatsSection.Panels.SingleSelect;
import GameEvent.Events;
import GameEvent.GameEvent;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Terrain.Terrain;
import Units.Building.Barracks.Barracks;
import Units.Building.LumberCamp.LumberCamp;
import Units.Building.MiningCamp.MiningCamp;
import Units.Building.Seaport.Seaport;
import Units.Building.Town.Town;
import Units.Human.Boat.Boat;
import Units.Human.Ship.Ship;
import Units.Human.Soldier.Soldier;
import Units.Human.Worker.Worker;
import Units.Resource.Bush.Bush;
import Units.Resource.Fish.BigFish;
import Units.Resource.Fish.LittleFish;
import Units.Resource.Mine.GoldMine;
import Units.Resource.Mine.StoneMine;
import Units.Resource.Resource;
import Units.Resource.Tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class Units extends JComponent {

    protected double originalOriginX;
    protected double originalOriginY;
    protected int originX;
    protected int originY;
    protected double[] originalXPoints = new double[4];
    protected double[] originalYPoints = new double[4];
    protected Polygon shape;
    protected int originalXHint;
    protected int originalYHint;
    protected int xHint;
    protected int yHint;
    protected int size;
    protected Color originalColor;
    protected Color color;
    protected Vector <Terrain> abandonTerrains;
    protected int imageNumber = 0;
    protected int direction = 0;
    protected boolean alive = true;
    protected SingleSelect statsPanel;
    protected volatile int health = 100;
    protected int healthCapacity = 100;

    private static Vector<Units> units = new Vector<>();

    public Units(GameCell cell, Player owner, int size) {
        this.size = size;

        int[] originalXs = cell.getOriginalXs();
        int[] originalYs = cell.getOriginalYs();
        originalXPoints[0] = originalXs[0] - (size-1)*Addresses.board.originalWidth/2;
        originalXPoints[1] = originalXs[1];
        originalXPoints[2] = originalXs[2] + (size-1)*Addresses.board.originalWidth/2;
        originalXPoints[3] = originalXs[3];
        originalYPoints[0] = originalYs[0] - (size-1)*Addresses.board.originalHeight/2;
        originalYPoints[1] = originalYs[1] - (size-1)*Addresses.board.originalHeight;
        originalYPoints[2] = originalYs[2] - (size-1)*Addresses.board.originalHeight/2;
        originalYPoints[3] = originalYs[3];

        originalOriginX = originalXPoints[0];
        originalOriginY = originalYPoints[1];
        originX = (int) (Addresses.board.zoom*originalOriginX);
        originY = (int) (Addresses.board.zoom*originalOriginY);

        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        int[] xs = cell.getShape().xpoints;
        int[] ys = cell.getShape().ypoints;
        xPoints[0] = xs[0] - (size-1)*Addresses.board.width/2;
        xPoints[1] = xs[1];
        xPoints[2] = xs[2] + (size-1)*Addresses.board.width/2;
        xPoints[3] = xs[3];
        yPoints[0] = ys[0] - (size-1)*Addresses.board.height/2;
        yPoints[1] = ys[1] - (size-1)*Addresses.board.height;
        yPoints[2] = ys[2] - (size-1)*Addresses.board.height/2;
        yPoints[3] = ys[3];
        shape = new Polygon(xPoints, yPoints, 4);
    }

    public int getX() {
        if (this instanceof Barracks)
            System.out.println(xHint + " " + yHint);

        return originX - xHint;
    }

    public int getY() {
        return originY - yHint;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public double getOriginalOriginX() {
        return originalOriginX;
    }

    public double getOriginalOriginY() {
        return originalOriginY;
    }

    public double getOriginalCenterX() {
        return shape.xpoints[0];
    }

    public double getOriginalCenterY() {
        return shape.ypoints[1];
    }

    public BufferedImage getEditorImage(Season season) {
        return null;
    }

    public BufferedImage getImage() {
        return null;
    }

    public Color getColor() {
        return color;
    }

    public Area getArea() {
        return new Area(shape);
    }

    public Polygon getShape() {
        return shape;
    }

    public boolean isAllowed(int i, int j) {
        try {
            for (int k = 0; k < size; k++)
                for (int l = 0; l < size; l++)
                    if (abandonTerrains.contains(Addresses.board.cells[i - k][j + l].getTerrain()))
                        return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        for (Player player : Addresses.board.getPlayers())
            if (!player.isAllowed(getShape()))
                return false;

        return Resource.isStaticAllowed(getShape());
    }

    public void zoom() {
        double zoom = Addresses.board.zoom;
        xHint = (int) (zoom*originalXHint);
        yHint = (int) (zoom*originalYHint);

        originX = (int) (zoom*originalOriginX);
        originY = (int) (zoom*originalOriginY);

        for (int k = 0; k < 4; k++) {
            shape.xpoints[k] = (int) (zoom*originalXPoints[k]);
            shape.ypoints[k] = (int) (zoom*originalYPoints[k]);
        }
        shape.invalidate();
    }

    public static Units getUnit(String className, GameCell selectedCell, Player owner) {
        switch (className) {
            case "Worker":
                return new Worker(selectedCell, owner);
            case "Soldier":
                return new Soldier(selectedCell, owner);
            case "Boat":
                return new Boat(selectedCell, owner);
            case "Ship":
                return new Ship(selectedCell, owner);
            case "Town":
                return new Town(selectedCell, owner);
            case "Barracks":
                return new Barracks(selectedCell, owner);
            case "LumberCamp":
                return new LumberCamp(selectedCell, owner);
            case "MiningCamp":
                return new MiningCamp(selectedCell, owner);
            case "Seaport":
                return new Seaport(selectedCell, owner);
            case "Bush":
                return new Bush(selectedCell, null);
            case "BigFish":
                return new BigFish(selectedCell, null);
            case "LittleFish":
                return new LittleFish(selectedCell, null);
            case "GoldMine":
                return new GoldMine(selectedCell, null);
            case "StoneMine":
                return new StoneMine(selectedCell, null);
            case "Tree":
                return new Tree(selectedCell, null);
        }

        return null;
    }

    public static synchronized void addUnit(Units unit) {
        if (units.size() != 0) {
            double y = unit.getShape().ypoints[0];
            for (int i = units.size() - 1; i >= 0; i--) {
                double yy = units.elementAt(i).getShape().ypoints[0];
                if (y > yy) {
                    units.insertElementAt(unit, i + 1);
                    break;
                } else if (i == 0)
                    units.insertElementAt(unit, i);
            }
        } else
            units.addElement(unit);
    }

    private static synchronized void removeUnit(Units unit) {
        units.removeElement(unit);
    }

    public static Vector<Units> getUnits() {
        return units;
    }

    public static void setUnits(Vector<Units> units) {
        Units.units = units;
    }

    public Player getOwner() {
        return null;
    }

    protected boolean findWay() {
        return true;
    }

    protected void findJob() {}

    protected int findCellI(int x, int y, int first, int last) {
        if(last - first == 1)
            return first;
        int temp = (first + last)/2;
        if(y <= -x/2 + temp*Addresses.board.originalHeight)
            return findCellI(x, y, first, temp);
        else
            return findCellI(x, y, temp, last);
    }

    protected int findCellJ(int x, int y, int first, int last) {
        if(last - first == 1)
            return first;
        int temp = (first + last)/2;
        if(y <= x/2 - temp*Addresses.board.originalHeight)
            return findCellJ(x, y, temp, last);
        else
            return findCellJ(x, y, first, temp);
    }

    protected void reArrange() {
        removeUnit(this);
        addUnit(this);
    }

    public int getUnitSize() {
        return size;
    }

    public int getHealth() {
        return health;
    }

    public int getHealthCapacity() {
        return healthCapacity;
    }

    public boolean isAlive() {
        return alive;
    }

    protected void death() {
        alive = false;
        imageNumber = 0;

        Addresses.board.dispatchEvent(new GameEvent(this, Events.clearSelection));
    }

    public Vector<Terrain> getAbandonTerrains() {
        return abandonTerrains;
    }

    public SingleSelect getStatsPanel() {
        return statsPanel;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.zoom:
                synchronized (this) {
                    zoom();
                }
                break;
            case Events.setKind:
            case Events.setKindStack:
                Units.addUnit(this);
                break;
            case Events.clearKind:
                Units.units.removeElement(this);
                break;
            case Events.order:
                findJob();
                findWay();
                break;
            case Events.unitSelect:
                color = Color.WHITE;
                break;
            case Events.unitDeselect:
                color = originalColor;
                break;
            case Events.death:
                death();
                break;
        }
    }

}
