package Game.MenuBar.Menu;

import Cursor.Cursors;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeed on 5/22/2017.
 */
public class MenuDialog extends JDialog {

    public MenuDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setSize(GameFrame.Frame.width/3, GameFrame.Frame.height/2);
        setLocation(GameFrame.Frame.width/2 - getWidth()/2, GameFrame.Frame.height/2 - getHeight()/2);
        setUndecorated(true);

        setCursor(Cursors.main);

        setContentPane(new MenuBack(null));
    }

}
