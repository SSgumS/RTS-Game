package Game.GamePanel;

import Addresses.Addresses;
import Game.MainFrame.MainFrame;
import GameEvent.Events;
import GameEvent.GameEvent;
import GameEvent.GenerateMapEvent;
import GameFrame.*;
import Map.GameBoard;
import Season.Season;
import Terrain.Terrain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Saeed on 6/22/2017.
 */
public class GamePanel extends GameFrame.Panel {

    public GamePanel(LayoutManager layout) {
        super(layout);
    }

}
