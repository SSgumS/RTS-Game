package MapEditor.Units;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Building {

    Town("Town", "resources\\images\\town");

    private String name;
    public BufferedImage[] images;

    Building(String name, String dirAddress) {
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
