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
public class Town extends JPanel implements ActionListener {

    protected JButton worker = new JButton();

    public Town(LayoutManager layout) {
        super(layout);

        setOpaque(false);
        setSize(272, 173);

        ImageIcon image = new ImageIcon("resources\\images\\ui\\units\\worker.png");
        worker.setSize(68, 68);
        worker.setLocation(0, 0);
        worker.setIcon(image);
        worker.addActionListener(this);
        add(worker);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((Board) Addresses.board).getActiveUnit().dispatchEvent(new GameEvent(this, Events.createUnit));
    }
}
