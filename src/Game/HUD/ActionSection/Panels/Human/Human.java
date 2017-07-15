package Game.HUD.ActionSection.Panels.Human;

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
public class Human extends JPanel implements ActionListener {

    protected JButton death = new JButton();

    public Human(LayoutManager layout) {
        super(layout);

        setOpaque(false);
        setSize(272, 173);

        ImageIcon image = new ImageIcon("resources\\images\\ui\\icons\\icons011.png");
        death.setSize(68, 68);
        death.setLocation(3*68, 0);
        death.setIcon(image);
        death.addActionListener(this);
        add(death);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(death))
            ((Board) Addresses.board).getActiveUnit().dispatchEvent(new GameEvent(this, Events.death));
    }

}
