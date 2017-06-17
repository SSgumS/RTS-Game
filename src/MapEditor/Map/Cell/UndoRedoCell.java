package MapEditor.Map.Cell;

import GameEvent.Events;
import GameEvent.GameEvent;
import Units.Units;

import javax.swing.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 6/14/2017.
 */
public class UndoRedoCell extends JComponent {

    private Cell cell;
    private Units unit;

    public UndoRedoCell(Cell cell) {
        this.cell = cell;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    public Units getUnit() {
        return unit;
    }

    public Cell getCell() {
        return cell;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setKind:
                if (unit != null)
                    unit.dispatchEvent(new GameEvent(this, Events.setKindStack));
                break;
            case Events.clearKind:
                if (unit != null)
                    unit.dispatchEvent(new GameEvent(this, Events.clearKind));
                break;
        }
    }

}
