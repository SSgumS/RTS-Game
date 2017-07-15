package Units.Building;

import Addresses.Addresses;
import GameEvent.Events;
import GameEvent.GameEvent;
import Map.GameCell;
import Player.Player;
import Units.Human.Human;
import Units.Units;

import java.awt.event.ComponentEvent;
import java.util.Vector;

/**
 * Created by Saeed on 6/5/2017.
 */
public class Building extends Units implements Runnable {

    protected Player owner;
    protected GameCell cell;
    protected volatile int queued;
    protected transient Thread thread = new Thread(this);
    protected Vector <String> queuedUnits = new Vector<>();
    protected int constructionSpeed;
    protected boolean constructed = false;
    protected boolean hintExecuted = false;

    public Building(GameCell cell, Player owner, int size) {
        super(cell, owner, size);

        this.owner = owner;
        this.cell = cell;

        originalColor = owner.getColor();
        color = originalColor;

        statsPanel = new GameHUD.StatsSection.Building.Building(null, this);

        health = 0;
    }

    protected Units createUnit(String className) {
        Units unit;

        for (int j = 1; j < Addresses.board.mapSize - getShape().ypoints[3]/Addresses.board.height + 1; j++)
            for (int i = 0; i < size + 2*j; i++) {
                    unit = Units.getUnit(className, Addresses.board.cells[cell.getI() + j - i][cell.getJ() - j], owner);
                    if (unit.isAllowed(cell.getI() + j - i, cell.getJ() - j))
                        return unit;
                    unit = Units.getUnit(className, Addresses.board.cells[cell.getI() + j - i][cell.getJ() - j + (size + 2*j - 1)], owner);
                    if (unit.isAllowed(cell.getI() + j - i, cell.getJ() - j + (size + 2*j - 1)))
                        return unit;
                    unit = Units.getUnit(className, Addresses.board.cells[cell.getI() + j][cell.getJ() - j + i], owner);
                    if (unit.isAllowed(cell.getI() + j, cell.getJ() - j + i))
                        return unit;
                    unit = Units.getUnit(className, Addresses.board.cells[cell.getI() + j - (size + 2*j - 1)][cell.getJ() - j + i], owner);
                    if (unit.isAllowed(cell.getI() + j - (size + 2*j - 1), cell.getJ() - j + i))
                        return unit;
            }

        return null;
    }

    protected void setHints () {
        xHint = (int) (Addresses.board.zoom*originalXHint);
        yHint = (int) (Addresses.board.zoom*originalYHint);
    }

    protected synchronized void construct() {
        if (health == healthCapacity - constructionSpeed) {
            health += constructionSpeed;
            constructed = true;
            setHints();
        } else if (health < healthCapacity)
            health += constructionSpeed;
    }

    public boolean isConstructed() {
        return constructed;
    }

    public void setConstructed() {
        constructed = true;
        health = healthCapacity;
    }

    public void setThread() {
        thread = new Thread(this);
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setKind:
                owner.addUnit(this);

                for (int i = size - 1; i >= 0; i--)
                    for (int j = size - 1; j >= 0; j--)
                        Addresses.board.cells[cell.getI() - i][cell.getJ() + j].setKind(this);

                Addresses.undo.push(this);
                Addresses.redo.removeAll();
                break;
            case Events.setKindStack:
                owner.addUnit(this);

                for (int i = size - 1; i >= 0; i--)
                    for (int j = size - 1; j >= 0; j--)
                        Addresses.board.cells[cell.getI() - i][cell.getJ() + j].setKind(this);
                break;
            case Events.clearKind:
                owner.clearUnit(this);

                for (int i = size - 1; i >= 0; i--)
                    for (int j = size - 1; j >= 0; j--)
                        Addresses.board.cells[cell.getI() - i][cell.getJ() + j].setKind(null);
                break;
            case Events.build:
                construct();
                break;
        }
    }

    @Override
    public void run() {
        while (queued > 0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Units unit = createUnit(queuedUnits.firstElement());
            unit.dispatchEvent(new GameEvent(this, Events.setKindStack));
            new Thread((Human) unit).start();

            synchronized (this) {
                queued--;
                queuedUnits.removeElementAt(0);
            }
        }
    }
}
