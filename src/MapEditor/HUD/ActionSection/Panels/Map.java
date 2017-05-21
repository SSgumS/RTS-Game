package MapEditor.HUD.ActionSection.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeed on 5/19/2017.
 */
public class Map extends JPanel {

    public Map(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setSize(271, 173);

        addMapSize();
        addDefaultTerrain();
        addButton();
    }

    private void addMapSize() {
        JLabel label = new JLabel("Map Size:");
        label.setForeground(Color.WHITE);
        label.setSize(getWidth()/2 - 20, getHeight()/4 - 20);
        label.setLocation(10, 10);
        add(label);

        JComboBox <String> mapSize = new JComboBox<>(new String[]{"2 Players (Small)", "3 Players (Medium)", "4 Players (Large)"});
        mapSize.setSize(getWidth()/2 - 10, getHeight()/4 - 20);
        mapSize.setLocation(label.getX() + label.getWidth() + 10, 10);
        add(mapSize);
    }

    private void addDefaultTerrain() {
        JLabel label = new JLabel("Default Terrain:");
        label.setForeground(Color.WHITE);
        label.setSize(getWidth()/2 - 20, getHeight()/4 - 20);
        label.setLocation(10, getHeight()/4 + 10);
        add(label);

        JComboBox <String> terrains = new JComboBox<>(new String[]{"Deep Water", "Dessert", "Dirt", "Dirt-Snow", "Grass", "Grass-Snow", "Ice", "Snow", "Water"});
        terrains.setSelectedIndex(4);
        terrains.setSize(getWidth()/2 - 10, getHeight()/4 - 20);
        terrains.setLocation(label.getX() + label.getWidth() + 10, getHeight()/4 + 10);
        add(terrains);
    }

    private void addButton() {
        JButton button = new JButton("Generate");
        button.setSize(3*getWidth()/4, getHeight()/2 - 50);
        button.setLocation(getWidth()/2 - button.getWidth()/2, getHeight()/2 + 25);
        add(button);
    }

}
