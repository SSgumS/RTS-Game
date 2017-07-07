package Game.GamePanel;

import Addresses.Addresses;
import Game.Map.Board;
import GameEvent.Events;
import Map.GameBoard;
import Game.MenuBar.Menu.MenuDialog;
import Game.MenuBar.MenuBar;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 6/22/2017.
 */
public class GamePanel extends GameFrame.Panel {

    private MenuBar menuBar;
    private GameBoard board;
//    private HUD hud;
    private MenuDialog menu = new MenuDialog(Addresses.frame, true);

    public GamePanel(LayoutManager layout) {
        super(layout);

        addMenuBar();
        addHUD();
        addBoard();

        new Thread(this).start();
    }

    private void addMenuBar() {
        menuBar = new MenuBar(null);
        add(menuBar);
    }

    private void addHUD() {
//        hud = new HUD(null);
//        Addresses.hud = hud;
//        add(hud);
    }

    private void addBoard() {
        board = new Board(null, true, Addresses.board.isThreadRunning(), Addresses.board.getMapSize(), Addresses.board.getSeason(), Addresses.board.getOriginalWidth(), Addresses.board.getOriginalHeight(), Addresses.board.getOriginalXo(), Addresses.board.getOriginalYo(), Addresses.board.width, Addresses.board.height, Addresses.board.getXo(), Addresses.board.getYo(), Addresses.board.getCells(), Addresses.board.getSelectedCell(), Addresses.board.getZoom(), Addresses.board.isZoomChanged(), Addresses.board.getPlayers(), Addresses.board.getCurrentPlayer());
        add(board);
    }
	
	@Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn)
            menu.setVisible(true);
        else if (e.getID() == Events.actionOff)
            menu.setVisible(false);
	}

}
