package MapEditor.Stack;

import MapEditor.Map.Cell.Cell;

import java.io.*;
import java.util.Vector;

/**
 * Created by Saeed on 6/1/2017.
 */
public class Stack implements Serializable {

    Vector<Cell> cells = new Vector<>();

    public void push(Cell cell) {
        cells.addElement(cell);

        if (cells.size() > 10)
            cells.removeElementAt(0);
    }

    public boolean isEmpty() {
        return cells.size() == 0;
    }

    public void removeAll() {
        cells.removeAllElements();
    }

    public Vector<Cell> getCells() {
        return cells;
    }

//    private void writeObject(ObjectOutputStream out) throws IOException {
//        //TODO: chera zamane write kardane image (ya shayadam read kardanesh) (zamani ke tooye ye file hamashoon ro mikhastim write konim) ghati mikard o dota darmiyoon null mindakht o dorost read o write nemikard?
//        out.defaultWriteObject();
////        int i = 0;
////        for (Cell cell : cells) {
////            i++;
////            File file = new File(path + i + ".png");
////            ImageIO.write(cell.getTerrainImage(), "png", file);
////        }
//    }

//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
////        int i = 0;
////        for (Cell cell : cells) {
////            i++;
////            File file = new File(path + i + ".png");
////            cell.setTerrainImage(ImageIO.read(file));
////        }
//    }

}
