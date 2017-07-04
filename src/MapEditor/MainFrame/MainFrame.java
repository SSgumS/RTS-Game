package MapEditor.MainFrame;

import MapEditor.GamePanel.GamePanel;

import java.awt.*;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MainFrame extends GameFrame.Frame {

    public MainFrame(String title) {
        super(title);

        setContentPane(new BackPanel(new BorderLayout()));
        getContentPane().add(new GamePanel(null));

        setVisible(true);
    }

}
