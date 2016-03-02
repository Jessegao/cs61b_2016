package editor;

/**
 * Created by jesse on 3/1/16.
 */
public class Node<Item> {
    public Node previous;
    public Item item;     /* Basically this is Hug's node class with a previous link */
    public Node next;

    public Node(Node p, Item i, Node h)  {
        previous = p;
        item = i;
        next = h;
    }

    public Item getItem(){
        return item;
    }

    public Node getPrevious(){
        return previous;
    }

    public Node getNext() {
        return next;
    }
}
