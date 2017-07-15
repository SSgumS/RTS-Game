package Game.HUD.StatsSection.Panels;

import Units.Units;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeed on 7/10/2017.
 */
public class SingleSelect extends JPanel {

    protected Units unit;

    public SingleSelect(LayoutManager layout, Units unit) {
        super(layout);
        
        setOpaque(false);
        setSize(480, 152);
        setLocation(33, 356);

        this.unit = unit;
    }

}
