package GameHUD;

import Addresses.Addresses;
import GameEvent.Events;
import GameHUD.MiniMap.MiniMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 7/9/2017.
 */
public class HUD extends JPanel {

    private BufferedImage image;
    protected MiniMap miniMap = new MiniMap(null, true);

    public HUD(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        setOpaque(false);

        Addresses.hud = this;

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\HUD\\HUD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(image.getWidth(), image.getHeight());
        setLocation(0, GameFrame.Frame.height - getHeight());

        miniMap.setLocation(getWidth() - 19 - miniMap.getWidth(), getHeight() - 10 - miniMap.getHeight());

        add(miniMap);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.boardCreated:
            case Events.zoom:
                miniMap.dispatchEvent(e);
                break;
        }
    }
}
