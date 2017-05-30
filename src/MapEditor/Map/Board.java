package MapEditor.Map;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.GameEvent.UnitSelectEvent;
import MapEditor.MainFrame.MainFrame;
import MapEditor.Map.Cell.Cell;
import MapEditor.Season.Season;
import MapEditor.Units.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by Saeed on 5/23/2017.
 */
public class Board extends JPanel implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener {

    private boolean isThreadRunning = false;
    public int mapSize;
    public Season season;
    public int originalWidth = 96, originalHeight = 48;
    private int originalXo, originalYo;
    public int width = 96, height = 48;
    public int xo, yo;
    public Cell[][] cells;
    private Cell selectedCell;
    private UnitsInterface kind = Terrain.Grass;
    private double zoom = 1;
    private boolean zoomChanged = false;

    public Board(LayoutManager layout, boolean isDoubleBuffered, int mapSize, Terrain terrain, Season season) {
        super(layout, isDoubleBuffered);
        Addresses.board = this;

        this.mapSize = mapSize;
        this.season = season;

        cells = new Cell[mapSize][mapSize];
        for(int i = 0; i < mapSize; i++) {
            for(int j = 0; j < mapSize; j++) {
                int[] xs = {j*width/2+i*width/2, width/2+j*width/2+i*width/2, width+j*width/2+i*width/2, width/2+j*width/2+i*width/2};
                int[] ys = {-j*height/2+i*height/2, -height/2-j*height/2+i*height/2, -j*height/2+i*height/2, height/2-j*height/2+i*height/2};
                cells[i][j] = new Cell(i, j, xs, ys, terrain);
            }
        }

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setBackground(Color.black);
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage image;

        for (int j = mapSize - 1; j >= 0; j--) {
            for (int i = 0; i < mapSize; i++) {
                if (zoomChanged)
                    cells[i][j].zoom(zoom);

                if (cells[i][j].getOriginX() + xo > -5*width && cells[i][j].getOriginX() + xo < getWidth() + 5*width && cells[i][j].getOriginY() + yo > -5*height && cells[i][j].getOriginY() + yo < getHeight() + 5*height) {
                    image = cells[i][j].getTerrainImage();
                    g.drawImage(image, cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, (int) (zoom*image.getWidth()), (int) (zoom*image.getHeight()), null);

                    try {
                        if (!cells[i][j].hasParent()) {
                            UnitsInterface kind = cells[i][j].getKind();
                            image = kind.getImage(i, j, season);
                            g.drawImage(image, (int) (cells[i][j].getOriginX() - zoom*kind.getXHint() + xo), (int) (cells[i][j].getOriginY() - zoom*kind.getYHint() + yo), (int) (zoom*image.getWidth()), (int) (zoom*image.getHeight()), null);
                        }
                    } catch (NullPointerException ignored) {}

                    if (cells[i][j].getShape().contains(Addresses.panel.mouseX - xo, Addresses.panel.mouseY - getY() - yo))
                        selectedCell = cells[i][j];
                }
            }
        }

        try {
            image = kind.getImage(0, 0, season);

            BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.drawImage(image, 0, 0, null);
            g2.dispose();

            g.drawImage(bufferedImage, (int) (selectedCell.getOriginX() - zoom*kind.getXHint() + xo), (int) (selectedCell.getOriginY() - zoom*kind.getYHint() + yo), (int) (zoom*bufferedImage.getWidth()), (int) (zoom*bufferedImage.getHeight()), null);
        } catch (NullPointerException ignored) {}

        if (zoomChanged)
            zoomChanged = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if ("Terrain".equals(kind.getSource())) {
            selectedCell.setTerrain(kind);
            selectedCell.clearKind();
        } else if (!selectedCell.hasKind() && kind.isAllowed((Terrain) selectedCell.getTerrain()))
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
            selectedCell.clearKind();
        } else if (!selectedCell.hasKind() && kind.isAllowed((Terrain) selectedCell.getTerrain()))
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
        while(Addresses.panel.mouseY == 0 || Addresses.panel.mouseY == MainFrame.height - 1 || Addresses.panel.mouseX == 0 || Addresses.panel.mouseX == getWidth() - 1 || yo > -mapSize*height/2 + getHeight() || xo > -mapSize*width + getWidth()) {
            if(Addresses.panel.mouseY == 0 && yo < mapSize*height/2) {
                yo += zoom*48;
                originalYo += 48;
            } else if(Addresses.panel.mouseY == MainFrame.height - 1 && yo > -mapSize*height/2 + getHeight()) {
                yo -= zoom*48;
                originalYo -= 48;
            } else if(Addresses.panel.mouseX == 0 && xo < 0) {
                xo += zoom*48;
                originalXo += 48;
            } else if(Addresses.panel.mouseX == getWidth() - 1 && xo > -mapSize*width + getWidth()) {
                xo -= zoom*48;
                originalXo -= 48;
            }

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
            originalXo = xo = -mapSize*width/2 + getWidth()/2;
            originalYo = yo = getHeight()/2;
        } else if (e.getID() == Events.unitSelect)
            kind = ((UnitSelectEvent) e).getUnit();
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
