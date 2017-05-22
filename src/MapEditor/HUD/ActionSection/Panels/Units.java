package MapEditor.HUD.ActionSection.Panels;

import MapEditor.Units.*;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Saeed on 5/19/2017.
 */
public class Units extends JPanel {

    private JTabbedPane tabbedPane;
    private JScrollPane unitsScrollPane;
    private JScrollPane buildingScrollPane;
    private JScrollPane terrainScrollPane;
    private JScrollPane othersScrollPane;
    private Vector <String> units = new Vector<>();
    private Vector <String> buildings = new Vector<>();
    private Vector <String> terrains = new Vector<>();
    private Vector <String> others = new Vector<>();
    private JList <String> unit;
    private JList <String> building;
    private JList <String> terrain;
    private JList <String> other;

    public Units(LayoutManager layout) {
        super(layout);
        setSize(271, 173);

        UIManager.put("TabbedPane.contentOpaque", false);
        tabbedPane = new JTabbedPane();

        tabbedPane.setSize(getWidth(), getHeight());

        setUnit();
        setBuilding();
        setTerrain();
        setOther();

        setOpaques();

        add(tabbedPane);
    }

    private void setUnit() {
        for (MapEditor.Units.Units unit : MapEditor.Units.Units.values())
            units.add(unit.getName());
        unit = new JList<>(units);
        unit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unit.setForeground(Color.WHITE);
        unitsScrollPane = new JScrollPane(unit);
        tabbedPane.addTab("Units", unitsScrollPane);
    }

    private void setBuilding() {
        for (Building building : Building.values())
            buildings.add(building.getName());
        building = new JList<>(buildings);
        building.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        building.setForeground(Color.WHITE);
        buildingScrollPane = new JScrollPane(building);
        tabbedPane.addTab("Building", buildingScrollPane);
    }

    private void setTerrain() {
        for (Terrain terrain : Terrain.values())
            terrains.add(terrain.getName());
        terrain = new JList<>(terrains);
        terrain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        terrain.setForeground(Color.WHITE);
        terrainScrollPane = new JScrollPane(terrain);
        tabbedPane.addTab("Terrain", terrainScrollPane);
    }

    private void setOther() {
        for (Others other : Others.values())
            others.add(other.getName());
        other = new JList<>(others);
        other.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        other.setForeground(Color.WHITE);
        othersScrollPane = new JScrollPane(other);
        tabbedPane.addTab("Others", othersScrollPane);
    }

    private void setOpaques() {
        setOpaque(false);

        unit.setOpaque(false);
        building.setOpaque(false);
        terrain.setOpaque(false);
        other.setOpaque(false);

        unitsScrollPane.getViewport().setOpaque(false);
        unitsScrollPane.setOpaque(false);
        buildingScrollPane.getViewport().setOpaque(false);
        buildingScrollPane.setOpaque(false);
        terrainScrollPane.getViewport().setOpaque(false);
        terrainScrollPane.setOpaque(false);
        othersScrollPane.getViewport().setOpaque(false);
        othersScrollPane.setOpaque(false);

        TransparentListCellRenderer cellRenderer = new TransparentListCellRenderer();
        unit.setCellRenderer(cellRenderer);
        building.setCellRenderer(cellRenderer);
        terrain.setCellRenderer(cellRenderer);
        other.setCellRenderer(cellRenderer);
    }

}

class TransparentListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setOpaque(isSelected);
        return this;
    }

}
