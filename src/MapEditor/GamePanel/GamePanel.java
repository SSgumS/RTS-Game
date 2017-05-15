package MapEditor.GamePanel;

import MapEditor.HUD.HUD;
import MapEditor.MainFrame.MainFrame;
import MapEditor.MenuBar.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Saeed on 5/15/2017.
 */
public class GamePanel extends JPanel {

    private BufferedImage dbImage;
    private MenuBar menuBar;
    private HUD hud;

    private Timer repaint = new Timer(16, e -> {
        render();
        paint();
    });

    public GamePanel(LayoutManager layout) {
        super(layout);
        setSize(MainFrame.width, MainFrame.height);
        setLocation(0, 0);

        addMenuBar();
        addHUD();

        repaint.start();
    }

    private void addMenuBar() {
        menuBar = new MenuBar(null);
        add(menuBar);
    }

    private void addHUD() {
        hud = new HUD(null);
        add(hud);
    }


    private void render() {
        dbImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dbImage.getGraphics();

        g.drawImage(hud.getImage(), hud.getX(), hud.getY(), null);
        g.drawImage(menuBar.getImage(), menuBar.getX(), menuBar.getY(), null);
    }

    private void paint() {
        Graphics g = getGraphics();

        g.drawImage(dbImage, 0, 0, null);

        g.dispose();
    }

}
