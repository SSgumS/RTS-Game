package Map;

import Addresses.Addresses;
import GameEvent.*;
import GameFrame.Frame;
import Player.Player;
import Season.Season;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Saeed on 6/5/2017.
 */
public class GameBoard extends JPanel implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    protected boolean isThreadRunning = false;
    public int mapSize;
    public Season season;
    public int originalWidth = 96, originalHeight = 48;
    protected int originalXo, originalYo;
    public int width = 96, height = 48;
    public int xo, yo;
    public GameCell[][] cells;
    protected GameCell selectedCell;
    public double zoom = 1;
    protected boolean zoomChanged = true;
    protected Player[] players;
    protected Player currentPlayer;
    protected Object kind;
    protected transient BufferedImage kindImage;
    protected int kindXHint;
    protected int kindYHint;

    public GameBoard(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setBackground(Color.BLACK);

        Addresses.board = this;
    }

    public GameBoard(LayoutManager layout, boolean isDoubleBuffered, boolean isThreadRunning, int mapSize, Season season, int originalWidth, int originalHeight, int originalXo, int originalYo, int width, int height, int xo, int yo, GameCell[][] cells, GameCell selectedCell, double zoom, boolean zoomChanged, Player[] players, Player currentPlayer) {
        super(layout, isDoubleBuffered);
        this.isThreadRunning = isThreadRunning;
        this.mapSize = mapSize;
        this.season = season;
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.originalXo = originalXo;
        this.originalYo = originalYo;
        this.width = width;
        this.height = height;
        this.xo = xo;
        this.yo = yo;
        this.cells = cells;
        this.selectedCell = selectedCell;
        this.zoom = zoom;
        this.zoomChanged = zoomChanged;
        this.players = players;
        this.currentPlayer = currentPlayer;

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setBackground(Color.BLACK);

        Addresses.board = this;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        Addresses.panel.mouseX = e.getX();
        Addresses.panel.mouseY = e.getY() + getY();

        if (!isThreadRunning && (Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1 || Addresses.panel.mouseY == 0 || Addresses.panel.mouseY == Frame.height - 1)) {
            isThreadRunning = true;
            new Thread(this).start();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Addresses.panel.mouseX = e.getX();
        Addresses.panel.mouseY = e.getY() + getY();

        if (!isThreadRunning && (Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1)) {
            isThreadRunning = true;
            new Thread(this).start();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int mouseWheel;
        if (-e.getPreciseWheelRotation() > 0)
            mouseWheel = (int) Math.ceil(-e.getPreciseWheelRotation());
        else
            mouseWheel = (int) -e.getPreciseWheelRotation();

        zoom += (double) mouseWheel/4;

        if (zoom < 0.5)
            zoom = 0.5;
        else if (zoom > 2)
            zoom = 2;

        zoomChanged = true;

        xo = (int) -(zoom*(Addresses.panel.mouseX - originalXo) - Addresses.panel.mouseX);
        yo = (int) -(zoom*(Addresses.panel.mouseY - getY() - originalYo) - (Addresses.panel.mouseY - getY()));

        width = (int) (zoom*originalWidth);
        height = (int) (zoom*originalHeight);

        Addresses.hud.dispatchEvent(new GameEvent(this, Events.zoom));

        try {
            for (Class unitClass : Addresses.unitsClass)
                unitClass.getMethod("setStaticHints").invoke(null);

            if (kind != null && kind instanceof Class) {
                kindXHint = (int) ((Class) kind).getField("staticXHint").get(null);
                kindYHint = (int) ((Class) kind).getField("staticYHint").get(null);
            }
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        while(Addresses.panel.mouseY == 0 || Addresses.panel.mouseY == Frame.height - 1 || Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1) {
            if(Addresses.panel.mouseY == 0 && yo < mapSize*height/2) {
                yo += zoom*48;
                originalYo += 48;
            } else if(Addresses.panel.mouseY == Frame.height - 1 && yo > -mapSize*height/2 + getHeight()) {
                yo -= zoom*48;
                originalYo -= 48;
            } else if(Addresses.panel.mouseX == 0 && xo < 0) {
                xo += zoom*48;
                originalXo += 48;
            } else if(Addresses.panel.mouseX == getWidth() - 1 && xo > -mapSize*width + getWidth()) {
                xo -= zoom*48;
                originalXo -= 48;
            }

            try {
                wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isThreadRunning = false;
    }

    public boolean isThreadRunning() {
        return isThreadRunning;
    }

    public int getMapSize() {
        return mapSize;
    }

    public Season getSeason() {
        return season;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public int getOriginalXo() {
        return originalXo;
    }

    public int getOriginalYo() {
        return originalYo;
    }

    public int getXo() {
        return xo;
    }

    public int getYo() {
        return yo;
    }

    public GameCell[][] getCells() {
        return cells;
    }

    public GameCell getSelectedCell() {
        return selectedCell;
    }

    public double getZoom() {
        return zoom;
    }

    public boolean isZoomChanged() {
        return zoomChanged;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

}
