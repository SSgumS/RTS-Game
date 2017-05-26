package MapEditor.HUD.ActionSection.Panels;

import MapEditor.Addresses.Addresses;
import MapEditor.Button.RegularButton;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.GameEvent.GenerateMapEvent;
import MapEditor.HUD.ActionSection.ActionSection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Saeed on 5/19/2017.
 */
public class Map extends JPanel implements ActionListener {

    private JComboBox <String> mapSize;
    private JComboBox <String> terrains;
    private JComboBox <String> seasons;

    public Map(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setSize(271, 173);

        addMapSize();
        addDefaultTerrain();
        addDefaultSeason();
        addButton();
    }

    private void addMapSize() {
        JLabel label = new JLabel("Map Size:");
        label.setForeground(Color.WHITE);
        label.setSize(getWidth()/2 - 20, getHeight()/4 - 20);
        label.setLocation(10, 10);
        add(label);

        mapSize = new JComboBox<>(new String[]{"2 Players (Small)", "3 Players (Medium)", "4 Players (Large)"});
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

        terrains = new JComboBox<>(new String[]{"Deep Water", "Dessert", "Grass", "Snow", "Water"});
        terrains.setSelectedIndex(2);
        terrains.setSize(getWidth()/2 - 10, getHeight()/4 - 20);
        terrains.setLocation(label.getX() + label.getWidth() + 10, getHeight()/4 + 10);
        add(terrains);
    }

    private void addDefaultSeason() {
        JLabel label = new JLabel("Default Season:");
        label.setForeground(Color.WHITE);
        label.setSize(getWidth()/2 - 20, getHeight()/4 - 20);
        label.setLocation(10, getHeight()/2 + 10);
        add(label);

        seasons = new JComboBox<>(new String[]{"Spring", "Summer", "Autumn", "Winter"});
        seasons.setSize(getWidth()/2 - 10, getHeight()/4 - 20);
        seasons.setLocation(label.getX() + label.getWidth() + 10, getHeight()/2 + 10);
        add(seasons);
    }

    private void addButton() {
        RegularButton button = new RegularButton("Generate");
        button.setSize(3*getWidth()/4, getHeight()/4 - 20);
        button.setLocation(getWidth()/2 - button.getWidth()/2, 3*getHeight()/4 + 10);
        button.addActionListener(this);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Addresses.panel.dispatchEvent(new GenerateMapEvent((JButton) e.getSource(), Events.generateMap, (String) mapSize.getSelectedItem(), (String) terrains.getSelectedItem(), (String) seasons.getSelectedItem()));
        Addresses.panel.dispatchEvent(new GameEvent((JButton) e.getSource(), Events.clearSelection));
    }

}
