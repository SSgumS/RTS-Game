package MapEditor.GamePanel;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.GameEvent.GenerateMapEvent;
import MapEditor.HUD.HUD;
import MapEditor.MainFrame.MainFrame;
import MapEditor.Map.Board;
import MapEditor.MenuBar.Menu.MenuDialog;
import MapEditor.MenuBar.MenuBar;
import MapEditor.Season.Season;
import MapEditor.Units.Terrain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Saeed on 5/15/2017.
 */
public class GamePanel extends JPanel implements MouseMotionListener, Runnable {

    private MenuBar menuBar;
    private Board board;
    private HUD hud;
    private MenuDialog menu = new MenuDialog(Addresses.frame, true);
    public int mouseX, mouseY;

    public GamePanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setSize(MainFrame.width, MainFrame.height);
        setLocation(0, 0);

        Addresses.panel = this;

        addMenuBar();
        addHUD();
        addBoard();

        addMouseMotionListener(this);

        new Thread(this).start();
//        repaint();
    }

    private void addMenuBar() {
        menuBar = new MenuBar(null);
        add(menuBar);
    }

    private void addBoard() {
        board = new Board(null, true, 75, Terrain.Grass, Season.Spring, 2);
        board.setSize(getWidth(), hud.getY() - menuBar.getHeight());
        board.setLocation(0, menuBar.getHeight());
        board.dispatchEvent(new GameEvent(this, Events.setOrigin));
        hud.dispatchEvent(new GameEvent(board, Events.boardCreated));
        add(board);
    }

    private void addHUD() {
        hud = new HUD(null);
        Addresses.hud = hud;
        add(hud);
    }

    @Override
    public void run() {
        while (true) {
            repaint();

            try {
                Thread.sleep(32);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
            board.dispatchEvent(new GameEvent(this, Events.setOrigin));
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

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (mouseY == 0 || mouseY == MainFrame.height - 1 || mouseX == 0 || mouseX == MainFrame.width - 1)
            board.dispatchEvent(new GameEvent(this, Events.actionOn));
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

//    private void render() {
//        dbImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
//        Graphics g = dbImage.getGraphics();
//
//        g.drawImage(hud.getImage(), hud.getX(), hud.getY(), null);
//        g.drawImage(menuBar.getImage(), menuBar.getX(), menuBar.getY(), null);
//    }

//    private void paint() {
//        Graphics g = getGraphics();
//
//        g.drawImage(dbImage, 0, 0, null);
//        paintComponents(g);
//
//        g.dispose();
//    }

}
