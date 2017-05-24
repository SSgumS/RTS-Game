package MapEditor.Map.Cell;

import MapEditor.Units.Unit;

import java.awt.*;

public class Cell {

	private Unit kind;
	private Unit terrain;
	private Polygon shape;

	public Cell(int[] xs, int[] ys, Unit terrain) {
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

    public Unit getTerrain() {
        return terrain;
    }

    public void setTerrain(Unit terrain) {
        this.terrain = terrain;
    }

    public void setKind(Unit kind) {
		this.kind = kind;
	}

    public Unit getKind() {
		return kind;
	}
	
}
