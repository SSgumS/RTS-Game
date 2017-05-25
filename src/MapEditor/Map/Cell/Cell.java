package MapEditor.Map.Cell;

import MapEditor.Units.UnitsInterface;

import java.awt.*;

public class Cell {

	private UnitsInterface kind;
	private UnitsInterface terrain;
	private Polygon shape;

	public Cell(int[] xs, int[] ys, UnitsInterface terrain) {
		shape = new Polygon(xs, ys, 4);
		this.terrain = terrain;
	}

    public Polygon getShape() {
		return shape;
	}

	public int getOriginY() {
		return shape.ypoints[0];
	}

	public int getOriginX() {
		return shape.xpoints[3];
	}

    public UnitsInterface getTerrain() {
        return terrain;
    }

    public void setTerrain(UnitsInterface terrain) {
        this.terrain = terrain;
    }

    public void setKind(UnitsInterface kind) {
		this.kind = kind;
	}

    public UnitsInterface getKind() {
		return kind;
	}
	
}
