package MapEditor.Units;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Others {

    Bush("Bush", "resources\\images\\bush\\editor"),
    FishBig("Fish Big", "resources\\images\\fish\\big\\editor"),
    FishLittle("Fish Little", "resources\\images\\fish\\little\\editor"),
    GoldMine("Gold Mine", "resources\\images\\gold mine\\editor"),
    StoneMine("Stone Mine", "resources\\images\\stone mine\\editor"),
    Broken("Broken", "resources\\images\\tree\\broken\\editor"),
    Tree("Tree", "resources\\images\\tree\\tree");

    private String name;
    public BufferedImage[] images;

    Others(String name, String dirAddress) {
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
