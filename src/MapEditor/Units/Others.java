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
public enum Others implements UnitsInterface {

    Bush("Bush", "resources\\images\\bush\\editor", new Color(0, 100, 0), (int) -4.5, 15,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water),
    FishBig("Big Fish", "resources\\images\\fish\\big\\editor", new Color(0, 100, 0), 269, 205,
            Terrain.Dessert, Terrain.Grass, Terrain.Ice, Terrain.Snow),
    FishLittle("Little Fish", "resources\\images\\fish\\little\\editor", new Color(0, 100, 0), 260, 209,
            Terrain.Dessert, Terrain.Grass, Terrain.Ice, Terrain.Snow),
    GoldMine("Gold Mine", "resources\\images\\gold mine\\editor", new Color(0, 100, 0), -4, 6,
            Terrain.DeepWater, Terrain.Ice, Terrain.Ice, Terrain.Water),
    StoneMine("Stone Mine", "resources\\images\\stone mine\\editor", new Color(0, 100, 0), -6, 2,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water),
    SpringTree("Tree", "resources\\images\\tree\\tree\\spring", new Color(0, 100, 0), (int) 5.5, 138,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water),
    SummerTree("Tree", "resources\\images\\tree\\tree\\summer", new Color(0, 100, 0), 3, 156,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water),
    AutumnTree("Tree", "resources\\images\\tree\\tree\\autumn", new Color(0, 100, 0), 3, 152,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water),
    WinterTree("Tree", "resources\\images\\tree\\tree\\winter", new Color(0, 100, 0), (int) -9.5, 118,
            Terrain.DeepWater, Terrain.Ice, Terrain.Water);

    private String name;
    private BufferedImage[] images;
    private int size = 1;
    private int xHint;
    private int yHint;
    private Color color;
    private Vector <Terrain> abandonTerrains;

    Others(String name, String dirAddress, Color color, int xHint, int yHint, Terrain... abandonTerrains) {
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
        return "Others";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BufferedImage getImage(int i, int j, Season season) {
        Others others = this;

        if (this == SpringTree) {
            switch (season) {
                case Spring:
                    others = SpringTree;
                    break;
                case Summer:
                    others = SummerTree;
                    break;
                case Autumn:
                    others = AutumnTree;
                    break;
                case Winter:
                    others = WinterTree;
                    break;
            }
        }

        return others.images[0];
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
