package GameFrame;

import Addresses.Addresses;
import Cursor.Cursors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Created by Saeed on 7/4/2017.
 */
public class Frame extends JFrame {

    public static int height, width;

    public Frame(String title) {
        super(title);

        setLookAndFeel();
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
