package MapEditor.MenuBar.EditorButtons;

import MapEditor.Addresses.Addresses;
import MapEditor.Button.Button;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Saeed on 5/17/2017.
 */
public class EditorButtons extends JPanel implements ActionListener {

    private Button map;
    private Button units;
    private Button diplomacy;
    private Button selectedButton;

    public EditorButtons(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        addMapButton();
//        addTerrainButton();
        addUnitsButton();
        addGameButton();
    }

    private void addMapButton() {
        map = new Button("Map");
        map.setLocation(10, 3);
        map.addActionListener(this);
        add(map);
    }

    private void addUnitsButton() {
        units = new Button("Units");
        units.setLocation(map.getX() + map.getWidth() + 10, 3);
        units.addActionListener(this);
        add(units);
    }

    private void addGameButton() {
        diplomacy = new Button("Diplomacy");
        diplomacy.setLocation(units.getX() + units.getWidth() + 10, 3);
        diplomacy.addActionListener(this);
        add(diplomacy);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Button source = (Button) e.getSource();
        if (source.equals(map)) {
            map.swapIcons();
            if (selectedButton == map) {
                selectedButton = null;
                Addresses.hud.dispatchEvent(new GameEvent(map, Events.actionOff));
            } else {
                if (selectedButton != null)
                    selectedButton.swapIcons();
                selectedButton = map;
                Addresses.hud.dispatchEvent(new GameEvent(map, Events.actionOn));
            }
        } else if (source.equals(units)) {
            units.swapIcons();
            if (selectedButton == units) {
                selectedButton = null;
                Addresses.hud.dispatchEvent(new GameEvent(units, Events.actionOff));
            } else {
                if (selectedButton != null)
                    selectedButton.swapIcons();
                selectedButton = units;
                Addresses.hud.dispatchEvent(new GameEvent(units, Events.actionOn));
            }
        } else if (source.equals(diplomacy)) {
            diplomacy.swapIcons();
            if (selectedButton == diplomacy) {
                selectedButton = null;
                Addresses.hud.dispatchEvent(new GameEvent(diplomacy, Events.actionOff));
            } else {
                if (selectedButton != null)
                    selectedButton.swapIcons();
                selectedButton = diplomacy;
                Addresses.hud.dispatchEvent(new GameEvent(diplomacy, Events.actionOn));
            }
        }
    }

}
