package MapEditor.Units;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Units implements UnitsInterface {

    Boat("Boat", "resources\\images\\boat\\editor"),
    Ship("Ship", "resources\\images\\ship\\editor"),
    Soldier("Soldier", "resources\\images\\soldier\\editor"),
    Worker("Worker", "resources\\images\\worker\\editor");

    private String name;
    private BufferedImage[] images;
    private int imageNum = 0;
    private int xHint;
    private int yHint;

    Units(String name, String dirAddress) {
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

    @Override
    public String getSource() {
        return "Units";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BufferedImage getImage() {
        BufferedImage image = images[imageNum];
//        if (imageNum < images.length - 1)
//            imageNum++;
//        else
//            imageNum = 0;
        return image;
    }

    @Override
    public int getXHint() {
        return xHint;
    }

    @Override
    public int getYHint() {
        return yHint;
    }

}
