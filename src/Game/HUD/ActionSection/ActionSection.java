package Game.HUD.ActionSection;

import Addresses.Addresses;
import Game.HUD.ActionSection.Panels.Building.Barracks;
import Game.HUD.ActionSection.Panels.Building.Seaport;
import Game.HUD.ActionSection.Panels.Building.Town;
import Game.HUD.ActionSection.Panels.Human.Human;
import Game.HUD.ActionSection.Panels.Human.Worker.Worker;
import Game.Map.Board;
import GameEvent.Events;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 7/9/2017.
 */
public class ActionSection extends GameHUD.ActionSection.ActionSection {

    private Barracks barracks = new Barracks(null);
    private Seaport seaport = new Seaport(null);
    private Town town = new Town(null);
    private Human human = new Human(null);
    private Worker worker = new Worker(null);

    public ActionSection(LayoutManager layout) {
        super(layout);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.unitSelect:
                removeAll();

                try {
                    Units.Units unit = ((Board) Addresses.board).getActiveUnit();
                    if (unit instanceof Units.Human.Human) {
                        if (unit instanceof Units.Human.Worker.Worker)
                            add(worker);
                        else
                            add(human);
                    } else if (unit instanceof Units.Building.Town.Town && unit.isAlive())
                        add(town);
                    else if (unit instanceof Units.Building.Barracks.Barracks && unit.isAlive())
                        add(barracks);
                    else if (unit instanceof Units.Building.Seaport.Seaport && unit.isAlive())
                        add(seaport);
                } catch (NullPointerException ignored) {}
                break;
        }
    }

}
