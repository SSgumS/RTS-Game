package Game.HUD.StatsSection;

import Addresses.Addresses;
import Game.HUD.StatsSection.Panels.MultiSelect;
import Game.Map.Board;
import GameEvent.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.Vector;

/**
 * Created by Saeed on 7/10/2017.
 */
public class StatsSection extends JPanel {

    private MultiSelect multiSelect = new MultiSelect(null);

    public StatsSection(LayoutManager layout) {
        super(layout);

        setOpaque(false);
        setSize(480, 152);
        setLocation(33, 356);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.actionOff:
                removeAll();
                break;
            case Events.unitSelect:
                removeAll();

                try {
                    Units.Units unit = ((Board) Addresses.board).getActiveUnit();
                    Vector <Units.Units> units = ((Board) Addresses.board).getSelectedUnits();
                    if (units.size() > 1) {
                        multiSelect.setUnit(unit, units);
                        add(multiSelect);
                    } else {
                        try {
                            add(unit.getStatsPanel());
                        } catch (NullPointerException ignored) {}
                    }
                } catch (NullPointerException ignored) {}
                break;
        }
    }

}
