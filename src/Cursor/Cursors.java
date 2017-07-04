package Cursor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/16/2017.
 */
public class Cursors {

    public static Cursor main;
    public static Cursor fight;
    public static Cursor farm;
    public static Cursor cut;
    public static Cursor build;
    public static Cursor mine;
    public static Cursor enter;
    public static Cursor embark;
    public static Cursor disembark;
    public static Cursor flag;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        try {
            main = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\main.png")), new Point(0, 0), "main");
            fight = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\fight.png")), new Point(0, 0), "fight");
            farm = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\farm.png")), new Point(0, 0), "farm");
            cut = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\cut.png")), new Point(0, 0), "cut");
            build = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\build.png")), new Point(0, 0), "build");
            mine = build;
            enter = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\enter.png")), new Point(0, 0), "enter");
            embark = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\embark.png")), new Point(0, 0), "embark");
            disembark = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\disembark.png")), new Point(0, 0), "disembark");
            flag = toolkit.createCustomCursor(ImageIO.read(new File("resources\\images\\ui\\mouse\\flag.png")), new Point(0, 0), "flag");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
