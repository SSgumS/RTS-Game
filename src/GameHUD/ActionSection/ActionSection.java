package GameHUD.ActionSection;

import GameEvent.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 7/9/2017.
 */
public class ActionSection extends JPanel {

    public ActionSection(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        setSize(272, 173);
        setLocation(34, 27);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);


        switch (e.getID()) {
            case Events.actionOff:
                removeAll();
                break;
        }
    }

}
