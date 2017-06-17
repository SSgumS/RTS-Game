package GameEvent;

import Map.GameCell;
import MapEditor.Map.Cell.UndoRedoCell;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 6/8/2017.
 */
public class SetKindEvent extends ComponentEvent {

    /**
     * Constructs a <code>ComponentEvent</code> object.
     * <p> This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * @param source The <code>Component</code> that originated the event
     * @param id     An integer indicating the type of event.
     *               For information on allowable values, see
     *               the class description for {@link ComponentEvent}
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getComponent()
     * @see #getID()
     */

    private GameCell cell;
    private UndoRedoCell undoRedoCell;

    public SetKindEvent(Component source, int id, GameCell cell, UndoRedoCell undoRedoCell) {
        super(source, id);

        this.cell = cell;
        this.undoRedoCell = undoRedoCell;
    }

    public GameCell getCell() {
        return cell;
    }

    public UndoRedoCell getUndoRedoCell() {
        return undoRedoCell;
    }

}
