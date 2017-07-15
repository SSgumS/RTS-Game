package mainMenu;
import GameFrame.Frame;
import MusicPlayer.MusicPlayer;
import mainMenu.MenuPanels.Main.ButPanel;

public class MyFrame extends Frame {

	public MyFrame(String title) {
		super(title);

        new MusicPlayer();

        setContentPane(new BackPanel(null));
        getContentPane().add(new ButPanel());

        setVisible(true);
	}
}
