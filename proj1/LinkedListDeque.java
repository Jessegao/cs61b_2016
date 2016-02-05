public class LinkedListDeque<Item>{
    private class Node{
        public Node previous;
        public Item item;     /* Basically this is Hug's node class with a previous link */
        public Node next;

        public Node(Node p, Item i, Node h) {
            previous = p;
            item = i;
            next = h;
        }
    }

    private Node sentinel; /* this is Hug's instance vars also */
    private int size;
    private Node last;
    //IMPORTANT! last.next is sentinel
    //sentinel.next is always front

    public LinkedListDeque(){
        sentinel = new Node(null, null, null);
        size = 0;
        last = sentinel;
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(Item thing){
        Node oldNode = sentinel.next;
        //last needs to account for first add
        //set the proper pointers for the new node
        sentinel.next = new Node(sentinel, thing, oldNode);
        //set pointer for oldnode
        oldNode.previous = sentinel.next;
        //special case for if the list was originally empty
        if(isEmpty()){
            last = sentinel.next;
            sentinel.previous = last;
        }
        size++;
    }

    public void addLast(Item thing){
        last.next = new Node(last, thing, sentinel);
        last = last.next;
        sentinel.previous = last;
        size++;
    }

    public boolean isEmpty(){
        return(size == 0);
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Node tracker = sentinel.next;
        if(size == 0){
            return;
        }

        for(int i = 0; i < size-1; i++){
            System.out.print(tracker.item.toString() + " ");
            tracker = tracker.next;
        }
        System.out.print(tracker.item.toString() + "\n");
    }

    public Item removeFirst(){
        if(isEmpty()){
            return null;
        }
        else{
            Node r = sentinel.next;//to be deleted

            sentinel.next = sentinel.next.next;//removes sentinel references to r
            sentinel.next.previous = sentinel;

            r.previous = null;//isolates r
            r.next = null;

            size--;
            //for the case where the only element in a set is removed and last becomes sentinel
            if(isEmpty()){
                last = sentinel;
            }
            
            return r.item;
        }
    }

    public Item removeLast(){

        //needs to remove the reference to last by storing last
        //then removing the reference to it in the second to last item and the sentinel
        //there is also a sentinel node between last and first(which is sentinel.next)
        if(isEmpty()){
            return null;
        }
        else{
            Node r = last;
            last = last.previous;
            sentinel.previous = last;

            r.previous = null;//isolates r
            r.next = null;

            size--;
            return r.item;
        }
    }

    public Item get(int index){
        if(index>size || index < 0){
            return null;
        }
        else{
            Node g = sentinel.next;
            while(index>0){
                g = g.next;
                index--;
            }
            return g.item;
        }
    }

    private Item helper(int index, Node n){
        if(index == 0)
            return n.item;
        else
            return helper(index-1, n.next);
    }

    public Item getRecursive(int index){
        if(index>size || index < 0){
            return null;
        }
        else{
        Node n = sentinel.next;
        return helper(index, n);
        }
    }
}