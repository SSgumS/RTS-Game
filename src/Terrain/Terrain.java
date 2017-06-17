package Terrain;

import Season.Season;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Terrain {

    DeepWater("resources\\images\\terrain\\deep water", new Color(25, 50, 200), 0),
    Dessert("resources\\images\\terrain\\dessert", new Color(255, 225, 150), 2),
    Grass("resources\\images\\terrain\\grass", new Color(0, 200,0), 4),
    Ice("resources\\images\\terrain\\ice", new Color(200, 225, 255), 5),
    Snow("resources\\images\\terrain\\snow", new Color(165, 205, 255), 3),
    Water("resources\\images\\terrain\\water", new Color(40, 180, 255), 1);

    private BufferedImage[] images;
    private Color color;
    private int priority;

    Terrain(String dirAddress, Color color, int priority) {
        this.color = color;
        this.priority = priority;

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

    public BufferedImage getEditorImage(int i, int j, Season season) {
        Terrain terrain = this;

        if (season == Season.Winter && (this == Water || this == DeepWater))
            terrain = Ice;

        return terrain.images[j%10 + (i%10)*10];
    }

    public Color getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }

}
