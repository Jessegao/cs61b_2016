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
    	System.arraycopy(items, items.length-front, a, front, items.length);
    	items = a;    	
    }


}