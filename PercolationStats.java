/* *****************************************************************************
 *  Name: Arjoban Singh
 *  Date: December 31, 2019
 *  Description: Stats file to experiment Percolation on number of times to get the average.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double[] percolatesAnswer;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        Percolation percolationTest;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        this.percolatesAnswer = new double[trials];

        // running experiment trial times
        for (int i = 0; i < trials; i++) {
            percolationTest = new Percolation(n);

            // until the grid percolates, keep opening new boxes
            while (!percolationTest.percolates())
            {
            int openRow = (StdRandom.uniform(n)) + 1;
            int openCol = (StdRandom.uniform(n)) + 1;

            percolationTest.open(openRow, openCol);
            }

            // total number of opened boxes after percolation
            int openSites = percolationTest.numberOfOpenSites();

            // threshold after every percolation and storing it in array
            percolatesAnswer[i] = openSites/ (double) (n * n);

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.percolatesAnswer);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.percolatesAnswer);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev())/Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev())/Math.sqrt(this.trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + percolationStats.confidenceLo() +", " + percolationStats.confidenceHi());
    }
}