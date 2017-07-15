package Game.HUD.ActionSection.Panels.Human.Worker;

import Addresses.Addresses;
import Game.HUD.ActionSection.Panels.Human.Human;
import GameEvent.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Saeed on 7/9/2017.
 */
public class Worker extends Human {

    private JButton build = new JButton();
    private JButton barracks = new JButton();
    private JButton seaport = new JButton();
    private JButton lumberCamp = new JButton();
    private JButton miningCamp = new JButton();
    private JButton town = new JButton();
    private JButton back = new JButton();

    public Worker(LayoutManager layout) {
        super(layout);

        ImageIcon image = new ImageIcon("resources\\images\\ui\\icons\\icons012.png");
        build.setSize(68, 68);
        build.setLocation(0, 0);
        build.setIcon(image);
        build.addActionListener(this);
        add(build);

        image = new ImageIcon("resources\\images\\ui\\icons\\icons001.png");
        barracks.setSize(68, 68);
        barracks.setLocation(0, 0);
        barracks.setIcon(image);
        barracks.setName("Barracks");
        barracks.addActionListener(this);

        image = new ImageIcon("resources\\images\\ui\\icons\\icons003.png");
        seaport.setSize(68, 68);
        seaport.setLocation(68, 0);
        seaport.setIcon(image);
        seaport.setName("Seaport");
        seaport.addActionListener(this);

        image = new ImageIcon("resources\\images\\ui\\icons\\icons008.png");
        lumberCamp.setSize(68, 68);
        lumberCamp.setLocation(2*68, 0);
        lumberCamp.setIcon(image);
        lumberCamp.setName("Lumber Camp");
        lumberCamp.addActionListener(this);

        image = new ImageIcon("resources\\images\\ui\\icons\\icons007.png");
        miningCamp.setSize(68, 68);
        miningCamp.setLocation(3*68, 0);
        miningCamp.setIcon(image);
        miningCamp.setName("Mining Camp");
        miningCamp.addActionListener(this);

        image = new ImageIcon("resources\\images\\ui\\icons\\icons010.png");
        town.setSize(68, 68);
        town.setLocation(0, 68);
        town.setIcon(image);
        town.setName("Town");
        town.addActionListener(this);

        image = new ImageIcon("resources\\images\\ui\\icons\\icons013.png");
        back.setSize(68, 68);
        back.setLocation(3*68, 68);
        back.setIcon(image);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        JButton source = (JButton) e.getSource();

        if (source.equals(build)) {
            removeAll();
            add(barracks);
            add(seaport);
            add(lumberCamp);
            add(miningCamp);
            add(town);
            add(back);
        } else if (source.equals(back)) {
            removeAll();
            add(build);
            add(death);
        } else if (!source.equals(death)) {
            Object unit = Addresses.unitsHashMap.get(source.getName());
            Addresses.board.dispatchEvent(new UnitSelectEvent(source, Events.unitSelect, unit));
        }

    }

}
