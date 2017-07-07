package Game.MenuBar;

import Addresses.Addresses;
import Button.Button;
import Game.MenuBar.StatsSection.StatsSection;
import GameEvent.Events;
import GameEvent.GameEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Saeed on 5/15/2017.
 */
public class MenuBar extends JPanel implements ActionListener {
	
	private StatsSection statsSection;
    private Button menuButton;

    public MenuBar(LayoutManager layout) {
        super(layout);
        setOpaque(false);

        try {
            BufferedImage image = ImageIO.read(new File("resources\\images\\ui\\menu bar\\map editor\\menu bar.png"));
			setSize(image.getWidth(), image.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		addStatsSection();
        addMenuButton();
    }
	
	private void addStatsSection() {
		statsSection = new StatsSection(null);
        statsSection.setLocation(8, 10);
		statsSection.setSize(377, 17);
        add(statsSection);
	}

    private void addMenuButton() {
        menuButton = new Button("Menu");
        menuButton.setLocation(getWidth() - 10 - menuButton.getWidth(), 3);
        menuButton.addActionListener(this);
        add(menuButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Addresses.panel.dispatchEvent(new GameEvent(menuButton, Events.actionOn));
    }

}
