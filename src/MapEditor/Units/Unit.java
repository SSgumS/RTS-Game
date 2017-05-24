package MapEditor.Units;

/**
 * Created by Saeed on 5/24/2017.
 */
public enum Unit {

    DeepWater("Terrain"),
    Dessert("Terrain"),
    Dirt("Terrain"),
    DirtSnow("Terrain"),
    Grass("Terrain"),
    GrassSnow("Terrain"),
    Ice("Terrain"),
    Snow("Terrain"),
    Water("Terrain"),
    Boat("Units"),
    Ship("Units"),
    Soldier("Units"),
    Worker("Units"),
    Town("Building"),
    Bush("Others"),
    FishBig("Others"),
    FishLittle("Others"),
    GoldMine("Others"),
    StoneMine("Others"),
    SpringTree("Others"),
    SummerTree("Others"),
    AutumnTree("Others"),
    WinterTree("Others");

    private String source;

    Unit(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

}
