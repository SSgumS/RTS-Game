package MapEditor.Units;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Terrain {

    DeepWater("Deep Water", "resources\\images\\terrain\\deep water"),
    Dessert("Dessert", "resources\\images\\terrain\\dessert"),
    Dirt("Dirt", "resources\\images\\terrain\\dirt"),
    DirtSnow("Dirt-Snow", "resources\\images\\terrain\\dirt-snow"),
    Grass("Grass", "resources\\images\\terrain\\grass"),
    GrassSnow("Grass-Snow", "resources\\images\\terrain\\grass-snow"),
    Ice("Ice", "resources\\images\\terrain\\ice"),
    Snow("Snow", "resources\\images\\terrain\\snow"),
    Water("Water", "resources\\images\\terrain\\water");

    private String name;
    public BufferedImage[] images;

    Terrain(String name, String dirAddress) {
        this.name = name;
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

    public String getName() {
        return name;
    }

}
