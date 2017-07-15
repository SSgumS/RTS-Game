package Addresses;

import GameFrame.Frame;
import GameFrame.Panel;
import GameHUD.HUD;
import Map.GameBoard;
import MapEditor.Stack.Redo;
import MapEditor.Stack.Undo;
import Terrain.Terrain;
import Units.Building.Barracks.Barracks;
import Units.Building.LumberCamp.LumberCamp;
import Units.Building.MiningCamp.MiningCamp;
import Units.Building.Seaport.Seaport;
import Units.Building.Town.Town;
import Units.Human.Boat.Boat;
import Units.Human.Ship.Ship;
import Units.Human.Soldier.Soldier;
import Units.Human.Worker.Worker;
import Units.Resource.Bush.Bush;
import Units.Resource.Fish.BigFish;
import Units.Resource.Fish.LittleFish;
import Units.Resource.Mine.GoldMine;
import Units.Resource.Mine.StoneMine;
import Units.Resource.Tree.Tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Saeed on 5/18/2017.
 */
public class Addresses {

    public static HUD hud;
    public static GameBoard board;
    public static Panel panel;
    public static Frame frame;
    public static Undo undo;
    public static Redo redo;

    public static Vector<Class> unitsClass;
    public static final HashMap<String, Object> unitsHashMap;

    static {
        unitsClass = new Vector<>(Arrays.asList(Worker.class, Town.class, Bush.class, BigFish.class,
                LittleFish.class, GoldMine.class, StoneMine.class, Tree.class));

        unitsHashMap = new HashMap<String, Object>() {{
            put("Deep Water", Terrain.DeepWater);
            put("Dessert", Terrain.Dessert);
            put("Grass", Terrain.Grass);
            put("Ice", Terrain.Ice);
            put("Snow", Terrain.Snow);
            put("Water", Terrain.Water);

            put("Worker", Worker.class);
            put("Soldier", Soldier.class);
            put("Boat", Boat.class);
            put("Ship", Ship.class);

            put("Barracks", Barracks.class);
            put("Lumber Camp", LumberCamp.class);
            put("Mining Camp", MiningCamp.class);
            put("Seaport", Seaport.class);
            put("Town", Town.class);

            put("Bush", Bush.class);
            put("Big Fish", BigFish.class);
            put("Little Fish", LittleFish.class);
            put("Gold Mine", GoldMine.class);
            put("Stone Mine", StoneMine.class);
            put("Tree", Tree.class);
        }};
    }

}
