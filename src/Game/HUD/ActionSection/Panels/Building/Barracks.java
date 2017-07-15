package Game.HUD.ActionSection.Panels.Building;

import Addresses.Addresses;
import Game.Map.Board;
import GameEvent.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Saeed on 7/10/2017.
 */
public class Barracks extends JPanel implements ActionListener {

    private JButton soldier = new JButton();

    public Barracks(LayoutManager layout) {
        super(layout);

        setOpaque(false);
        setSize(272, 173);

        ImageIcon image = new ImageIcon("resources\\images\\ui\\units\\soldier.png");

        soldier.setSize(68, 68);
        soldier.setLocation(0, 0);
        soldier.setIcon(image);
        soldier.addActionListener(this);
        add(soldier);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((Board) Addresses.board).getActiveUnit().dispatchEvent(new GameEvent(this, Events.createUnit));
    }

}
