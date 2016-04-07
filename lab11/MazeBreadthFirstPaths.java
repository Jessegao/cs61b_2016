import java.util.Observable;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    public Maze maze;
    */

    private int start;
    private int target;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        start = m.xyTo1D(sourceX, sourceY);
        target = m.xyTo1D(targetX, targetY);
    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int start) {
        /* Your code here. */
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int v = 0; v < maze.V(); v++) {
            distTo[v] = Integer.MAX_VALUE;
        }
        distTo[start] = 0;
        edgeTo[start] = start;
        marked[start] = true;
        q.add(start);

        while (!q.isEmpty()) {
            int v = q.remove();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(start);
    }
}

