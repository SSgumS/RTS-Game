package MapEditor.HUD;

import GameEvent.Events;
import MapEditor.HUD.ActionSection.ActionSection;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/15/2017.
 */
public class HUD extends GameHUD.HUD {

    private ActionSection actionSection = new ActionSection(null);

    public HUD(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);

        add(actionSection);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn || e.getID() == Events.actionOff)
            actionSection.dispatchEvent(e);
        else if (e.getID() == Events.boardCreated) {
            miniMap.dispatchEvent(e);
            actionSection.dispatchEvent(e);
        } else if (e.getID() == Events.clearSelection)
            actionSection.dispatchEvent(e);
    }
}
