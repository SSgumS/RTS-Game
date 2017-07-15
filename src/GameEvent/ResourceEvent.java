package GameEvent;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 7/12/2017.
 */
public class ResourceEvent extends ComponentEvent {

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

    private int bar;

    public ResourceEvent(Component source, int id, int bar) {
        super(source, id);

        this.bar = bar;
    }

    public int getBar() {
        return bar;
    }
}
