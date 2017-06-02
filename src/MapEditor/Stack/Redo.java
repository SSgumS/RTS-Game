package MapEditor.Stack;

import MapEditor.Map.Cell.Cell;

/**
 * Created by Saeed on 6/1/2017.
 */
public class Redo extends Stack {

    public Cell pop() {
        Cell cell = cells.elementAt(cells.size() - 1);
        cells.removeElement(cell);
        return cell;
    }

}
