/**
 * Created by jesse on 2/4/16.
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class TestLLDeque {
    @Test
    public void testGet(){
        LinkedListDeque<Integer> ints = new LinkedListDeque<Integer>();
        ints.addFirst(5);
        ints.addFirst(4);
        ints.addFirst(3);
        ints.addFirst(2);
        ints.addFirst(1);
        ints.addLast(6);
        ints.addLast(7);
        ints.printDeque();
        int g = ints.get(0);//check that get is returning the right things
        int g2 = ints.get(6);
        assertEquals(1, g);
        assertEquals(7, g2);
    }

    @Test
    public void testRemove(){
        LinkedListDeque<Integer> ints = new LinkedListDeque<Integer>();
        ints.addFirst(2);
        ints.addFirst(1);

        ints.printDeque();
        ints.removeLast();
        ints.printDeque();
        ints.removeFirst();
        ints.printDeque();
        ints.removeFirst();
        ints.printDeque();
    }

    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", TestLLDeque.class);
    }
}
