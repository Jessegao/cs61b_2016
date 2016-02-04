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
    	items[last] = thing;
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
        if(size == 0)
        {
            return;
        }

        for(int i = front; i < items.length; i++)
        {
            System.out.print(items[i].toString() + " ");
        }

        for(int i = 0; i < last; i++)
        {
            System.out.print(items[i].toString() + " ");
        }

        System.out.print(items[last].toString() + "\n");
    }

    public Item removeFirst()
    {
        if(isEmpty())
        {
            return null;
        }
        else
        {
            Item r = items[front];//to be deleted

            items[front] = null;
            moveFront(1);

            size--;
            return r;

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
            Item r = items[last];//to be deleted
            items[last] = null;
            moveLast(-1);
            size--;
            return r;
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
            return items[index + front];
        }
    }
}