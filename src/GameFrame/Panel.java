package GameFrame;

import Addresses.Addresses;
import GameEvent.GameEvent;
import GameEvent.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Saeed on 7/4/2017.
 */
public class Panel extends JPanel implements MouseMotionListener, Runnable {

    public int mouseX, mouseY;
    protected boolean active = true;

    public Panel(LayoutManager layout) {
        super(layout);

        setOpaque(false);
        setSize(Frame.width, Frame.height);
        setLocation(0, 0);

        Addresses.panel = this;

        addMouseMotionListener(this);
    }

    public Panel() {}

    public boolean isActive() {
        return active;
    }

    @Override
    public void run() {
        while (active) {
            repaint();

            try {
                Thread.sleep(32);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX() + ((JComponent) e.getSource()).getX();
        mouseY = e.getY() + ((JComponent) e.getSource()).getY();

        if ((mouseY == 0 || mouseY == Frame.height - 1 || mouseX == 0 || mouseX == Frame.width - 1) && Addresses.board != null)
            Addresses.board.dispatchEvent(new GameEvent(this, Events.actionOn));
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        super.processComponentEvent(e);

        switch (e.getID()) {
            case Events.shutdown:
                active = false;
                break;
        }
    }
}
