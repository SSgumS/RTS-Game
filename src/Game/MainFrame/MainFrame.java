package Game.MainFrame;

import Game.GamePanel.GamePanel;

import java.awt.*;

/**
 * Created by Saeed on 6/22/2017.
 */
public class MainFrame extends GameFrame.Frame {

    public MainFrame(String title) {
        super(title);

        setContentPane(new BackPanel(new BorderLayout()));
        getContentPane().add(new GamePanel(null));

        setVisible(true);
    }

}
