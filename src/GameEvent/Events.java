package GameEvent;

import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/18/2017.
 */
public class Events {

    public static final int actionOn = ComponentEvent.RESERVED_ID_MAX + 1;
    public static final int actionOff = ComponentEvent.RESERVED_ID_MAX + 2;
    public static final int setOrigin = ComponentEvent.RESERVED_ID_MAX + 3;
    public static final int unitSelect = ComponentEvent.RESERVED_ID_MAX + 4;
    public static final int generateMap = ComponentEvent.RESERVED_ID_MAX + 5;
    public static final int boardCreated = ComponentEvent.RESERVED_ID_MAX + 6;
    public static final int load = ComponentEvent.RESERVED_ID_MAX + 7;
    public static final int clearSelection = ComponentEvent.RESERVED_ID_MAX + 8;
    public static final int zoom = ComponentEvent.RESERVED_ID_MAX + 9;
    public static final int cellRefactor = ComponentEvent.RESERVED_ID_MAX + 10;
    public static final int currentPlayer = ComponentEvent.RESERVED_ID_MAX + 11;
    public static final int setKind = ComponentEvent.RESERVED_ID_MAX + 12;
    public static final int clearKind = ComponentEvent.RESERVED_ID_MAX + 13;
    public static final int setKindStack = ComponentEvent.RESERVED_ID_MAX + 14;

}
