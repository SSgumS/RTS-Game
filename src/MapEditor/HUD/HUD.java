package MapEditor.HUD;

import GameEvent.Events;
import MapEditor.HUD.ActionSection.ActionSection;
import MapEditor.HUD.MiniMap.MiniMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/15/2017.
 */
public class HUD extends JPanel {

    private BufferedImage image;
    private MiniMap miniMap = new MiniMap(null, true);
    private ActionSection actionSection = new ActionSection(null);

    public HUD(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        try {
            image = ImageIO.read(new File("resources\\images\\ui\\HUD\\HUD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(image.getWidth(), image.getHeight());
        setLocation(0, GameFrame.Frame.height - getHeight());

        miniMap.setLocation(getWidth() - 19 - miniMap.getWidth(), getHeight() - 10 - miniMap.getHeight());

        add(miniMap);
        add(actionSection);
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn || e.getID() == Events.actionOff)
            actionSection.dispatchEvent(e);
        else if (e.getID() == Events.boardCreated) {
            miniMap.dispatchEvent(e);
            actionSection.dispatchEvent(e);
        } else if (e.getID() == Events.clearSelection)
            actionSection.dispatchEvent(e);
        else if (e.getID() == Events.zoom)
            miniMap.dispatchEvent(e);
    }
}
