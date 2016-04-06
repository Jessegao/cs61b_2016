package hw4.puzzle;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTester {

    @Test
    public void testIsGoal() {
        int[][] tiles = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board goal = new Board(tiles);

        assertTrue(goal.isGoal());
    }

    @Test
    public void testEquals() {
        int[][] tiles = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        assertTrue(new Board(tiles).equals(new Board(tiles)));
    }

    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", BoardTester.class);
    }
}
