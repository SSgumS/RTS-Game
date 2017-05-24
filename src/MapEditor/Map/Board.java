package MapEditor.Map;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.MainFrame.MainFrame;
import MapEditor.Map.Cell.Cell;
import MapEditor.Units.Terrain;
import MapEditor.Units.Others;
import MapEditor.Units.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Saeed on 5/23/2017.
 */
public class Board extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    private int mapSize = 100;
    private int width = 96, height = 48;
    private int xo, yo;
    private Cell[][] cells = new Cell[mapSize][mapSize];
    private Cell selectedCell;
    private Unit kind = Unit.Water;

    public Board(LayoutManager layout, boolean isDoubleBuffered, Unit terrain) {
        super(layout, isDoubleBuffered);

        for(int i = 0;i < mapSize; i++) {
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

    private void mapGenerator(Unit terrain) {
        for(int i = 0;i < mapSize; i++) {
            for(int j = 0; j < mapSize; j++) {
                cells[i][j].setTerrain(terrain);
            }
        }
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                switch (cells[i][j].getTerrain()) {
                    case DeepWater:
                        g.drawImage(Terrain.DeepWater.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case Dessert:
                        g.drawImage(Terrain.Dessert.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case Dirt:
                        g.drawImage(Terrain.Dirt.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case DirtSnow:
                        g.drawImage(Terrain.DirtSnow.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case Grass:
                        g.drawImage(Terrain.Grass.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case GrassSnow:
                        g.drawImage(Terrain.GrassSnow.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case Ice:
                        g.drawImage(Terrain.Ice.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case Snow:
                        g.drawImage(Terrain.Snow.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                    case Water:
                        g.drawImage(Terrain.Water.getImage(), cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, null);
                        break;
                }

                try {
                    switch (cells[i][j].getKind()) {
                        case SpringTree:
                            g.drawImage(Others.SpringTree.getImage(), cells[i][j].getOriginX() - (Others.SpringTree.getImage().getWidth() - width)/2 + xo, cells[i][j].getOriginY() - (Others.SpringTree.getImage().getHeight() - height/2) + yo, null);
                            break;
                    }
                } catch (NullPointerException ignored) {}

                if (cells[i][j].getShape().contains(Addresses.panel.mouseX - xo, Addresses.panel.mouseY - getY() - yo)) {
                    selectedCell = cells[i][j];
                    int[] selectedCellXS = new int[4];
                    int[] selectedCellYS = new int[4];
                    for (int k = 0; k < 4; k++) {
                        selectedCellXS[k] = selectedCell.getShape().xpoints[k] + xo;
                        selectedCellYS[k] = selectedCell.getShape().ypoints[k] + yo;
                    }
                    g.setColor(new Color(255, 200, 0, 75));
                    g.fillPolygon(selectedCellXS, selectedCellYS, 4);
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

        Addresses.panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Addresses.panel.mouseX = e.getX();
        Addresses.panel.mouseY = e.getY() + getY();

        if (Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1)
            new Thread(this).start();

        if ("Terrain".equals(kind.getSource())) {
            selectedCell.setTerrain(kind);
            selectedCell.setKind(null);
        } else
            selectedCell.setKind(kind);

        Addresses.panel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Addresses.panel.mouseX = e.getX();
        Addresses.panel.mouseY = e.getY() + getY();

        if (Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1)
            new Thread(this).start();

        Addresses.panel.repaint();
    }

    @Override
    public synchronized void run() {
        while(Addresses.panel.mouseY == 0 || Addresses.panel.mouseY == MainFrame.height - 1 || Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1) {
            if(Addresses.panel.mouseY == 0 && yo < 0)
                yo++;
            if(Addresses.panel.mouseY == MainFrame.height - 1 && yo > -mapSize*height + getHeight())
                yo--;
            if(Addresses.panel.mouseX == 0 && xo < mapSize*width/2)
                xo++;
            if(Addresses.panel.mouseX == getWidth() - 1 && xo > -mapSize*width/2 + getWidth())
                xo--;

            Addresses.panel.repaint();

            try {
                wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn)
            new Thread(this).start();
        else if (e.getID() == Events.setOrigin) {
            xo = getWidth()/2;
            yo = -mapSize*height/2 -getHeight()/2;
        }
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
