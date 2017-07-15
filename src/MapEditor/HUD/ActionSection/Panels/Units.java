package MapEditor.HUD.ActionSection.Panels;

import Addresses.Addresses;
import GameEvent.Events;
import GameEvent.UnitSelectEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/19/2017.
 */
public class Units extends JPanel implements ListSelectionListener, ChangeListener {

    private JTabbedPane tabbedPane;
    private JScrollPane unitsScrollPane;
    private JScrollPane buildingScrollPane;
    private JScrollPane terrainScrollPane;
    private JScrollPane othersScrollPane;
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

        tabbedPane.addChangeListener(this);

        unit.addMouseMotionListener(Addresses.panel);
        building.addMouseMotionListener(Addresses.panel);
        terrain.addMouseMotionListener(Addresses.panel);
        other.addMouseMotionListener(Addresses.panel);
        tabbedPane.addMouseMotionListener(Addresses.panel);

        add(tabbedPane);
    }

    private void setUnit() {
        unit = new JList<>(new String[]{"Worker", "Soldier", "Boat"});
        unit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unit.setForeground(Color.WHITE);
        unit.addListSelectionListener(this);
        unitsScrollPane = new JScrollPane(unit);
        tabbedPane.addTab("Units", unitsScrollPane);
    }

    private void setBuilding() {
        building = new JList<>(new String[]{"Town", "Seaport", "Barracks", "Lumber Camp", "Mining Camp"});
        building.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        building.setForeground(Color.WHITE);
        building.addListSelectionListener(this);
        buildingScrollPane = new JScrollPane(building);
        tabbedPane.addTab("Building", buildingScrollPane);
    }

    private void setTerrain() {
        terrain = new JList<>(new String[]{"Deep Water", "Dessert", "Grass", "Ice", "Snow", "Water"});
        terrain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        terrain.setForeground(Color.WHITE);
        terrain.addListSelectionListener(this);
        terrainScrollPane = new JScrollPane(terrain);
        tabbedPane.addTab("Terrain", terrainScrollPane);
    }

    private void setOther() {
        other = new JList<>(new String[]{"Bush", "Big Fish", "Little Fish", "Gold Mine", "Stone Mine", "Tree"});
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
        JList source = (JList) e.getSource();
        Object unit;
        if (source.getSelectedValue() != null) {
            unit = Addresses.unitsHashMap.get(source.getSelectedValue());
            Addresses.board.dispatchEvent(new UnitSelectEvent(source, Events.unitSelect, unit));
        }
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
