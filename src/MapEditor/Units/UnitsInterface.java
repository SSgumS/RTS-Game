package MapEditor.Units;

import java.awt.image.BufferedImage;

/**
 * Created by Saeed on 5/25/2017.
 */
public interface UnitsInterface {

    String getSource();
    String getName();
    BufferedImage getImage();
    int getXHint();
    int getYHint();

}
