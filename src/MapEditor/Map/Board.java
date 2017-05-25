package MapEditor.Map;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.UnitSelectEvent;
import MapEditor.MainFrame.MainFrame;
import MapEditor.Map.Cell.Cell;
import MapEditor.Season.Season;
import MapEditor.Units.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/**
 * Created by Saeed on 5/23/2017.
 */
public class Board extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    private boolean isThreadRunning = false;
    private int mapSize;
    private Season season;
    private int width = 96, height = 48;
    private int xo, yo;
    private Cell[][] cells;
    private Cell selectedCell;
    private UnitsInterface kind = Terrain.Grass;

    public Board(LayoutManager layout, boolean isDoubleBuffered, int mapSize, Terrain terrain, Season season) {
        super(layout, isDoubleBuffered);
        Addresses.board = this;

        this.mapSize = mapSize;
        this.season = season;

        cells = new Cell[mapSize][mapSize];
        for(int i = 0; i < mapSize; i++) {
            for(int j = 0; j < mapSize; j++) {
                int[] xs = {-j*width/2+i*width/2, width/2-j*width/2+i*width/2, -j*width/2+i*width/2, 2-width/2-j*width/2+i*width/2};
                int[] ys = {j*height/2+i*height/2, height/2+j*height/2+i*height/2, height+j*height/2+i*height/2, height/2+j*height/2+i*height/2};
                cells[i][j] = new Cell(xs, ys, terrain);
            }
        }

        addMouseListener(this);
        addMouseMotionListener(this);

        setBackground(Color.black);
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                UnitsInterface terrain = cells[i][j].getTerrain();
                g.drawImage(terrain.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);

                try {
                    UnitsInterface kind = cells[i][j].getKind();
                    g.drawImage(kind.getImage(), cells[i][j].getOriginX() - kind.getXHint() + xo, cells[i][j].getOriginY() - kind.getYHint() + yo, null);
                } catch (NullPointerException ignored) {}

                if (cells[i][j].getShape().contains(Addresses.panel.mouseX - xo, Addresses.panel.mouseY - getY() - yo)) {
                    selectedCell = cells[i][j];
//                    Graphics2D g2 = image.createGraphics();
//                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                    g.drawImage(kind.getImage(), cells[i][j].getOriginX() - kind.getXHint() + xo, cells[i][j].getOriginY() - kind.getYHint() + yo, null);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if ("Terrain".equals(kind.getSource())) {
            selectedCell.setTerrain(kind);
            selectedCell.setKind(null);
        } else
            selectedCell.setKind(kind);

//        Addresses.panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Addresses.panel.mouseX = e.getX();
        Addresses.panel.mouseY = e.getY() + getY();

        if (!isThreadRunning && (Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1)) {
            isThreadRunning = true;
            new Thread(this).start();
        }

        if ("Terrain".equals(kind.getSource())) {
            selectedCell.setTerrain(kind);
            selectedCell.setKind(null);
        } else
            selectedCell.setKind(kind);

//        Addresses.panel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Addresses.panel.mouseX = e.getX();
        Addresses.panel.mouseY = e.getY() + getY();

        if (!isThreadRunning && (Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1)) {
            isThreadRunning = true;
            new Thread(this).start();
        }

//        Addresses.panel.repaint();
    }

    @Override
    public synchronized void run() {
        while(Addresses.panel.mouseY == 0 || Addresses.panel.mouseY == MainFrame.height - 1 || Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1) {
            if(Addresses.panel.mouseY == 0 && yo < 0)
                yo+=25;
            if(Addresses.panel.mouseY == MainFrame.height - 1 && yo > -mapSize*height + getHeight())
                yo-=25;
            if(Addresses.panel.mouseX == 0 && xo < mapSize*width/2)
                xo+=25;
            if(Addresses.panel.mouseX == getWidth() - 1 && xo > -mapSize*width/2 + getWidth())
                xo-=25;

//            Addresses.panel.repaint();

            try {
                wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isThreadRunning = false;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn) {
            if (!isThreadRunning) {
                isThreadRunning = true;
                new Thread(this).start();
            }
        } else if (e.getID() == Events.setOrigin) {
            xo = getWidth()/2;
            yo = -mapSize*height/2 -getHeight()/2;
        } else if (e.getID() == Events.unitSelect)
            kind = ((UnitSelectEvent) e).getUnit();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
