package mainMenu;

import GameFrame.Frame;
import GameFrame.Panel;

public class ForPanel extends Panel {

	protected int wid = Frame.width/4;
	protected int high = Frame.height/11;

	public ForPanel() {
		setOpaque(false);
        setSize(GameFrame.Frame.width, GameFrame.Frame.height);
        setLocation(0, 0);
        setLayout(null);
	}

}
