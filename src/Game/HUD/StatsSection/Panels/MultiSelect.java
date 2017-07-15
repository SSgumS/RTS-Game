package Game.HUD.StatsSection.Panels;

import Units.Units;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Saeed on 7/10/2017.
 */
public class MultiSelect extends JPanel {

    public MultiSelect(LayoutManager layout) {
        super(layout);

        setOpaque(false);
        setSize(480, 152);
        setLocation(33, 356);
    }

    public void setUnit(Units unit, Vector <Units> units) {}

}
