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
public enum Units implements UnitsInterface {

//    Boat("Boat", "resources\\images\\boat\\editor", Color.BLUE, (int) -11.5, 45,
//            Terrain.Dessert, Terrain.Grass, Terrain.Ice, Terrain.Snow),
//    Ship("Ship", "resources\\images\\ship\\editor", Color.BLUE, 0, 0,
//            Terrain.Dessert, Terrain.Grass, Terrain.Ice, Terrain.Snow),
//    Soldier("Soldier", "resources\\images\\soldier\\editor", Color.BLUE, -17, 9,
//            Terrain.DeepWater, Terrain.Ice, Terrain.Water),
    Worker("Worker", "resources\\images\\worker\\editor", Color.BLUE, -34, -1,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water);

    private String name;
    private BufferedImage[] images;
    private int size = 1;
    private int xHint;
    private int yHint;
    private Color color;
    private Vector<Terrain> abandonTerrains;

    Units(String name, String dirAddress, Color color, int xHint, int yHint, Terrain... abandonTerrains) {
        this.name = name;
        this.color = color;
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
    }

    @Override
    public String getSource() {
        return "Units";
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
        return color;
    }

    @Override
    public boolean isAllowed(Terrain terrain) {
        return !abandonTerrains.contains(terrain);
    }

}
