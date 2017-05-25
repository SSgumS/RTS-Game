package MapEditor.Units;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/21/2017.
 */
public enum Others implements UnitsInterface {

    Bush("Bush", "resources\\images\\bush\\editor"),
    FishBig("Big Fish", "resources\\images\\fish\\big\\editor"),
    FishLittle("Little Fish", "resources\\images\\fish\\little\\editor"),
    GoldMine("Gold Mine", "resources\\images\\gold mine\\editor"),
    StoneMine("Stone Mine", "resources\\images\\stone mine\\editor"),
    SpringTree("Tree", "resources\\images\\tree\\tree\\spring"),
    SummerTree("Tree", "resources\\images\\tree\\tree\\summer"),
    AutumnTree("Tree", "resources\\images\\tree\\tree\\autumn"),
    WinterTree("Tree", "resources\\images\\tree\\tree\\winter");

    private String name;
    private BufferedImage[] images;
    private int imageNum = 0;
    private int xHint;
    private int yHint;

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

    @Override
    public String getSource() {
        return "Others";
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
