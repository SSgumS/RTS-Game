package MapEditor.GameEvent;

import MapEditor.Season.Season;
import MapEditor.Units.Terrain;
import MapEditor.Units.UnitsInterface;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.Vector;

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

    private Vector <Terrain> allTerrains = new Vector<>();
    private Vector <String> allTerrainsStr = new Vector<>();

    public GenerateMapEvent(Component source, int id, String size, String terrain, String season) {
        super(source, id);
        this.size = size;
        this.terrain = terrain;
        this.season = season;

        for (Terrain unit : Terrain.values()) {
            allTerrains.add(unit);
            allTerrainsStr.add(unit.getName());
        }
    }

    public int getSize() {
        switch (size) {
            case "2 Players (Small)":
                return 100;
            case "3 Players (Medium)":
                return 150;
            case "4 Players (Large)":
                return 200;
        }
        return 0;
    }

    public Terrain getTerrain() {
        Terrain unit = allTerrains.elementAt(allTerrainsStr.indexOf(terrain));

        return unit;
    }

    public Season getSeason() {
        return Season.valueOf(season);
    }
}
