package MapEditor.Map.Cell;

import MapEditor.Addresses.Addresses;
import MapEditor.GameEvent.Events;
import MapEditor.GameEvent.GameEvent;
import MapEditor.Player.Player;
import MapEditor.Units.Terrain;
import MapEditor.Units.UnitsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Vector;

public class Cell extends JComponent implements Serializable {

	private int i , j;
	private Player player;
	private UnitsInterface kind;
	private UnitsInterface terrain;
	private Polygon shape;
	private Cell parent;
	private int[] originalXs, originalYs;
	private Vector <Cell> relatedCells = new Vector<>();
	private BufferedImage image;

	public Cell(int i, int j, int[] xs, int[] ys, UnitsInterface terrain) {
	    this.i = i;
	    this.j = j;
	    originalXs = xs;
	    originalYs = ys;
		shape = new Polygon(xs, ys, 4);
		this.terrain = terrain;
		BufferedImage bufferedImage = terrain.getImage(i, j, Addresses.board.season);
		image = new BufferedImage(bufferedImage.getColorModel(), bufferedImage.copyData(null), bufferedImage.isAlphaPremultiplied(), null);
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

        changeTerrainImage(false);
    }

    private void changeTerrainImage(boolean isEventDispatched) {
	    int height = Addresses.board.originalHeight, width = Addresses.board.originalWidth;
	    BufferedImage[] bufferedImages = new BufferedImage[6];
        for (int k = 0; k < bufferedImages.length; k++)
            bufferedImages[k] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        BufferedImage bufferedImage = terrain.getImage(i, j, Addresses.board.season);
        image = new BufferedImage(bufferedImage.getColorModel(), bufferedImage.copyData(null), bufferedImage.isAlphaPremultiplied(), null);

        createImageInDir(-1, -1, width, height, bufferedImages, !isEventDispatched);
        createImageInDir(-1, 0, width, height, bufferedImages, !isEventDispatched);
        createImageInDir(-1, 1, width, height, bufferedImages, !isEventDispatched);
        createImageInDir(0, -1, width, height, bufferedImages, !isEventDispatched);
        createImageInDir(0, 1, width, height, bufferedImages, !isEventDispatched);
        createImageInDir(1, -1, width, height, bufferedImages, !isEventDispatched);
        createImageInDir(1, 0, width, height, bufferedImages, !isEventDispatched);
        createImageInDir(1, 1, width, height, bufferedImages, !isEventDispatched);

        Graphics g = image.getGraphics();
        for (int k = 5; k >= 0; k--)
            g.drawImage(bufferedImages[k], 0, 0, null);
        g.dispose();
    }

    private void createImageInDir(int ii, int jj, int width, int height, BufferedImage[] bufferedImages, boolean isElseValid) {
        Cell cell;

        cell = Addresses.board.cells[i + ii][j + jj];
        if (((Terrain) cell.getTerrain()).getPriority() < ((Terrain) terrain).getPriority()) {
            BufferedImage image = cell.getTerrain().getImage(i, j, Addresses.board.season);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color originalColor = new Color(image.getRGB(x, y),true);

                    Color newColor;
                    switch (ii) {
                        case -1:
                            switch (jj) {
                                case -1:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && x < (double) width/8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255*(x - 0)/(width/8)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 0:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y + x/2 < (double) 3*height/4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255*((y + x/2) - height/2)/(height/4)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 1:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y < (double) height/8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255*(y - 0)/(height/8)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                            }
                            break;
                        case 0:
                            switch (jj) {
                                case -1:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y - x/2 > (double) height/4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255*(height/2 - (y - x/2))/(height/4)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 1:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y - x/2 < (double) -height/4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255*((y - x/2) + height/2)/(height/4)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                            }
                            break;
                        case 1:
                            switch (jj) {
                                case -1:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y > (double) 7*height/8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255*(height - y)/(height/8)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 0:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y + x/2 > (double) 5*height/4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255*(3*height/2 - (y + x/2))/(height/4)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 1:
                                    if((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && x > (double) 7*width/8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255*(x - 7*width/8)/(width/8)));

                                        Color imageColor = new Color(bufferedImages[((Terrain) cell.getTerrain()).getPriority()].getRGB(x, y),true);
                                        if(newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[((Terrain) cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        } else if (isElseValid)
            cell.dispatchEvent(new GameEvent(this, Events.cellRefactor));
    }

    public void setKind(UnitsInterface kind, Player player) {
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
            if ("Units".equals(kind.getSource()) || "Building".equals(kind.getSource())) {
                for (int k = 0; k < size; k++)
                    for (int l = 0; l < size; l++) {
                        Cell cell = Addresses.board.cells[i - k][j + l];
                        cell.kind = kind;
                        cell.player = player;
                        if (cell != this)
                            cell.parent = this;
                        else
                            this.player.addCell(this);
                        relatedCells.add(cell);
                    }
            } else {
                for (int k = 0; k < size; k++)
                    for (int l = 0; l < size; l++) {
                        Cell cell = Addresses.board.cells[i - k][j + l];
                        cell.kind = kind;
                        if (cell != this)
                            cell.parent = this;
                        relatedCells.add(cell);
                    }
            }
	}

    public UnitsInterface getKind() {
		return kind;
	}

    public boolean hasKind() {
        return kind != null;
    }

	public void clearKind() {
		if (parent != null)
            parent.clearKind();
        else {
            if (player != null)
                player.removeCell(this);
            for (Cell cell : relatedCells) {
                cell.kind = null;
                cell.parent = null;
                cell.player = null;
            }
            relatedCells.removeAllElements();
        }
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

    public BufferedImage getTerrainImage() {
        return image;
    }

    public Color getColor() {
	    if (player == null)
	        return kind.getColor();
	    else
	        return player.getColor();
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        changeTerrainImage(true);
    }

}
