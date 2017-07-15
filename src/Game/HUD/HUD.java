package Game.HUD;

import Game.HUD.ActionSection.ActionSection;
import Game.HUD.StatsSection.StatsSection;
import GameEvent.Events;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 7/9/2017.
 */
public class HUD extends GameHUD.HUD {

    private ActionSection actionSection = new ActionSection(null);
    private StatsSection statsSection = new StatsSection(null);

    public HUD(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);

        add(actionSection);
        add(statsSection);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.unitSelect:
            case Events.actionOff:
                actionSection.dispatchEvent(e);
                statsSection.dispatchEvent(e);
                break;
        }
    }
}
