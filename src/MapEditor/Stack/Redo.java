package MapEditor.Stack;

/**
 * Created by Saeed on 6/1/2017.
 */
public class Redo extends Stack {

    public Object pop() {
        Object object = stack.elementAt(stack.size() - 1);
        stack.removeElement(object);
        return object;
    }

}
