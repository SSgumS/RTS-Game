package mainMenu;
import GameFrame.Frame;

public class MyFrame extends Frame {

	public MyFrame(String title) {
		super(title);

        setContentPane(new BackPanel(null));
        getContentPane().add(new ButPanel());

        setVisible(true);
	}
}
