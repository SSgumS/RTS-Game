package MapEditor.Map.Cell;

import MapEditor.Addresses.Addresses;
import MapEditor.Units.Terrain;
import MapEditor.Units.UnitsInterface;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

public class Cell implements Serializable {

	private int i , j;
	private UnitsInterface kind;
	private UnitsInterface terrain;
	private Polygon shape;
	private Cell parent;
	private int[] originalXs, originalYs;
	private Vector <Cell> relatedCells = new Vector<>();

	public Cell(int i, int j, int[] xs, int[] ys, UnitsInterface terrain) {
	    this.i = i;
	    this.j = j;
	    originalXs = xs;
	    originalYs = ys;
		shape = new Polygon(xs, ys, 4);
		this.terrain = terrain;
	}

    public Polygon getShape() {
		return shape;
	}

	public int getOriginY() {
		return shape.ypoints[1];
	}

	public int getOriginX() {
		return shape.xpoints[0];
	}

    public UnitsInterface getTerrain() {
        return terrain;
    }

    public void setTerrain(UnitsInterface terrain) {
        this.terrain = terrain;
    }

    public void setKind(UnitsInterface kind) {
	    boolean canSet = true;

        int size = kind.getSize();

        for (int k = 0; k < size; k++)
            for (int l = 0; l < size; l++) {
                Cell cell = Addresses.board.cells[i - k][j + l];
                if (cell.hasKind() || !kind.isAllowed((Terrain) cell.getTerrain())) {
                    canSet = false;
                    break;
                }
            }

        if (canSet)
            for (int k = 0; k < size; k++)
                for (int l = 0; l < size; l++) {
                    Cell cell = Addresses.board.cells[i - k][j + l];
                    cell.kind = kind;
                    if (cell != this)
                        cell.parent = this;
                    relatedCells.add(cell);
                }
	}

    public UnitsInterface getKind() {
		return kind;
	}

    public boolean hasKind() {
        return kind != null;
    }

	public void clearKind() {
		if (parent != null) {
            parent.clearKind();
            parent = null;
        } else
            for (Cell cell : relatedCells)
                cell.kind = null;
	}

    public boolean hasParent() {
        return parent != null;
    }

    public void zoom(double zoom) {
        for (int k = 0; k < 4; k++) {
            shape.xpoints[k] = (int) (zoom*originalXs[k]);
            shape.ypoints[k] = (int) (zoom*originalYs[k]);
        }
        shape.invalidate();
    }

}
