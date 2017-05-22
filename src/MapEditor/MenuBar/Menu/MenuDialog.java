package MapEditor.MenuBar.Menu;

import MapEditor.Cursor.Cursors;
import MapEditor.MainFrame.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/22/2017.
 */
public class MenuDialog extends JDialog {

    public MenuDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setSize(MainFrame.width/3, MainFrame.height/2);
        setLocation(MainFrame.width/2 - getWidth()/2, MainFrame.height/2 - getHeight()/2);
        setUndecorated(true);

        setCursor(Cursors.main);

        setContentPane(new MenuBack(null));
    }

}
