public class ArrayDeque<Item>{
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
    	Item[] a = (Item[]) new Object[items.length*RFACTOR];
/*
    	System.arraycopy(items, front, a, 0, items.length-front);
    	System.arraycopy(items, 0, a, items.length-front, front);
    	*/
    	for(int i = 0; i < size; i++){
    			a[i] = items[front];
    			moveFront(1);
    		}

    	items = a;
    	last = size-1;
    }

    public void addFirst(Item thing){
    	if(size == items.length){
    		resize();
    	}
    	if(items[front] != null){
    		moveFront(-1);
    	}
    	items[front] = thing;
        size++;
    }

    //adjusts front for you
    //i can only be 1 or -1
    private void moveFront(int i){
    	if(front + i >= items.length){
    		front = 0;
    	}
    	else if(front + i < 0){
    		front = items.length-1;
    	}
    	else{
    		front += i;
    	}
    }

    private void moveLast(int i)
    {
    	if(last + i >= items.length){
    		last = 0;
    	}
    	else if(last + i < 0){
    		last = items.length-1;
    	}
    	else{
    		last += i;
    	}
    }


    public void addLast(Item thing){
        if(size == items.length){
    		resize();
    	}
    	if(items[last] != null){
    		moveLast(1);
    	}
    	items[last] = thing;
        size++;
    }

    public boolean isEmpty(){
        return(size == 0);
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        if(size == 0){
            return;
        }
        else{
        	for(int i = front; i < items.length; i++){
        		if(items[i]!=null){
        			System.out.print(items[i].toString() + " ");
        		}
        	}

       	 	for(int i = 0; i <= last; i++){
            	if(items[i]!=null){
        			System.out.print(items[i].toString() + " ");
        		}
        	}
  		}	
    }

    private void downsize(){
    	if(size*4<items.length && items.length>=16){
    		Item[] a = (Item[]) new Object[size*RFACTOR];
    		/*
    		System.arraycopy(items, front, a, 0, items.length-front); // copies items from front to the end of the array
    		System.arraycopy(items, 0, a, items.length-front, last+1);
    		items = a;*/
    		for(int i = 0; i < size; i++){
    			a[i] = items[front];
    			moveFront(1);
    		}
    		items = a;
    		last = size-1;
    	}
    }

    public Item removeFirst(){
        if(isEmpty()){
            return null;
        }
        else{
            Item r = items[front];//to be deleted

            items[front] = null;
            size--;

            if(front != last)
            	{moveFront(1);}

            downsize();
            return r;
        }
    }

    public Item removeLast(){
        if(isEmpty()){
            return null;
        }
        else{
            Item r = items[last];//to be deleted
            
            items[last] = null;
            size--;

            if(last != front){
            	moveLast(-1);
            }

            downsize();
            return r;
        }
    }

    public Item get(int index){
        if(index>=size || index < 0){
            return null;
        }
        else{
            return items[(index + front)%(items.length-1)];
        }
    }
}