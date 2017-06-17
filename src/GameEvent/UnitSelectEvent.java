package GameEvent;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/25/2017.
 */
public class UnitSelectEvent extends ComponentEvent {

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

    private Object unit;

    public UnitSelectEvent(Component source, int id, Object unit) {
        super(source, id);
        this.unit = unit;
    }

    public Object getUnit() {
        return unit;
    }

}
