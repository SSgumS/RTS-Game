package Game.Map;

import Addresses.Addresses;
import GameEvent.GameEvent;
import GameEvent.Events;
import Map.GameBoard;
import Map.GameCell;
import Player.Player;
import Season.Season;
import Units.Units;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Saeed on 7/5/2017.
 */
public class Board extends GameBoard {

    public Board(LayoutManager layout, boolean isDoubleBuffered, boolean isThreadRunning, int mapSize, Season season, int originalWidth, int originalHeight, int originalXo, int originalYo, int width, int height, int xo, int yo, GameCell[][] cells, GameCell selectedCell, double zoom, boolean zoomChanged, Player[] players, Player currentPlayer) {
        super(layout, isDoubleBuffered, isThreadRunning, mapSize, season, originalWidth, originalHeight, originalXo, originalYo, width, height, xo, yo, cells, selectedCell, zoom, zoomChanged, players, currentPlayer);

        Addresses.board = this;
    }

    @Override
    protected void paintComponent(Graphics g) {
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
                image = unit.getImage();
                g.drawImage(image, unit.getOriginX() + width/2 - (int) (zoom*image.getWidth()) + xo, unit.getOriginY() + height/2 - (int) (zoom*image.getHeight()) + yo,  (int) (zoom*image.getWidth()), (int) (zoom*image.getHeight()), null);
            }
        }

        if (zoomChanged)
            zoomChanged = false;
    }

}
