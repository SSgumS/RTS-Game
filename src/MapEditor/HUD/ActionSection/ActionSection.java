package MapEditor.HUD.ActionSection;

import MapEditor.Button.Button;
import MapEditor.GameEvent.Events;
import MapEditor.HUD.ActionSection.Panels.Player;
import MapEditor.HUD.ActionSection.Panels.Map;
import MapEditor.HUD.ActionSection.Panels.Units;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/19/2017.
 */
public class ActionSection extends JPanel {

    private Map map = new Map(null);
    private Player player = new Player(null);
    private Units units = new Units(null);

    public ActionSection(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        setSize(271, 173);
        setLocation(34, 27);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn) {
            Button source = (Button) e.getSource();

            removeAll();

            switch (source.getText()) {
                case "Map":
                    add(map);
                    break;
                case "Units":
                    add(units);
                    break;
                case "Player":
                    add(player);
                    break;
            }
        } else if (e.getID() == Events.actionOff)
            removeAll();
        else if (e.getID() == Events.clearSelection)
            units.dispatchEvent(e);
        else if (e.getID() == Events.boardCreated)
            player.dispatchEvent(e);

//        Addresses.panel.repaint();
    }

}
