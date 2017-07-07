package Game.MenuBar.StatsSection;

import Addresses.Addresses;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Arman on 7/7/2017.
 */
public class StatsSection extends JPanel {

    private JLabel tree;
    private JLabel food;
    private JLabel gold;
    private JLabel stone;

    public StatsSection(LayoutManager layout) {
        super(layout);

        tree = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getTree()));
        tree.setLocation(25, 0);
        tree.setSize(69 - 25, 17);
        add(tree);

        food = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getFood()));
        food.setLocation(tree.getX() + tree.getWidth() + 8 + 25, 0);
        food.setSize(69 - 25, 17);
        add(food);

        gold = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getGold()));
        gold.setLocation(food.getX() + food.getWidth() + 8 + 25, 0);
        gold.setSize(69 - 25, 17);
        add(gold);

        stone = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getStone()));
        stone.setLocation(gold.getX() + gold.getWidth() + 8 + 25, 0);
        stone.setSize(69 - 25, 17);
        add(stone);
    }

}
