package Game.Map;

import Addresses.Addresses;
import GameEvent.*;
import Map.GameBoard;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Units.Building.Building;
import Units.Human.Human;
import Units.Human.Worker.Worker;
import Units.Resource.Resource;
import Units.Units;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

/**
 * Created by Saeed on 7/5/2017.
 */
public class Board extends GameBoard {

    private BufferedImage[] images;
    private Rectangle selectionBound;
    private Point clickedPoint;
    private Vector<Units> selectedUnits = new Vector<Units>();
    private Units activeUnit;

    private Timer seasonTimer = new Timer(10000, e -> {
        switch (season) {
            case Spring:
                season = Season.Summer;
                break;
            case Summer:
                season = Season.Autumn;
                break;
            case Autumn:
                season = Season.Winter;
                for (int i = 0; i < mapSize; i++)
                    for (int j = 0; j < mapSize; j++)
                        cells[i][j].dispatchEvent(new GameEvent(this, Events.seasonChanged));
                break;
            case Winter:
                season = Season.Spring;
                for (int i = 0; i < mapSize; i++)
                    for (int j = 0; j < mapSize; j++)
                        cells[i][j].dispatchEvent(new GameEvent(this, Events.seasonChanged));
                break;
        }

        for (int i = 0; i < Units.getUnits().size(); i++)
            Units.getUnits().elementAt(i).dispatchEvent(new GameEvent(this, Events.seasonChanged));
    });

    public Board(LayoutManager layout, boolean isDoubleBuffered, boolean isThreadRunning, int mapSize, Season season, int originalWidth, int originalHeight, int originalXo, int originalYo, int width, int height, int xo, int yo, GameCell[][] cells, GameCell selectedCell, double zoom, boolean zoomChanged, Player[] players, Player currentPlayer) {
        super(layout, isDoubleBuffered, isThreadRunning, mapSize, season, originalWidth, originalHeight, originalXo, originalYo, width, height, xo, yo, cells, selectedCell, zoom, zoomChanged, players, currentPlayer);

        File[] files = new File("resources\\images\\ui\\health").listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++)
                images[i] = ImageIO.read(files[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Units unit : Units.getUnits()) {
            if (unit instanceof Human)
                new Thread((Human) unit).start();
            else if (unit instanceof Building) {
                ((Building) unit).setConstructed();
                ((Building) unit).setThread();
            }
        }

        try {
            for (Class unitClass : Addresses.unitsClass)
                unitClass.getMethod("setStaticHints").invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        seasonTimer.start();
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage image;

        for (int j = mapSize - 1; j >= 0; j--) {
            for (int i = 0; i < mapSize; i++) {
                try {
                    if (zoomChanged)
                        cells[i][j].dispatchEvent(new GameEvent(this, Events.zoom));

                    if (cells[i][j].getOriginX() + xo > -5*width && cells[i][j].getOriginX() + xo < getWidth() + 5*width && cells[i][j].getOriginY() + yo > -5*height && cells[i][j].getOriginY() + yo < getHeight() + 5*height) {
                        image = cells[i][j].getTerrainImage();
                        g.drawImage(image, cells[i][j].getOriginX() + xo, cells[i][j].getOriginY() + yo, (int) (zoom*image.getWidth()), (int) (zoom*image.getHeight()), null);

                        if (cells[i][j].getShape().contains(Addresses.panel.mouseX - xo, Addresses.panel.mouseY - getY() - yo))
                            selectedCell = cells[i][j];
                    }
                } catch (NullPointerException ignored) {}
            }
        }

        Units unit;
        for (int i = 0; i < Units.getUnits().size(); i++) {
//        try {
            unit = Units.getUnits().elementAt(i);
//            for (Units unit : Units.getUnits()) {
            if (zoomChanged)
                unit.dispatchEvent(new GameEvent(this, Events.zoom));

            if (unit.getOriginX() + xo > -5 * width && unit.getOriginX() + xo < getWidth() + 5 * width && unit.getOriginY() + yo > -5 * height && unit.getOriginY() + yo < getHeight() + 5 * height) {
                try {
                    image = unit.getImage();
                    g.drawImage(image, unit.getX() + xo, unit.getY() + yo, (int) (zoom * image.getWidth()), (int) (zoom * image.getHeight()), null);
                } catch (NullPointerException ignored) {}
            }
//            }
//        } catch (ConcurrentModificationException ignored) {}
        }

        for (Units selectedUnit : selectedUnits) {
            double healthPercentage;
            healthPercentage = (double) selectedUnit.getHealth()/selectedUnit.getHealthCapacity();

            image = images[25 - (int) (healthPercentage*25)];
            g.drawImage(image, selectedUnit.getOriginX() + selectedUnit.getUnitSize()*width/2 - (int) (zoom*image.getWidth()/2) + xo, selectedUnit.getOriginY() + selectedUnit.getUnitSize()*height + yo,  (int) (zoom*image.getWidth()), (int) (zoom*image.getHeight()), null);
        }

        try {
            g.drawRect(selectionBound.x, selectionBound.y, selectionBound.width, selectionBound.height);
        } catch (NullPointerException ignored) {}

        try {
            g.drawImage(kindImage, selectedCell.getOriginX() - kindXHint + xo, selectedCell.getOriginY() - kindYHint + yo, (int) (zoom*kindImage.getWidth()), (int) (zoom*kindImage.getHeight()), null);
        } catch (NullPointerException ignored) {}

        if (zoomChanged)
            zoomChanged = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if(SwingUtilities.isLeftMouseButton(e) && kind == null)
            select(e);
        else if (SwingUtilities.isLeftMouseButton(e)) {
            StringBuilder fullName = new StringBuilder(((Class) kind).getName());
            String name = fullName.substring(fullName.lastIndexOf(".") + 1);

            Units unit = Units.getUnit(name, selectedCell, currentPlayer);

            if (unit.isAllowed(selectedCell.getI(), selectedCell.getJ())) {
                unit.dispatchEvent(new GameEvent(this, Events.setKindStack));

                for (Units worker : selectedUnits)
                    if (worker instanceof Worker && worker.getOwner().equals(currentPlayer))
                        worker.dispatchEvent(new GameEvent(this, Events.order));

                kind = null;
                kindImage = null;
            }
        } else if(SwingUtilities.isRightMouseButton(e))
            for (Units unit : selectedUnits)
                if ((unit instanceof Human || unit instanceof Building) && unit.getOwner().equals(currentPlayer))
                    unit.dispatchEvent(new GameEvent(this, Events.order));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (kind == null) {
            Point dragPoint = e.getPoint();
            int x = Math.min(clickedPoint.x, dragPoint.x);
            int y = Math.min(clickedPoint.y, dragPoint.y);
            int width = Math.max(clickedPoint.x - dragPoint.x, dragPoint.x - clickedPoint.x);
            int height = Math.max(clickedPoint.y - dragPoint.y, dragPoint.y - clickedPoint.y);
            selectionBound = new Rectangle(x, y, width, height);

            multiSelect(e, x, y, width, height);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        clickedPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        clickedPoint = null;
        selectionBound = null;
    }

    private void select(MouseEvent e) {
        if(!e.isControlDown()) {
            for (Units unit : selectedUnits)
                if ((unit instanceof Human || unit instanceof Building) && unit.getOwner().equals(currentPlayer))
                    unit.dispatchEvent(new GameEvent(this, Events.unitDeselect));

            selectedUnits.removeAllElements();
        }

        Vector <Units> units = Units.getUnits();
        for (int i = units.size() - 1; i >= 0; i--) {
            Units unit = units.elementAt(i);
            if (unit.getShape().contains(Addresses.panel.mouseX - xo, Addresses.panel.mouseY - getY() - yo) && unit.isAlive()) {
                selectedUnits.addElement(unit);
                if ((unit instanceof Human || unit instanceof Building) && unit.getOwner().equals(currentPlayer))
                    unit.dispatchEvent(new GameEvent(this, Events.unitSelect));
                break;
            }
        }

        if (selectedUnits.size() > 0) {
            for (Units unit : selectedUnits)
                if (unit instanceof Resource || ((unit instanceof Human || (unit instanceof Building && ((Building) unit).isConstructed())) && unit.getOwner().equals(currentPlayer))) {
                    activeUnit = unit;
                    break;
                } else
                    activeUnit = null;
            Addresses.hud.dispatchEvent(new GameEvent(this, Events.unitSelect));
        } else {
            activeUnit = null;
            Addresses.hud.dispatchEvent(new GameEvent(this, Events.actionOff));
        }
    }

    private void multiSelect(MouseEvent e, int x, int y, int width, int height) {
        if(!e.isControlDown()) {
            for (Units unit : selectedUnits)
                if ((unit instanceof Human || unit instanceof Building) && unit.getOwner().equals(currentPlayer))
                    unit.dispatchEvent(new GameEvent(this, Events.unitDeselect));

            selectedUnits.removeAllElements();
        }

        Rectangle temp = new Rectangle(x - xo, y - getY() - yo, width, height);
        Vector <Units> units = Units.getUnits();
        for (int i = units.size() - 1; i >= 0; i--) {
            Units unit = units.elementAt(i);
            if (unit.getShape().intersects(temp) && unit instanceof Human && unit.isAlive()) {
                if (selectedUnits.size() == 14)
                    break;

                selectedUnits.addElement(unit);
                if (unit.getOwner().equals(currentPlayer))
                    unit.dispatchEvent(new GameEvent(this, Events.unitSelect));
            }
        }

        if (selectedUnits.size() != 0) {
            for (Units unit : selectedUnits)
                if (unit instanceof Resource || ((unit instanceof Human || unit instanceof Building) && unit.getOwner().equals(currentPlayer))) {
                    activeUnit = unit;
                    break;
                } else
                    activeUnit = null;
            Addresses.hud.dispatchEvent(new GameEvent(this, Events.unitSelect));
        } else {
            activeUnit = null;
            Addresses.hud.dispatchEvent(new GameEvent(this, Events.actionOff));
        }
    }

    public Vector<Units> getSelectedUnits() {
        return selectedUnits;
    }

    public Units getActiveUnit() {
        return activeUnit;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.actionOn:
                if (!isThreadRunning) {
                    isThreadRunning = true;
                    new Thread(this).start();
                }
                break;
            case Events.clearSelection:
                if (e.getSource().equals(activeUnit)) {
                    activeUnit = null;
                    selectedUnits.removeElement(e.getSource());
                    Addresses.hud.dispatchEvent(new GameEvent(this, Events.actionOff));
                }
                break;
            case Events.unitSelect:
                synchronized (this) {
                    try {
                        kind = ((UnitSelectEvent) e).getUnit();

                        BufferedImage image = (BufferedImage) ((Class) kind).getMethod("getStaticEditorImage", Season.class).invoke(null, season);

                        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                        Graphics2D g2 = bufferedImage.createGraphics();
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                        g2.drawImage(image, 0, 0, null);
                        g2.dispose();

                        kindImage = bufferedImage;

                        kindXHint = (int) ((Class) kind).getField("staticXHint").get(null);
                        kindYHint = (int) ((Class) kind).getField("staticYHint").get(null);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | NullPointerException e1) {
                        e1.printStackTrace();
                    }
                }
                break;
        }
    }

}
