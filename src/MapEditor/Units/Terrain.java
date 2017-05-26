package MapEditor.Units;

import MapEditor.Season.Season;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Terrain implements UnitsInterface {

    DeepWater("Deep Water", "resources\\images\\terrain\\deep water", new Color(25, 50, 200)),
    Dessert("Dessert", "resources\\images\\terrain\\dessert", new Color(255, 225, 150)),
    Grass("Grass", "resources\\images\\terrain\\grass", new Color(0, 200,0)),
    Ice("Ice", "resources\\images\\terrain\\ice", new Color(200, 225, 255)),
    Snow("Snow", "resources\\images\\terrain\\snow", new Color(165, 205, 255)),
    Water("Water", "resources\\images\\terrain\\water", new Color(40, 180, 255));

    private String name;
    private BufferedImage[] images;
    private int size = 1;
    private int xHint = 0;
    private int yHint = 0;
    private Color color;

    Terrain(String name, String dirAddress, Color color) {
        this.name = name;
        this.color = color;

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
        return "Terrain";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BufferedImage getImage(int i, int j, Season season) {
        Terrain terrain = this;

        if (season == Season.Winter && (this == Water || this == DeepWater))
            terrain = Ice;

        return terrain.images[j%10 + (i%10)*10];
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
        return true;
    }

}
