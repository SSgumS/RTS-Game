package MapEditor.MenuBar.EditorButtons;

import MapEditor.Button.Button;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeed on 5/17/2017.
 */
public class EditorButtons extends JPanel {

    private Button map;
    private Button terrain;
    private Button units;
    private Button game;

    public EditorButtons(LayoutManager layout) {
        super(layout);

        setOpaque(false);

        addMapButton();
        addTerrainButton();
        addUnitsButton();
        addGameButton();
    }

    private void addMapButton() {
        map = new Button("Map");
        map.setLocation(10, 3);
        add(map);
    }

    private void addTerrainButton() {
        terrain = new Button("Terrain");
        terrain.setLocation(map.getX() + map.getWidth() + 10, 3);
        add(terrain);
    }

    private void addUnitsButton() {
        units = new Button("Units");
        units.setLocation(terrain.getX() + terrain.getWidth() + 10, 3);
        add(units);
    }

    private void addGameButton() {
        game = new Button("Game");
        game.setLocation(units.getX() + units.getWidth() + 10, 3);
        add(game);
    }

}
