package Game.MainFrame;

import Addresses.Addresses;
import Game.GamePanel.GamePanel;

import java.awt.*;

/**
 * Created by Saeed on 6/22/2017.
 */
public class MainFrame {

    public MainFrame() {
        Addresses.frame.setContentPane(new BackPanel(new BorderLayout()));
        Addresses.frame.getContentPane().add(new GamePanel(null));
    }

}
