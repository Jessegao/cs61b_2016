public class ArrayDeque<Item>
{
	private Item[] items;//vars from Hug's code
	private int size;
	private int front;
	private int last;

	private static int RFACTOR = 2;

	 public ArrayDeque() {
    	size = 0;
    	front = 0;
    	last = 0;
    	items = (Item[]) new Object[8];
    }

    private void resize() {
    	Item[] a = (Item[]) new Object[size*RFACTOR];
    	System.arraycopy(items, front, a, 0, items.length-front);
    	System.arraycopy(items, 0, a, items.length-front, front+1);
    	items = a;
    	front = 0;
    	last = size-1;
    }

    public void addFirst(Item thing)
    {
    	if(size == items.length)
    	{
    		resize();
    	}
    	if(items[front] != null)
    	{
    		moveFront(-1);
    	}
    	items[front] = thing;
        size++;
    }

    //adjusts front for you
    //i can only be 1 or -1
    private void moveFront(int i)
    {
    	if(front + i >= size)
    	{
    		front = 0;
    	}
    	else if(front + i < 0)
    	{
    		front = size-1;
    	}
    	else
    	{
    		front += i;
    	}
    }

    private void moveLast(int i)
    {
    	if(last + i >= size)
    	{
    		last = 0;
    	}
    	else if(last + i < 0)
    	{
    		last = size-1;
    	}
    	else
    	{
    		last += i;
    	}
    }


    public void addLast(Item thing)
    {
        if(size == items.length)
    	{
    		resize();
    	}
    	if(items[last] != null)
    	{
    		moveLast(1);
    	}
    	items[front] = thing;
        size++;
    }

    public boolean isEmpty()
    {
        return(size == 0);
    }

    public int size()
    {
        return size;
    }

    public void printDeque()
    {
        Node tracker = sentinel.next;
        if(size == 0)
        {
            return;
        }

        for(int i = 0; i < size-1; i++)
        {
            System.out.print(tracker.item.toString() + " ");
            tracker = tracker.next;
        }
        System.out.print(tracker.item.toString() + "\n");
    }

    public Item removeFirst()
    {
        if(isEmpty())
        {
            return null;
        }
        else
        {
            Node r = sentinel.next;//to be deleted

            sentinel.next = sentinel.next.next;//removes sentinel references to r
            sentinel.next.previous = sentinel;

            r.previous = null;//isolates r
            r.next = null;

            size--;
            return r.item;

        }
    }

    public Item removeLast()
    {
        if(isEmpty())
        {
            return null;
        }
        else
        {
            Node r = last;
            last = r.previous;
            r.previous = null;
            r.next = null;
            last.previous.next = sentinel;
            sentinel.previous = last;
            size--;
            return r.item;

        }
    }

    public Item get(int index)
    {
        if(index>size || index < 0)
        {
            return null;
        }
        else
        {
            Node g = sentinel.next;
            while(index>0)
            {
                g = g.next;
                index--;
            }
            return g.item;
        }
    }
}