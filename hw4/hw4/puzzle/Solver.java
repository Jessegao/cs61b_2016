package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    // DO NOT MODIFY MAIN METHOD
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
       }
    }

    private class SearchNode implements Comparable{
        public Board board;
        public int moves;
        public SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        public int compareTo(Object obj) {
            if (! (obj instanceof SearchNode)) {
                throw new RuntimeException("bad compare");
            }

            SearchNode searchNode = (SearchNode) obj;
            return getPriority() - searchNode.getPriority();
        }

        public int getPriority() {
            return moves + board.manhattan();
        }
    }

    private ArrayList<Board> boards;
    private int numberMoves = 0;
    private MinPQ<SearchNode> minPQ;

    public Solver(Board initial) {
        boards = new ArrayList<Board>();
        minPQ = new MinPQ<SearchNode>();
        minPQ.insert(new SearchNode(initial, 0, null));
        findPath();
    }

    private void findPath() {
        SearchNode search = minPQ.delMin();
        if (search.moves > boards.size() - 1) {
            boards.add(search.moves, search.board);
        } else {
            boards.set(search.moves, search.board);
        }
        if (search.board.isGoal()) {
            numberMoves = search.moves;
            return;
        } else {
            for (Board b : BoardUtils.neighbors(search.board)) {
                if (!b.equals(search.board)) {
                    minPQ.insert(new SearchNode(b, search.moves + 1, search));
                }
            }
            findPath();
        }
    }

    public int moves() {
        return numberMoves;
    }

    public Iterable<Board> solution() {
        return boards;
    }
}
