package MapEditor.Player;

import MapEditor.Map.Cell.Cell;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Saeed on 5/30/2017.
 */
public class Player implements Serializable {

    private Color color;
    private Vector <Cell> cells = new Vector<>();

    public Player(Color color) {
        this.color = color;
    }

    public void addCell(Cell cell) {
        cells.addElement(cell);
    }

    public void removeCell(Cell cell) {
        cells.removeElement(cell);
    }

    public Color getColor() {
        return color;
    }

}
