package MapEditor.Map.Cell;

import Addresses.Addresses;
import GameEvent.*;
import Map.GameCell;
import Terrain.Terrain;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class Cell extends GameCell {

	public Cell(int i, int j, int[] xs, int[] ys, Terrain terrain) {
	    this.i = i;
	    this.j = j;
	    originalXs = xs;
	    originalYs = ys;
		shape = new Polygon(xs, ys, 4);
		this.terrain = terrain;
		BufferedImage bufferedImage = terrain.getEditorImage(i, j, Addresses.board.season);
		image = new BufferedImage(bufferedImage.getColorModel(), bufferedImage.copyData(null), bufferedImage.isAlphaPremultiplied(), null);
	}

    private Cell(int i, int j, Terrain terrain, int[] originalXs, int[] originalYs, BufferedImage image) {
	    this.i = i;
	    this.j = j;
	    this.terrain = terrain;
	    this.originalXs = originalXs;
	    this.originalYs = originalYs;
	    this.image = image;
    }

    private void setTerrain(Terrain terrain) {
        this.terrain = terrain;

        changeTerrainImage(false);
    }

    private void changeTerrainImage(boolean isEventDispatched) {
	    int height = Addresses.board.originalHeight, width = Addresses.board.originalWidth;
	    BufferedImage[] bufferedImages = new BufferedImage[6];
        for (int k = 0; k < bufferedImages.length; k++)
            bufferedImages[k] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        BufferedImage bufferedImage = terrain.getEditorImage(i, j, Addresses.board.season);
        image = new BufferedImage(bufferedImage.getColorModel(), bufferedImage.copyData(null), bufferedImage.isAlphaPremultiplied(), null);

        try {
            createImageInDir(-1, -1, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            createImageInDir(-1, 0, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            createImageInDir(-1, 1, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            createImageInDir(0, -1, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            createImageInDir(0, 1, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            createImageInDir(1, -1, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            createImageInDir(1, 0, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            createImageInDir(1, 1, width, height, bufferedImages, !isEventDispatched);
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        Graphics g = image.getGraphics();
        for (int k = 5; k >= 0; k--)
            g.drawImage(bufferedImages[k], 0, 0, null);
        g.dispose();
    }

    private void createImageInDir(int ii, int jj, int width, int height, BufferedImage[] bufferedImages, boolean isRefactor) {
        Cell cell = (Cell) Addresses.board.cells[i + ii][j + jj];
        if (isRefactor)
            cell.dispatchEvent(new GameEvent(this, Events.cellRefactor));

        if ((cell.getTerrain()).getPriority() < terrain.getPriority()) {
            BufferedImage image = cell.getTerrain().getEditorImage(i, j, Addresses.board.season);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color originalColor = new Color(image.getRGB(x, y), true);

                    Color newColor;
                    switch (ii) {
                        case -1:
                            switch (jj) {
                                case -1:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && x < (double) width / 8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255 * (x - 0) / (width / 8)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 0:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y + x / 2 < (double) 3 * height / 4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255 * ((y + x / 2) - height / 2) / (height / 4)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 1:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y < (double) height / 8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255 * (y - 0) / (height / 8)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                            }
                            break;
                        case 0:
                            switch (jj) {
                                case -1:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y - x / 2 > (double) height / 4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255 * (height / 2 - (y - x / 2)) / (height / 4)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 1:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y - x / 2 < (double) -height / 4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255 * ((y - x / 2) + height / 2) / (height / 4)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                            }
                            break;
                        case 1:
                            switch (jj) {
                                case -1:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y > (double) 7 * height / 8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255 * (height - y) / (height / 8)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 0:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && y + x / 2 > (double) 5 * height / 4) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 - 255 * (3 * height / 2 - (y + x / 2)) / (height / 4)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                                case 1:
                                    if ((originalColor.getBlue() != 255 || originalColor.getGreen() != 255 || originalColor.getRed() != 255) && x > (double) 7 * width / 8) {
                                        newColor = new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), (int) ((double) 255 * (x - 7 * width / 8) / (width / 8)));

                                        Color imageColor = new Color(bufferedImages[(cell.getTerrain()).getPriority()].getRGB(x, y), true);
                                        if (newColor.getAlpha() > imageColor.getAlpha())
                                            bufferedImages[(cell.getTerrain()).getPriority()].setRGB(x, y, newColor.getRGB());
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        }
    }

    public void setTerrainImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public Cell clone() {
	    Cell cell = new Cell(i, j, terrain, originalXs, originalYs, image);
	    cell.shape = shape;
	    return cell;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.setKind:
                setTerrain((Terrain) ((SetEvent) e).getKind());
                break;
            case Events.cellRefactor:
                changeTerrainImage(true);
                break;
        }
    }

}
