package MapEditor.MainFrame;

import Addresses.Addresses;
import MapEditor.GamePanel.GamePanel;

import java.awt.*;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MainFrame {

    public MainFrame() {
        Addresses.frame.setContentPane(new BackPanel(null));
        Addresses.frame.getContentPane().add(new GamePanel(null));
    }

}
