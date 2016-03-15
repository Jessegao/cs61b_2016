package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int dimension; // keeps track of dimensions of the grid
    private boolean[][] grid;
    private WeightedQuickUnionUF set;
    private int top;
    private int bottom;
    private int boxesOpen = 0;
    private WeightedQuickUnionUF noBottomSet;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        dimension = N;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        set = new WeightedQuickUnionUF(N * N + 2);
        noBottomSet = new WeightedQuickUnionUF(N * N + 2);
        top = N * N;
        bottom = N * N + 1;
    }

    public void open(int row, int col) {
        if (row == 0) {
            set.union(top, convertRowColToInt(row, col));
            noBottomSet.union(top, convertRowColToInt(row, col));
            // doesn't connect the bottom to the set
        }
        if (row == dimension - 1) {
            set.union(bottom, convertRowColToInt(row, col));
        }

        if (!grid[row][col]) {
            grid[row][col] = true;
            connectSurrounding(row, col);
            boxesOpen++;
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row > dimension - 1 || col < 0 || col > dimension - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    private void connectSurrounding(int row, int col) {
        try {
            if (isOpen(row - 1, col)) {
                set.union(convertRowColToInt(row - 1, col), convertRowColToInt(row, col));
                noBottomSet.union(convertRowColToInt(row - 1, col), convertRowColToInt(row, col));
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            row = row;
        }
        try {
            if (isOpen(row + 1, col)) {
                set.union(convertRowColToInt(row + 1, col), convertRowColToInt(row, col));
                noBottomSet.union(convertRowColToInt(row + 1, col), convertRowColToInt(row, col));
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            row = row;
        }
        try {
            if (isOpen(row, col - 1)) {
                set.union(convertRowColToInt(row, col - 1), convertRowColToInt(row, col));
                noBottomSet.union(convertRowColToInt(row, col - 1), convertRowColToInt(row, col));
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            row = row;
        }
        try {
            if (isOpen(row, col + 1)) {
                set.union(convertRowColToInt(row, col + 1), convertRowColToInt(row, col));
                noBottomSet.union(convertRowColToInt(row, col + 1), convertRowColToInt(row, col));
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            row = row;
        }
    }

    public boolean isFull(int row, int col) {
        return noBottomSet.connected(convertRowColToInt(row, col), top);
    }

    public int numberOfOpenSites() {
        return boxesOpen;
    }
    public boolean percolates() {
        return set.connected(top, bottom);
    }

    private int convertRowColToInt(int row, int col) {
        return row * dimension + col;
    }
    //public static void main(String[] args)   // unit testing (not required)
}                       
