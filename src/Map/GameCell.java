package Map;

import Addresses.Addresses;
import GameEvent.Events;
import Terrain.Terrain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by Saeed on 6/5/2017.
 */
public class GameCell extends JComponent implements Serializable {

    protected int i , j;
    protected Terrain terrain;
    protected Polygon shape;
    protected int[] originalXs, originalYs;
    protected transient BufferedImage image;

    public Polygon getShape() {
        return shape;
    }

    public int getOriginY() {
        return shape.ypoints[1];
    }

    public int getOriginX() {
        return shape.xpoints[0];
    }

    public int getOriginalOriginY() {
        return originalYs[1];
    }

    public int getOriginalOriginX() {
        return originalXs[0];
    }

    public int[] getOriginalXs() {
        return originalXs;
    }

    public int[] getOriginalYs() {
        return originalYs;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public BufferedImage getTerrainImage() {
        return image;
    }

    public void zoom() {
        double zoom = Addresses.board.zoom;
        for (int k = 0; k < 4; k++) {
            shape.xpoints[k] = (int) (zoom*originalXs[k]);
            shape.ypoints[k] = (int) (zoom*originalYs[k]);
        }
        shape.invalidate();
    }

    public Color getColor() {
        return terrain.getColor();
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.zoom:
                zoom();
                break;
        }
    }

}
