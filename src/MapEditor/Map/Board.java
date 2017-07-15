package MapEditor.Map;

import Map.GameBoard;
import Addresses.Addresses;
import GameEvent.*;
import GameEvent.UnitSelectEvent;
import MapEditor.Map.Cell.Cell;
import MapEditor.Map.Cell.UndoRedoCell;
import Player.Player;
import Season.Season;
import MapEditor.Stack.Redo;
import MapEditor.Stack.Undo;
import Terrain.Terrain;
import Units.Human.Human;
import Units.Resource.Resource;
import Units.Units;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Saeed on 5/23/2017.
 */
public class Board extends GameBoard {

    private Undo undo = new Undo();
    private Redo redo = new Redo();

    public Board(LayoutManager layout, boolean isDoubleBuffered, int mapSize, Terrain terrain, Season season, int playerNumber) {
        super(layout, isDoubleBuffered);

        Addresses.undo = undo;
        Addresses.redo = redo;

        this.mapSize = mapSize;
        this.season = season;
        kind = Terrain.Grass;
        kindImage = Terrain.Grass.getEditorImage(0, 0, season);

        players = new Player[playerNumber];
        for (int i = 0; i < players.length; i++) {
            Color color = null;

            switch (i) {
                case 0:
                    color = Color.BLUE;
                    break;
                case 1:
                    color = Color.RED;
                    break;
                case 2:
                    color = Color.BLACK;
                    break;
                case 3:
                    color = Color.MAGENTA;
                    break;
            }

            players[i] = new Player(color, i);
        }
        currentPlayer = players[0];

        cells = new Cell[mapSize][mapSize];
        for(int i = 0; i < mapSize; i++) {
            for(int j = 0; j < mapSize; j++) {
                int[] xs = {j*width/2+i*width/2, width/2+j*width/2+i*width/2, width+j*width/2+i*width/2, width/2+j*width/2+i*width/2};
                int[] ys = {-j*height/2+i*height/2, -height/2-j*height/2+i*height/2, -j*height/2+i*height/2, height/2-j*height/2+i*height/2};
                cells[i][j] = new Cell(i, j, xs, ys, terrain);
            }
        }

        FocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (Addresses.frame.isFocused()) {
                FocusManager.getCurrentKeyboardFocusManager().redispatchEvent(Addresses.board, e);
                return true;
            }

            return false;
        });
        addKeyListener(this);
    }

    public int getPlayersNumber() {
        return players.length;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayer.getPlayerNumber();
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

        for (Units unit : Units.getUnits()) {
            if (zoomChanged)
                unit.dispatchEvent(new GameEvent(this, Events.zoom));

            if (unit.getOriginX() + xo > -5*width && unit.getOriginX() + xo < getWidth() + 5*width && unit.getOriginY() + yo > -5*height && unit.getOriginY() + yo < getHeight() + 5*height) {
                image = unit.getEditorImage(season);
                g.drawImage(image, unit.getX() + xo, unit.getY() + yo,  (int) (zoom*image.getWidth()), (int) (zoom*image.getHeight()), null);
            }
        }

        try {
            g.drawImage(kindImage, selectedCell.getOriginX() - kindXHint + xo, selectedCell.getOriginY() - kindYHint + yo, (int) (zoom*kindImage.getWidth()), (int) (zoom*kindImage.getHeight()), null);
        } catch (NullPointerException ignored) {}

        if (zoomChanged)
            zoomChanged = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (kind instanceof Terrain) {
                UndoRedoCell cell = new UndoRedoCell(((Cell) selectedCell).clone());
                undo.push(cell);
                Addresses.redo.removeAll();

                selectedCell.dispatchEvent(new SetEvent(this, Events.setKind, kind));

                for (Player player : players)
                    player.dispatchEvent(new SetKindEvent(this, Events.clearKind, selectedCell, cell));

                Resource.clearKind(selectedCell.getShape(), cell);
            } else {
                StringBuilder fullName = new StringBuilder(((Class) kind).getName());
                String name = fullName.substring(fullName.lastIndexOf(".") + 1);

                Units unit = Units.getUnit(name, selectedCell, currentPlayer);

                if (unit.isAllowed(selectedCell.getI(), selectedCell.getJ()))
                    unit.dispatchEvent(new GameEvent(this, Events.setKind));
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (kind instanceof Terrain) {
                if (selectedCell.getTerrain() != kind || !selectedCell.hasUnit()) {
                    UndoRedoCell cell = new UndoRedoCell(((Cell) selectedCell).clone());
                    undo.push(cell);
                    Addresses.redo.removeAll();

                    selectedCell.dispatchEvent(new SetEvent(this, Events.setKind, kind));

                    for (Player player : players)
                        player.dispatchEvent(new SetKindEvent(this, Events.clearKind, selectedCell, cell));

                    Resource.clearKind(selectedCell.getShape(), cell);
                }
            } else {
                StringBuilder fullName = new StringBuilder(((Class) kind).getName());
                String name = fullName.substring(fullName.lastIndexOf(".") + 1);

                Units unit = Units.getUnit(name, selectedCell, currentPlayer);

                if (unit.isAllowed(selectedCell.getI(), selectedCell.getJ()))
                    unit.dispatchEvent(new GameEvent(this, Events.setKind));
            }
        }
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn) {
            if (!isThreadRunning) {
                isThreadRunning = true;
                new Thread(this).start();
            }
        } else if (e.getID() == Events.generateMap) {
            originalXo = xo = -mapSize*width/2 + getWidth()/2;
            originalYo = yo = getHeight()/2;

            Resource.getResources().removeAllElements();
            Units.getUnits().removeAllElements();

            for (Class unitClass : Addresses.unitsClass)
                try {
                    unitClass.getMethod("setStaticHints").invoke(null);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                    e1.printStackTrace();
                }
        } else if (e.getID() == Events.unitSelect) {
            synchronized (this) {
                kind = ((UnitSelectEvent) e).getUnit();

                if (kind instanceof Terrain) {
                    BufferedImage image = ((Terrain) kind).getEditorImage(0, 0, season);

                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D g2 = bufferedImage.createGraphics();
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                    g2.drawImage(image, 0, 0, null);
                    g2.dispose();

                    kindImage = bufferedImage;

                    kindXHint = 0;
                    kindYHint = 0;
                } else {
                    try {
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
            }
        } else if (e.getID() == Events.currentPlayer) {
            JRadioButton source = (JRadioButton) e.getSource();

            switch (source.getText()) {
                case "Player 1":
                    currentPlayer = players[0];
                    break;
                case "Player 2":
                    currentPlayer = players[1];
                    break;
                case "Player 3":
                    currentPlayer = players[2];
                    break;
                case "Player 4":
                    currentPlayer = players[3];
                    break;
            }
        } else if (e.getID() == Events.load) {
            Addresses.undo = undo;
            Addresses.redo = redo;

            if (kind instanceof Terrain) {
                BufferedImage image = ((Terrain) kind).getEditorImage(0, 0, season);

                BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2 = bufferedImage.createGraphics();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.drawImage(image, 0, 0, null);
                g2.dispose();

                kindImage = bufferedImage;
            } else
                try {
                    BufferedImage image = (BufferedImage) ((Class) kind).getMethod("getStaticEditorImage", Season.class).invoke(null, season);

                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D g2 = bufferedImage.createGraphics();
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                    g2.drawImage(image, 0, 0, null);
                    g2.dispose();

                    kindImage = bufferedImage;
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                    e1.printStackTrace();
                }

            for (Class unitClass : Addresses.unitsClass)
                try {
                    unitClass.getMethod("setStaticHints").invoke(null);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                    e1.printStackTrace();
                }
        } else if (e.getID() == Events.setUnitImages) {
            for (Units unit : Units.getUnits())
                if (unit instanceof Human)
                    unit.dispatchEvent(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object object;
        UndoRedoCell undoRedoCell;
        Units unit;


        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
            if (e.isShiftDown() && !redo.isEmpty()) {
                object = redo.pop();
                if (object instanceof UndoRedoCell) {
                    undoRedoCell = (UndoRedoCell) object;
                    Cell cell = undoRedoCell.getCell();

                    UndoRedoCell undoRedoCell1 = new UndoRedoCell(((Cell) cells[cell.getI()][cell.getJ()]).clone());
                    undoRedoCell1.setUnit(undoRedoCell.getUnit());
                    undo.push(undoRedoCell1);

                    undoRedoCell.dispatchEvent(new GameEvent(this, Events.clearKind));
                    cells[cell.getI()][cell.getJ()] = cell;
                    for (int i = -1; i <= 1; i++)
                        for (int j = -1; j <= 1; j++)
                            if (i != 0 || j != 0) {
                                try {
                                    cells[cell.getI() + i][cell.getJ() + j].dispatchEvent(new GameEvent(this, Events.cellRefactor));
                                } catch (ArrayIndexOutOfBoundsException ignored) {}
                            }
                } else {
                    unit = (Units) object;
                    unit.dispatchEvent(new GameEvent(this, Events.setKindStack));
                    undo.push(unit);
                }
            } else if (!e.isShiftDown() && !undo.isEmpty()) {
                object = undo.pop();
                if (object instanceof UndoRedoCell) {
                    undoRedoCell = (UndoRedoCell) object;
                    Cell cell = undoRedoCell.getCell();

                    UndoRedoCell undoRedoCell1 = new UndoRedoCell(((Cell) cells[cell.getI()][cell.getJ()]).clone());
                    undoRedoCell1.setUnit(undoRedoCell.getUnit());
                    redo.push(undoRedoCell1);

                    undoRedoCell.dispatchEvent(new GameEvent(this, Events.setKind));
                    cells[cell.getI()][cell.getJ()] = cell;
                    for (int i = -1; i <= 1; i++)
                        for (int j = -1; j <= 1; j++)
                            if (i != 0 || j != 0) {
                                try {
                                    cells[cell.getI() + i][cell.getJ() + j].dispatchEvent(new GameEvent(this, Events.cellRefactor));
                                } catch (ArrayIndexOutOfBoundsException ignored) {}
                            }
                } else {
                    unit = (Units) object;
                    unit.dispatchEvent(new GameEvent(this, Events.clearKind));
                    redo.push(unit);
                }
            }
        }
    }

}
