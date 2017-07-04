package MapEditor.MenuBar.Menu;

import Cursor.Cursors;
import MapEditor.MainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;

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
