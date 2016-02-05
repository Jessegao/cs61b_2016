import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest{
	@Test
	public void testAddFirst(){
		ArrayDeque<Integer> ints = new ArrayDeque<Integer>();
		for(int i = 16; i >=0; i--)
		{
			ints.addFirst(i);//sees if it resizes
		}
		System.out.println("the size is " + ints.size() + "\n");
		int g = ints.get(0);
		int g2 = ints.get(7);
		int g3 = ints.get(15);
		ints.printDeque();
		assertEquals(9,g);
		assertEquals(1,g2);
	}
	
	@Test
	public void testAddLast(){
		ArrayDeque<Integer> ints = new ArrayDeque<Integer>();
		for(int i = 0; i < 20; i++)
		{
			ints.addLast(i);//sees if it resizes
		}
		System.out.println("the size is " + ints.size() + "\n");
		int g = ints.get(0);
		int g2 = ints.get(7);
		int g3 = ints.get(15);
		ints.printDeque();
		assertEquals(0,g);
		assertEquals(7,g2);
		assertEquals(15,g3);
	}

	@Test 
	public void testRemoves(){
		ArrayDeque<Integer> ints = new ArrayDeque<Integer>();
		ints.addFirst(1);
		int r = ints.removeFirst();
		assertEquals(1, r);
		ints.addLast(9);
		int r2 = ints.removeLast();
		assertEquals(9, r2);

	}

	public static void main(String[] args) {
		jh61b.junit.TestRunner.runTests("all", ArrayDequeTest.class);
	}
}