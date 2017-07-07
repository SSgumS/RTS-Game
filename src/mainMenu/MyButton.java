package mainMenu;

import java.awt.Color;

import javax.swing.JButton;

public class MyButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	    public MyButton(String text, int wid, int high) {
	        super(text);

	        setSize(wid,high);
	        setOpaque(false);
	    }
	    
}
