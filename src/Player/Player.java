package Player;

import GameEvent.Events;
import GameEvent.SetKindEvent;
import MapEditor.Map.Cell.UndoRedoCell;
import Units.Building.Town;
import Units.Units;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Saeed on 5/30/2017.
 */
public class Player extends JComponent implements Serializable {

    private Color color;
    private Vector <Units> units = new Vector<>();
    private Units capital;
    private int playerNumber;

    public Player(Color color, int playerNumber) {
        this.color = color;
        this.playerNumber = playerNumber;
    }

    public void addUnit(Units unit) {
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

    public void clearUnit(Units unit) {
        units.removeElement(unit);
    }

    public Vector<Units> getUnits() {
        return units;
    }

    public Color getColor() {
        return color;
    }

    public Units getCapital() {
        return capital;
    }

    public void setCapital(Units capital) {
        this.capital = capital;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    private void clearKind(Polygon shape, UndoRedoCell cell) {
        Iterator<Units> iterator = units.iterator();
        while (iterator.hasNext()) {
            Units unit = iterator.next();
            Area area = new Area(shape);
            area.intersect(unit.getArea());
            if (!area.isEmpty()) {
                //-- Iterator used to avoid ConcurrentModificationException. Foreach loop creates a iterator and when a iterator has created, when we modify the collection it will throw the exception.
                iterator.remove();
                Units.getUnits().removeElement(unit);
                if (unit instanceof Town)
                    setCapital(null);
                cell.setUnit(unit);
            }
        }
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.clearKind:
                clearKind(((SetKindEvent) e).getCell().getShape(), ((SetKindEvent) e).getUndoRedoCell());
                break;
        }
    }

    public boolean isAllowed(Polygon shape) {
        for (Units unit : units) {
            Area area = new Area(shape);
            area.intersect(unit.getArea());
            if (!area.isEmpty())
                return false;
        }

        return true;
    }

}
