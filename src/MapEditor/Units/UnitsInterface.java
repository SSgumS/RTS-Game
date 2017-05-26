package MapEditor.Units;

import MapEditor.Season.Season;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Saeed on 5/25/2017.
 */
public interface UnitsInterface {

    String getSource();
    String getName();
    BufferedImage getImage(int i, int j, Season season);
    int getSize();
    int getXHint();
    int getYHint();
    Color getColor();
    boolean isAllowed(Terrain terrain);

}
