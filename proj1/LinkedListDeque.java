public class LinkedListDeque<GenericThing>
{
    public class Node
    {
        public Node previous;
        public GenericThing item;     /* Basically this is Hug's node class with a previous link */
        public Node next;

        public Node(Node p, GenericThing i, Node h) {
            previous = p;
            item = i;
            next = h;
        }
    }

    private Node sentinel; /* this is Hug's instance vars also */
    private int size;
    private Node last;

    public LinkedListDeque()
    {
        sentinel = Node(null, null, null);
        size = 0;
        last = sentinel;
    }

    public LinkedListDeque(GenericThing thing)
    {
        sentinel = Node(null, null, Node(sentinel, thing, sentinel));
        sentinel.previous = sentinel.next;
        last = sentinel.next;
        size = 1;
    }

    public void addFirst(GenericThing thing)
    {
        GenericThing oldNode = sentinel.next;
        sentinel.next = Node(sentinel, thing, oldNode);
        size++;
    }

    public void addLast(GenericThing thing)
    {
        last.next = Node(last, thing, sentinel);
        size++;
    }


}