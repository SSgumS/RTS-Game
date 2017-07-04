package GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Saeed on 7/4/2017.
 */
public class Back extends JPanel {

    protected BufferedImage menuBar;
    protected BufferedImage hud;

    public Back(LayoutManager layout) {
        super(layout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(menuBar, 0, 0, null);
        g.drawImage(hud, 0, Frame.height - hud.getHeight(), null);
    }

}
