package MapEditor.GamePanel;

import Addresses.Addresses;
import GameEvent.Events;
import GameEvent.GameEvent;
import GameEvent.GenerateMapEvent;
import Map.GameBoard;
import MapEditor.HUD.HUD;
import MapEditor.Map.Board;
import MapEditor.MenuBar.Menu.MenuDialog;
import MapEditor.MenuBar.MenuBar;
import Season.Season;
import Terrain.Terrain;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by Saeed on 5/15/2017.
 */
public class GamePanel extends GameFrame.Panel {

    private MenuBar menuBar;
    private GameBoard board;
    private HUD hud;
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

    private void addBoard() {
        board = new Board(null, true, 75, Terrain.Grass, Season.Spring, 2);
        board.setSize(getWidth(), hud.getY() - menuBar.getHeight());
        board.setLocation(0, menuBar.getHeight());
        board.dispatchEvent(new GameEvent(this, Events.generateMap));
        hud.dispatchEvent(new GameEvent(board, Events.boardCreated));
        add(board);
    }

    private void addHUD() {
        hud = new HUD(null);
        Addresses.hud = hud;
        add(hud);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn) {
            menu.setVisible(true);
        } else if (e.getID() == Events.actionOff) {
            menu.setVisible(false);
        } else if (e.getID() == Events.generateMap) {
            remove(board);
            GenerateMapEvent event = (GenerateMapEvent) e;
            board = new Board(null, true, event.getSize(), event.getTerrain(), event.getSeason(), event.getPlayerNumber());
            board.setSize(getWidth(), hud.getY() - menuBar.getHeight());
            board.setLocation(0, menuBar.getHeight());
            board.dispatchEvent(new GameEvent(this, Events.generateMap));
            hud.dispatchEvent(new GameEvent(board, Events.boardCreated));
            add(board);
        } else if (e.getID() == Events.load) {
            remove(board);
            board = Addresses.board;
            board.dispatchEvent(e);
            hud.dispatchEvent(new GameEvent(board, Events.boardCreated));
            add(board);
        } else if (e.getID() == Events.clearSelection)
            hud.dispatchEvent(new GameEvent(this, Events.clearSelection));
    }

}
