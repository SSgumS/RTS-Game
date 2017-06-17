package GameEvent;

import Addresses.Addresses;
import Season.Season;
import Terrain.Terrain;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/25/2017.
 */
public class GenerateMapEvent extends ComponentEvent {

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

    private String size;
    private String terrain;
    private String season;

    public GenerateMapEvent(Component source, int id, String size, String terrain, String season) {
        super(source, id);
        this.size = size;
        this.terrain = terrain;
        this.season = season;
    }

    public int getSize() {
        switch (size) {
            case "2 Players (Small)":
                return 75;
            case "3 Players (Medium)":
                return 100;
            case "4 Players (Large)":
                return 125;
        }
        return 0;
    }

    public Terrain getTerrain() {
        return (Terrain) Addresses.unitsHashMap.get(terrain);
    }

    public Season getSeason() {
        return Season.valueOf(season);
    }

    public int getPlayerNumber() {
        switch (size) {
            case "2 Players (Small)":
                return 2;
            case "3 Players (Medium)":
                return 3;
            case "4 Players (Large)":
                return 4;
        }

        return 0;
    }

}
