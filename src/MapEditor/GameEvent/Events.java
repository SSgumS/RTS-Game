package MapEditor.GameEvent;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/18/2017.
 */
public class Events {

    public static int actionOn = ComponentEvent.RESERVED_ID_MAX + 1;
    public static int actionOff = ComponentEvent.RESERVED_ID_MAX + 2;
    public static int setOrigin = ComponentEvent.RESERVED_ID_MAX + 3;
    public static int unitSelect = ComponentEvent.RESERVED_ID_MAX + 4;
    public static int generateMap = ComponentEvent.RESERVED_ID_MAX + 5;
    public static int boardCreated = ComponentEvent.RESERVED_ID_MAX + 6;
    public static int load = ComponentEvent.RESERVED_ID_MAX + 7;
    public static int clearSelection = ComponentEvent.RESERVED_ID_MAX + 8;
    public static int zoom = ComponentEvent.RESERVED_ID_MAX + 9;
    public static int cellRefactor = ComponentEvent.RESERVED_ID_MAX + 10;

}
