package Units.Human;

import Addresses.Addresses;
import GameEvent.Events;
import Map.GameCell;
import Player.Player;
import Units.Units;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 6/6/2017.
 */
public class Human extends Units {

    protected Player owner;

    public Human(GameCell cell, Player owner, int size) {
        super(cell, owner, size);
        this.owner = owner;
    }

    @Override
    public Color getColor() {
        return owner.getColor();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setKind:
                owner.addUnit(this);

                Addresses.undo.push(this);
                Addresses.redo.removeAll();
                break;
            case Events.setKindStack:
                owner.addUnit(this);
                break;
            case Events.clearKind:
                owner.clearUnit(this);
                break;
        }
    }

}
