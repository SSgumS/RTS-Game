package MapEditor.MainFrame;

import MapEditor.Cursor.Cursors;
import MapEditor.GamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MainFrame extends JFrame {

    public static int height, width;

    public MainFrame(String title) {
        super(title);

        setLookAndFeel();
        new Cursors();
        setCursor(Cursors.main);

        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
        setUndecorated(true);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        gd.setDisplayMode(new DisplayMode(1280, 1024, 32, 60));
        width = getWidth();
        height = getHeight();

        setContentPane(new BackPanel(new BorderLayout()));
//        getContentPane().add(new BackPanel());
        getContentPane().add(new GamePanel(null));

        setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
