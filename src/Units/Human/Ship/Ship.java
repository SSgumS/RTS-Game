package Units.Human.Ship;

import Map.GameCell;
import Player.Player;
import Units.Human.Human;

/**
 * Created by Saeed on 7/10/2017.
 */
public class Ship extends Human {

    public Ship(GameCell cell, Player owner) {
        super(cell, owner, 1);
    }

}
