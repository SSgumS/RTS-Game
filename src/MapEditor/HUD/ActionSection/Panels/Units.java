package MapEditor.HUD.ActionSection.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeed on 5/19/2017.
 */
public class Units extends JPanel {

    private JTabbedPane tabbedPane;
    private JScrollPane unitsScrollPane;
    private JScrollPane buildingScrollPane;
    private JScrollPane terrainScrollPane;
    private JScrollPane othersScrollPane;
    private JList <String> units = new JList<>(new String[]{"jdflksjaf", "odjfkasjfkj"});
    private JList <String> building = new JList<>(new String[]{"jdflksjaf", "odjfkasjfkj"});
    private JList <String> terrain = new JList<>(new String[]{"jdflksjaf", "odjfkasjfkj"});
    private JList <String> others = new JList<>(new String[]{"jdflksjaf", "odjfkasjfkj"});

    public Units(LayoutManager layout) {
        super(layout);
        setSize(271, 173);

        UIManager.put("TabbedPane.contentOpaque", false);
        tabbedPane = new JTabbedPane();

        tabbedPane.setSize(getWidth(), getHeight());

        setUnits();
        setBuilding();
        setTerrain();
        setOthers();

        setOpaques();

        add(tabbedPane);
    }

    private void setUnits() {
        units.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        units.setForeground(Color.WHITE);
        unitsScrollPane = new JScrollPane(units);
        tabbedPane.addTab("Units", unitsScrollPane);
    }

    private void setBuilding() {
        building.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        building.setForeground(Color.WHITE);
        buildingScrollPane = new JScrollPane(building);
        tabbedPane.addTab("Building", buildingScrollPane);
    }

    private void setTerrain() {
        terrain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        terrain.setForeground(Color.WHITE);
        terrainScrollPane = new JScrollPane(terrain);
        tabbedPane.addTab("Terrain", terrainScrollPane);
    }

    private void setOthers() {
        others.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        others.setForeground(Color.WHITE);
        othersScrollPane = new JScrollPane(others);
        tabbedPane.addTab("Others", othersScrollPane);
    }

    private void setOpaques() {
        setOpaque(false);

        units.setOpaque(false);
        building.setOpaque(false);
        others.setOpaque(false);

        unitsScrollPane.getViewport().setOpaque(false);
        unitsScrollPane.setOpaque(false);
        buildingScrollPane.getViewport().setOpaque(false);
        buildingScrollPane.setOpaque(false);
        othersScrollPane.getViewport().setOpaque(false);
        othersScrollPane.setOpaque(false);

        TransparentListCellRenderer cellRenderer = new TransparentListCellRenderer();
        units.setCellRenderer(cellRenderer);
        building.setCellRenderer(cellRenderer);
        others.setCellRenderer(cellRenderer);
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
