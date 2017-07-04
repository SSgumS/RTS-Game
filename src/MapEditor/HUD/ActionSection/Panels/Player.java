package MapEditor.HUD.ActionSection.Panels;

import Addresses.Addresses;
import GameEvent.Events;
import GameEvent.GameEvent;
import MapEditor.Map.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/19/2017.
 */
public class Player extends JPanel implements ActionListener {

    private ButtonGroup playersGroup = new ButtonGroup();
    private JRadioButton player1 = new JRadioButton("Player 1", true);
    private JRadioButton player2 = new JRadioButton("Player 2");
    private JRadioButton player3 = new JRadioButton("Player 3");
    private JRadioButton player4 = new JRadioButton("Player 4");
    private JRadioButton[] players = new JRadioButton[4];

    public Player(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setSize(271, 173);

        initButtons();
        setSizes();
        setLocations();
        addRadioButtons();
        addActionListener();
        setOpaques();
        addListeners();

        add(player1);
        add(player2);
    }

    private void initButtons() {
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;
        player1.setBackground(Color.BLUE);
        player2.setBackground(Color.RED);
        player3.setBackground(Color.BLACK);
        player4.setBackground(Color.MAGENTA);
        player1.setForeground(Color.WHITE);
        player2.setForeground(Color.WHITE);
        player3.setForeground(Color.WHITE);
        player4.setForeground(Color.WHITE);
    }

    private void setSizes() {
        player1.setSize(getWidth()/2, getHeight()/4 - 10);
        player2.setSize(getWidth()/2, getHeight()/4 - 10);
        player3.setSize(getWidth()/2, getHeight()/4 - 10);
        player4.setSize(getWidth()/2, getHeight()/4 - 10);
    }

    private void setLocations() {
        player1.setLocation(getWidth()/2 - player1.getWidth()/2, 5);
        player2.setLocation(getWidth()/2 - player2.getWidth()/2, player1.getY() + getHeight()/4);
        player3.setLocation(getWidth()/2 - player3.getWidth()/2, player2.getY() + getHeight()/4);
        player4.setLocation(getWidth()/2 - player4.getWidth()/2, player3.getY() + getHeight()/4);
    }

    private void addRadioButtons() {
        playersGroup.add(player1);
        playersGroup.add(player2);
        playersGroup.add(player3);
        playersGroup.add(player4);
    }

    private void addActionListener() {
        player1.addActionListener(this);
        player2.addActionListener(this);
        player3.addActionListener(this);
        player4.addActionListener(this);
    }

    private void setOpaques() {
        player1.setOpaque(true);
        player2.setOpaque(true);
        player3.setOpaque(true);
        player4.setOpaque(true);
    }

    private void addListeners() {
        player1.addMouseMotionListener(Addresses.panel);
        player2.addMouseMotionListener(Addresses.panel);
        player3.addMouseMotionListener(Addresses.panel);
        player4.addMouseMotionListener(Addresses.panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton source = (JRadioButton) e.getSource();

        switch (source.getText()) {
            case "Player 1":
                Addresses.board.dispatchEvent(new GameEvent(player1, Events.currentPlayer));
                break;
            case "Player 2":
                Addresses.board.dispatchEvent(new GameEvent(player2, Events.currentPlayer));
                break;
            case "Player 3":
                Addresses.board.dispatchEvent(new GameEvent(player3, Events.currentPlayer));
                break;
            case "Player 4":
                Addresses.board.dispatchEvent(new GameEvent(player4, Events.currentPlayer));
                break;
        }
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        removeAll();

        switch (((Board) Addresses.board).getPlayersNumber()) {
            case 2:
                add(player1);
                add(player2);
                break;
            case 3:
                add(player1);
                add(player2);
                add(player3);
                break;
            case 4:
                add(player1);
                add(player2);
                add(player3);
                add(player4);
                break;
        }

        playersGroup.clearSelection();
        players[((Board) Addresses.board).getCurrentPlayerNumber()].setSelected(true);
    }
}
