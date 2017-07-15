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
public class Seaport extends JPanel implements ActionListener {

    private JButton boat = new JButton();
    private JButton ship = new JButton();

    public Seaport(LayoutManager layout) {
        super(layout);

        setOpaque(false);
        setSize(272, 173);

        ImageIcon image1 = new ImageIcon("resources\\images\\ui\\units\\boat.png");
        ImageIcon image2 = new ImageIcon("resources\\images\\ui\\units\\ship.png");

        boat.setSize(68, 68);
        boat.setLocation(0, 0);
        boat.setIcon(image1);
        boat.addActionListener(this);
        boat.setName("Boat");
        add(boat);

        ship.setSize(68, 68);
        ship.setLocation(68, 0);
        ship.setIcon(image2);
        ship.addActionListener(this);
        ship.setName("Ship");
//        add(ship);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        ((Board) Addresses.board).getActiveUnit().dispatchEvent(new GameEvent(source, Events.createUnit));
    }
}
