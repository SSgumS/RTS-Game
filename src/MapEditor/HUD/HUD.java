package MapEditor.HUD;

import MapEditor.Button.*;
import MapEditor.Button.Button;
import MapEditor.GameEvent.Events;
import MapEditor.HUD.ActionSection.ActionSection;
import MapEditor.MainFrame.MainFrame;

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
        setLocation(0, MainFrame.height - getHeight());

        add(actionSection);
    }

    public BufferedImage getImage() {
        BufferedImage dbImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dbImage.getGraphics();

        g.drawImage(image, 0, 0, null);
        paintComponents(g);

        return dbImage;
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        if (e.getID() == Events.actionOn || e.getID() == Events.actionOff)
            actionSection.dispatchEvent(e);
    }
}
