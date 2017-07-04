package MapEditor.MenuBar.EditorButtons;

import Addresses.Addresses;
import Button.Button;
import GameEvent.Events;
import GameEvent.GameEvent;

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
    private Button player;
    private Button selectedButton;

    public EditorButtons(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        addMapButton();
        addUnitsButton();
        addPlayerButton();
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

    private void addPlayerButton() {
        player = new Button("Player");
        player.setLocation(units.getX() + units.getWidth() + 10, 3);
        player.addActionListener(this);
        add(player);
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
        } else if (source.equals(player)) {
            player.swapIcons();
            if (selectedButton == player) {
                selectedButton = null;
                Addresses.hud.dispatchEvent(new GameEvent(player, Events.actionOff));
            } else {
                if (selectedButton != null)
                    selectedButton.swapIcons();
                selectedButton = player;
                Addresses.hud.dispatchEvent(new GameEvent(player, Events.actionOn));
            }
        }
    }

}
