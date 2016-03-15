package hw2;                       
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double[] thresholds;
    private int T;

    public PercolationStats(int N, int T) {
        this.T = T;

        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        thresholds = new double[T];

        for (int i = 0; i < T; i++) {
            thresholds[i] = getThreshHold(N);
        }
    }

    private double getThreshHold(int dimension) {
        Percolation percolation = new Percolation(dimension);
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(dimension), StdRandom.uniform(dimension));
        }
        return ((double) percolation.numberOfOpenSites()) / ((double) dimension * dimension);
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        return mean() - (1.96 * stddev() / (Math.sqrt((double) T)));
    }
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / (Math.sqrt((double) T)));
    }
}                       
