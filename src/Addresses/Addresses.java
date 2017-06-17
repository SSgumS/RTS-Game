package Addresses;

import Map.GameBoard;
import MapEditor.GamePanel.GamePanel;
import MapEditor.HUD.HUD;
import MapEditor.MainFrame.MainFrame;
import MapEditor.Stack.Redo;
import MapEditor.Stack.Undo;
import Terrain.Terrain;
import Units.Building.Town;
import Units.Human.Worker;
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
    public static GamePanel panel;
    public static MainFrame frame;
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
