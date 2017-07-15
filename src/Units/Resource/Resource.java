package Units.Resource;

import Addresses.Addresses;
import GameEvent.Events;
import GameEvent.GameEvent;
import Map.GameCell;
import MapEditor.Map.Cell.UndoRedoCell;
import Player.Player;
import Units.Units;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class Resource extends Units {

    protected GameCell cell;
    private static Vector <Resource> resources = new Vector<>();

    public Resource(GameCell cell, Player owner, int size) {
        super(cell, owner, size);

        this.cell = cell;

        statsPanel = new GameHUD.StatsSection.Resource.Resource(null, this);
    }

    private static void addUnit(Resource resource) {
        if (resources.size() != 0) {
            double y = resource.getShape().ypoints[0];
            for (int i = resources.size() - 1; i >= 0; i--) {
                double yy = resources.elementAt(i).getShape().ypoints[0];
                if (y > yy) {
                    resources.insertElementAt(resource, i + 1);
                    break;
                } else if (i == 0)
                    resources.insertElementAt(resource, i);
            }
        } else
            resources.addElement(resource);
    }

    public static Vector<Resource> getResources() {
        return resources;
    }

    public static void setResources(Vector<Resource> resources) {
        Resource.resources = resources;
    }

    public static void clearKind(Polygon shape, UndoRedoCell cell) {
        Iterator<Resource> iterator = resources.iterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            Area area = new Area(shape);
            area.intersect(resource.getArea());
            if (!area.isEmpty()) {
                //-- Iterator used to avoid ConcurrentModificationException. Foreach loop creates a iterator and when a iterator has created, when we modify the collection it will throw the exception.
                iterator.remove();
                Units.getUnits().removeElement(resource);
                cell.setUnit(resource);
            }
        }
    }

    public static boolean isStaticAllowed(Polygon shape) {
        for (Resource resource : resources) {
            Area area = new Area(shape);
            area.intersect(resource.getArea());
            if (!area.isEmpty())
                return false;
        }

        return true;
    }

    protected void use() {
        health-=10;
        if (health == 0)
            dispatchEvent(new GameEvent(this, Events.death));
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setKind:
                Resource.addUnit(this);

                for (int i = size - 1; i >= 0; i--)
                    for (int j = size - 1; j >= 0; j--)
                        Addresses.board.cells[cell.getI() - i][cell.getJ() + j].setKind(this);

                Addresses.undo.push(this);
                Addresses.redo.removeAll();
                break;
            case Events.setKindStack:
                Resource.addUnit(this);

                for (int i = size - 1; i >= 0; i--)
                    for (int j = size - 1; j >= 0; j--)
                        Addresses.board.cells[cell.getI() - i][cell.getJ() + j].setKind(this);
                break;
            case Events.clearKind:
                resources.removeElement(this);

                for (int i = size - 1; i >= 0; i--)
                    for (int j = size - 1; j >= 0; j--)
                        Addresses.board.cells[cell.getI() - i][cell.getJ() + j].setKind(null);
                break;
            case Events.use:
                use();
                break;
        }
    }

    @Override
    protected void death() {
        super.death();

        dispatchEvent(new GameEvent(this, Events.clearKind));
    }

}
