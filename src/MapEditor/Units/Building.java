package MapEditor.Units;

import MapEditor.Season.Season;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Building implements UnitsInterface {

    Town("Town", "resources\\images\\town\\town", 4, 143, 351,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water);

    private String name;
    private BufferedImage[] images;
    private int size;
    private int xHint;
    private int yHint;
    private Vector<Terrain> abandonTerrains;

    Building(String name, String dirAddress, int size, int xHint, int yHint, Terrain... abandonTerrains) {
        this.name = name;
        this.size = size;
        this.xHint = xHint;
        this.yHint = yHint;
        this.abandonTerrains = new Vector<>(Arrays.asList(abandonTerrains));

        File[] files = new File(dirAddress).listFiles();
        images = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                images[i] = ImageIO.read(files[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        name();
    }

    @Override
    public String getSource() {
        return "Building";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BufferedImage getImage(int i, int j, Season season) {
        return images[0];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getXHint() {
        return xHint;
    }

    @Override
    public int getYHint() {
        return yHint;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean isAllowed(Terrain terrain) {
        return !abandonTerrains.contains(terrain);
    }

}
