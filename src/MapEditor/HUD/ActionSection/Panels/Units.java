package MapEditor.HUD.ActionSection.Panels;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.UnitSelectEvent;
import MapEditor.HUD.ActionSection.ActionSection;
import MapEditor.Units.*;
import MapEditor.Units.Terrain;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Saeed on 5/19/2017.
 */
public class Units extends JPanel implements ListSelectionListener, ChangeListener {

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

    private Vector <UnitsInterface> allUnits = new Vector<>();
    private Vector <String> allUnitsStr = new Vector<>();

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

        tabbedPane.addChangeListener(this);

        unitsScrollPane.addMouseMotionListener(Addresses.board);
        buildingScrollPane.addMouseMotionListener(Addresses.board);
        terrainScrollPane.addMouseMotionListener(Addresses.board);
        othersScrollPane.addMouseMotionListener(Addresses.board);

        add(tabbedPane);
    }

    private void setUnit() {
        for (MapEditor.Units.Units unit : MapEditor.Units.Units.values()) {
            units.add(unit.getName());

            allUnits.add(unit);
            allUnitsStr.add(unit.getName());
        }
        unit = new JList<>(units);
        unit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unit.setForeground(Color.WHITE);
        unit.addListSelectionListener(this);
        unitsScrollPane = new JScrollPane(unit);
        tabbedPane.addTab("Units", unitsScrollPane);
    }

    private void setBuilding() {
        for (Building building : Building.values()) {
            buildings.add(building.getName());

            allUnits.add(building);
            allUnitsStr.add(building.getName());
        }
        building = new JList<>(buildings);
        building.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        building.setForeground(Color.WHITE);
        building.addListSelectionListener(this);
        buildingScrollPane = new JScrollPane(building);
        tabbedPane.addTab("Building", buildingScrollPane);
    }

    private void setTerrain() {
        for (Terrain terrain : Terrain.values()) {
            terrains.add(terrain.getName());

            allUnits.add(terrain);
            allUnitsStr.add(terrain.getName());
        }
        terrain = new JList<>(terrains);
        terrain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        terrain.setForeground(Color.WHITE);
        terrain.addListSelectionListener(this);
        terrainScrollPane = new JScrollPane(terrain);
        tabbedPane.addTab("Terrain", terrainScrollPane);
    }

    private void setOther() {
        for (Others other : Others.values()) {
            if (!others.contains(other.getName()))
                others.add(other.getName());

            allUnits.add(other);
            allUnitsStr.add(other.getName());
        }
        other = new JList<>(others);
        other.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        other.setForeground(Color.WHITE);
        other.addListSelectionListener(this);
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

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            JList source = (JList) e.getSource();
            UnitsInterface unit = allUnits.elementAt(allUnitsStr.indexOf(source.getSelectedValue()));

            Addresses.board.dispatchEvent(new UnitSelectEvent(source, Events.unitSelect, unit));
        } catch (ArrayIndexOutOfBoundsException ignored) {} //throws when we clear selection
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        ((JList) ((JScrollPane) ((JTabbedPane) e.getSource()).getSelectedComponent()).getViewport().getView()).clearSelection();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        unit.clearSelection();
        building.clearSelection();
        terrain.clearSelection();
        other.clearSelection();
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
