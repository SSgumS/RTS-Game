package MapEditor.Button;

import Addresses.Addresses;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeed on 5/16/2017.
 */
public class Button extends JButton {

    private ImageIcon icon = new ImageIcon("resources\\images\\ui\\button\\button.png");
    private ImageIcon pressedIcon = new ImageIcon("resources\\images\\ui\\button\\button pressed.png");

    public Button(String text) {
        super(text);
        setSize(100, 26);

        setIcon(icon);
        setPressedIcon(pressedIcon);

        setHorizontalTextPosition(CENTER);
        setForeground(new Color(255, 247, 153));

        addMouseMotionListener(Addresses.panel);
    }

    public void swapIcons() {
        if (getIcon().equals(icon)) {
            setIcon(pressedIcon);
            setPressedIcon(icon);
        } else {
            setIcon(icon);
            setPressedIcon(pressedIcon);
        }
    }

}
