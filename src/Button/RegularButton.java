package Button;

import Addresses.Addresses;

import javax.swing.*;

/**
 * Created by Saeed on 5/26/2017.
 */
public class RegularButton extends JButton {

    public RegularButton(String text) {
        super(text);

        addMouseMotionListener(Addresses.panel);
    }

}
