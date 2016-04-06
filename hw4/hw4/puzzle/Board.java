package hw4.puzzle;

public class Board {

    private int[][] tiles;

    public Board(int[][] tiles) {
        this.tiles = tiles.clone(); //immutability
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    public int size() {
        return tiles.length;
    }

    public int hamming() {
        int correctNumberTracker = 1;
        int numbersOutOfPosition = 0;
        for (int[] row : tiles) { // iterates through the numbers and checks that each is in its place
            for (int i : row) {
                if (i != correctNumberTracker) {
                    numbersOutOfPosition++;
                }
                correctNumberTracker++;
            }
        }
        numbersOutOfPosition--; //takes into account the extra blank space
        return numbersOutOfPosition;
    }

    public int manhattan() {
        int outOfPlaceDistance = 0;
        for (int i = 0; i < tiles.length; i++) { // iterates through the numbers and checks that each is in its place
            for (int j = 0; j < tiles[i].length; j++) {
                int n = tiles[i][j] - 1;
                int differenceHeight = Math.abs((n / tiles.length) - i);
                int differenceWidth = Math.abs((n % tiles.length) - j);
                outOfPlaceDistance = outOfPlaceDistance + differenceHeight + differenceWidth;
            }
        }
        return outOfPlaceDistance;
    }

    public boolean isGoal() {
        int counter = 1;
        int[][] goal = new int[size()][size()];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (counter != size() * size()) {
                    goal[i][j] = counter;
                }
            }
        }
        return this.equals(goal);
    }
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }

        return this.tiles.equals(((Board) y).tiles);
    }
}
