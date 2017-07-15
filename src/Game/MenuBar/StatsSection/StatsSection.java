package Game.MenuBar.StatsSection;

import Addresses.Addresses;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Arman on 7/7/2017.
 */
public class StatsSection extends JPanel implements Runnable {

    private JLabel wood;
    private JLabel food;
    private JLabel gold;
    private JLabel stone;

    public StatsSection(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        wood = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getWood()));
        wood.setLocation(25, 0);
        wood.setSize(69 - 25, 17);
        wood.setOpaque(false);
        wood.setForeground(Color.WHITE);
        add(wood);

        food = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getFood()));
        food.setLocation(wood.getX() + wood.getWidth() + 8 + 25, 0);
        food.setSize(69 - 25, 17);
        food.setOpaque(false);
        food.setForeground(Color.WHITE);
        add(food);

        gold = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getGold()));
        gold.setLocation(food.getX() + food.getWidth() + 8 + 25, 0);
        gold.setSize(69 - 25, 17);
        gold.setOpaque(false);
        gold.setForeground(Color.WHITE);
        add(gold);

        stone = new JLabel(Integer.toString(Addresses.board.getCurrentPlayer().getStone()));
        stone.setLocation(gold.getX() + gold.getWidth() + 8 + 25, 0);
        stone.setSize(69 - 25, 17);
        stone.setOpaque(false);
        stone.setForeground(Color.WHITE);
        add(stone);

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (Addresses.panel.isActive()) {
            wood.setText(Integer.toString(Addresses.board.getCurrentPlayer().getWood()));
            food.setText(Integer.toString(Addresses.board.getCurrentPlayer().getFood()));
            gold.setText(Integer.toString(Addresses.board.getCurrentPlayer().getGold()));
            stone.setText(Integer.toString(Addresses.board.getCurrentPlayer().getStone()));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
