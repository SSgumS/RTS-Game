package MapEditor.MainFrame;

import Addresses.Addresses;
import MapEditor.Cursor.Cursors;
import MapEditor.GamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

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

        try {
            gd.setDisplayMode(new DisplayMode(1280, 768, 32, 60));
            width = 1280;
            height = 768;
        } catch (IllegalArgumentException e) {
            gd.setDisplayMode(new DisplayMode(1280, 1024, 32, 60));
            width = 1280;
            height = 1024;
        }

        Addresses.frame = this;

        setContentPane(new BackPanel(new BorderLayout()));
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

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_DEACTIVATED)
            return;

        super.processWindowEvent(e);
    }
}
