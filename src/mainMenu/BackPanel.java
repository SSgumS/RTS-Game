package mainMenu;

import GameFrame.Frame;

import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackPanel extends JPanel{
	
	private ImageIcon background;

	public BackPanel(LayoutManager layout) {
		super(layout);

		if (Frame.height == 1024)
		    background = new ImageIcon("resources\\images\\ui\\background\\main menu\\knight 1024.jpg");
		else
            background = new ImageIcon("resources\\images\\ui\\background\\main menu\\knight 768.jpg");
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background.getImage(),0,0,null);
	}
	
}
