package MapEditor.Cursor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/16/2017.
 */
public class Cursors {

    public static Cursor main;

    public Cursors() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        try {
            main = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\mouse001.png")), new Point(3, 0), "main");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
