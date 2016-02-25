package creatures;
import huglife.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;

/**
 * Created by cs61b-bog on 2/25/16.
 */
public class TestClorus {
    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.01);
        p.move();
        assertEquals(1.94, p.energy(), 0.01);
        p.stay();
        assertEquals(1.93, p.energy(), 0.01);
        p.stay();
        assertEquals(1.92, p.energy(), 0.01);
    }
     @Test
     public void testReplicate() {
         Clorus p = new Clorus(2);
         Clorus newborn = p.replicate();
         double pp = p.energy();
         double np = newborn.energy();
         assertEquals(pp, 1.0, 0.1);
         assertEquals(np, 1.0, 0.1);
         assertNotSame(p, newborn);
     }

    @Test
    public void testChoose() {
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        surrounded.put(Direction.BOTTOM, new Empty());
        actual = p.chooseAction(surrounded);
        expected = new Action(Action.ActionType.REPLICATE, Direction.BOTTOM);
        assertEquals(expected, actual);

        actual = p.chooseAction(surrounded);
        double pp = p.energy();

        expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);
        assertEquals(expected, actual);
        assertEquals(0.5, pp, 0.1);
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Empty());
        actual = p.chooseAction(surrounded);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
}
