package GameEvent;

import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/18/2017.
 */
public class Events {

    public static final int actionOn = ComponentEvent.RESERVED_ID_MAX + 1;
    public static final int actionOff = ComponentEvent.RESERVED_ID_MAX + 2;
//    public static final int setOrigin = ComponentEvent.RESERVED_ID_MAX + 3;
    public static final int unitSelect = ComponentEvent.RESERVED_ID_MAX + 3;
    public static final int generateMap = ComponentEvent.RESERVED_ID_MAX + 4;
    public static final int boardCreated = ComponentEvent.RESERVED_ID_MAX + 5;
    public static final int load = ComponentEvent.RESERVED_ID_MAX + 6;
    public static final int clearSelection = ComponentEvent.RESERVED_ID_MAX + 7;
    public static final int zoom = ComponentEvent.RESERVED_ID_MAX + 8;
    public static final int cellRefactor = ComponentEvent.RESERVED_ID_MAX + 9;
    public static final int currentPlayer = ComponentEvent.RESERVED_ID_MAX + 10;
    public static final int setKind = ComponentEvent.RESERVED_ID_MAX + 11;
    public static final int clearKind = ComponentEvent.RESERVED_ID_MAX + 12;
    public static final int setKindStack = ComponentEvent.RESERVED_ID_MAX + 13;
    public static final int order = ComponentEvent.RESERVED_ID_MAX + 14;
    public static final int createUnit = ComponentEvent.RESERVED_ID_MAX + 15;
    public static final int death = ComponentEvent.RESERVED_ID_MAX + 16;
    public static final int unitDeselect = ComponentEvent.RESERVED_ID_MAX + 17;
    public static final int setUnitImages = ComponentEvent.RESERVED_ID_MAX + 18;
    public static final int seasonChanged = ComponentEvent.RESERVED_ID_MAX + 19;
    public static final int shutdown = ComponentEvent.RESERVED_ID_MAX + 20;
    public static final int use = ComponentEvent.RESERVED_ID_MAX + 21;
    public static final int wood = ComponentEvent.RESERVED_ID_MAX + 22;
    public static final int gold = ComponentEvent.RESERVED_ID_MAX + 23;
    public static final int stone = ComponentEvent.RESERVED_ID_MAX + 24;
    public static final int food = ComponentEvent.RESERVED_ID_MAX + 25;
    public static final int build = ComponentEvent.RESERVED_ID_MAX + 26;

}
