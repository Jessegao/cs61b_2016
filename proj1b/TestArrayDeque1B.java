import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDeque1B{
	@Test
	public void testAddRemoveFront(){
		System.out.println("Testing for adding to the front will begin");
		StudentArrayDeque<Integer> ints = new StudentArrayDeque();
		for(i = 0, i<32, i++){
			ints.addFront(i);
		}
		assertEquals(ints.size() == 32);
	}

	@Test
	public void testGet(){
		System.out.println("Testing for the get method will begin. If this test fails, disregard all bottom cases");
		StudentArrayDeque<Integer> ints = new StudentArrayDeque();
		for(i = 0, i<32, i++){
			ints.addLast(i);
			int g = ints.get(i);
			assertEquals(i, g);
		}
	}
	@Test
	public void randomAddRemove(){
		System.out.println("Testing add and removing randomly with 500 calls");
		StudentArrayDeque<Integer> ints = new StudentArrayDeque();
	}
}