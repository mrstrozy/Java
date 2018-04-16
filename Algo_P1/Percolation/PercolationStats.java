
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    
    double[] trialRec;
    int numTrials;
    
    public PercolationStats(int n, int trials) {
        
        int numSites = n*n;
        int row, col;
        Percolation p;
        this.numTrials = trials;
        this.trialRec = new double[trials];
        
        // Sanity checks
        if( (n < 1) || (trials < 1) ) {
            throw new java.lang.IllegalArgumentException();
        }
        
        for(int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while(!p.percolates()) {
                row = StdRandom.uniform(n)+1;
                col = StdRandom.uniform(n)+1;
            
                // Open a site
                p.open(row, col);
            }
            
            //StdOut.println("It Percolated");
            //StdOut.printf("num open: %d | num sites: %d\n", p.numberOfOpenSites(), numSites);
            
            this.trialRec[i] = (double) p.numberOfOpenSites()/(double) numSites;
            
        }
    }
    
    public double mean() {
        return StdStats.mean(this.trialRec);
    }
    
    public double stddev() {
        return StdStats.stddev(this.trialRec);
    }
    
    public double confidenceLo() {
     
        return mean() - (1.96*stddev())/Math.sqrt(this.numTrials);
        
    }
    
    public double confidenceHi() {
        return mean() + (1.96*stddev())/Math.sqrt(this.numTrials);
    }
    
    public static void main(String[] args) {
        
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        
        PercolationStats per = new PercolationStats(n, t);
        
        StdOut.printf("mean                      = %f\n", per.mean());
        StdOut.printf("stddev                    = %f\n", per.stddev());
        StdOut.printf("95%% confidence interval   = [%f , %f]\n", per.confidenceLo(), per.confidenceHi());
        
    }
    
}