package MapEditor.GamePanel;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.HUD.HUD;
import MapEditor.MainFrame.MainFrame;
import MapEditor.Map.Board;
import MapEditor.MenuBar.Menu.MenuDialog;
import MapEditor.MenuBar.MenuBar;
import MapEditor.Units.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Created by Saeed on 5/15/2017.
 */
public class GamePanel extends JPanel implements MouseMotionListener {

    private BufferedImage dbImage;
    private MenuBar menuBar;
    private Board board;
    private HUD hud;
    private MenuDialog menu = new MenuDialog(Addresses.frame, true);
    public int mouseX, mouseY;

    private Timer repaint = new Timer(16, e -> {
        repaint();
    });

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

        repaint();
    }

    private void addMenuBar() {
        menuBar = new MenuBar(null);
        add(menuBar);
    }

    private void addBoard() {
        board = new Board(null, true, Unit.Grass);
        board.setSize(getWidth(), hud.getY() - menuBar.getHeight());
        board.setLocation(0, menuBar.getHeight());
        board.dispatchEvent(new GameEvent(this, Events.setOrigin));
        add(board);
    }

    private void addHUD() {
        hud = new HUD(null);
        Addresses.hud = hud;
        add(hud);
    }


    private void render() {
        dbImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dbImage.getGraphics();

        g.drawImage(hud.getImage(), hud.getX(), hud.getY(), null);
        g.drawImage(menuBar.getImage(), menuBar.getX(), menuBar.getY(), null);
    }

    private void paint() {
        Graphics g = getGraphics();

        g.drawImage(dbImage, 0, 0, null);
        paintComponents(g);

        g.dispose();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        JButton source = (JButton) e.getSource();

        if (e.getID() == Events.actionOn) {
            switch (source.getText()) {
                case "Menu":
                    menu.setVisible(true);
                    break;
            }
        } else if (e.getID() == Events.actionOff) {
            switch (source.getText()) {
                case "Cancel":
                    menu.setVisible(false);
                    break;
            }
        }
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

}
