package mainMenu.MenuButton;

import javax.swing.JButton;

public class MyButton extends JButton{

	    public MyButton(String text, int wid, int high) {
	        super(text);

	        setSize(wid,high);
	        setOpaque(false);
	    }
	    
}
