
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    
    int[][] grid;
    WeightedQuickUnionUF uf;
    int length, top, bottom, numSitesOpen;
    /*
     * For Grid: 
     *
     *  0 - closed
     *  1 - open
     * 
     */
    
    public Percolation(int n) {
    
        // Need 2 extra for absolute top and bottom
        this.uf = new WeightedQuickUnionUF((n)*(n) + 2);
        this.grid = new int[n][n];
        this.numSitesOpen = 0;
        this.top = n*n;
        this.bottom = n*n + 1;
        this.length = n;
        
        //StdOut.println(this.bottom);
        //StdOut.println(this.length);
        
        // Connect the top row to the top site and the bottom row to 
        // the bottom site
        
        // Top
        for (int i = 0; i < n; i++) {
            this.uf.union(this.top, i);   
        }
        
        // Bottom
        for(int i = n*(n-1); i < n*n; i++) {
            this.uf.union(this.bottom, i);   
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                this.grid[i][j] = 0;
            }
        }
    }
    
    private void checkInputs(int row, int col) {
        
        //StdOut.printf("row: %d | col: %d\n", row, col);
        
        if((row < 1) || (row > this.length) || (col < 1) || (col > this.length)){
            throw new java.lang.IllegalArgumentException();
        }
    }
    
    private int convertInput(int row, int col) {
        
        return row*length + col;
    }
    
    private boolean siteAboveOpen(int row, int col) {
     
        
        if((row > 0) && isOpen(row-1, col)) { 
            //StdOut.println("There is an OPEN site ABOVE");
            return true;
        }
        
        return false;
    }
    
    private boolean siteLeftOpen(int row, int col) {
        if((col > 0) && isOpen(row, col-1)) { 
            //StdOut.println("There is an  OPEN site LEFT");
            return true; 
        }
        
        return false;
    }
    
    private boolean siteBelowOpen(int row, int col) {
        if((row < (this.length - 1)) && isOpen(row+1, col)) { 
            //StdOut.println("There is an OPEN site BELOW");
            return true; 
        }
        
        return false;
    }
    
    private boolean siteRightOpen(int row, int col) {
        if((col < (this.length - 1)) && isOpen(row, col+1)) { 
            //StdOut.println("There is an OPEN site RIGHT");
            return true; 
        }
           
        return false;
    }
    
    public void open(int row, int col) {
        
        // Check if input is OK
        checkInputs(row, col);
        
        // Convert to our grid
        
        row = row - 1;
        col = col - 1;
        
        // Check if already open
        if(this.grid[row][col] == 1) {
            return;
        }
        
        // Open the site
        this.grid[row][col] = 1;
        this.numSitesOpen += 1;
        
        //StdOut.printf("Opened site: %d %d \n", row, col);
        // Now check all sides and merge the components
        
        if(siteLeftOpen(row, col)) {
            this.uf.union(convertInput(row, col-1), convertInput(row, col));
            //StdOut.printf("Unioning: %d %d  AND  %d %d \n", row, col, row, col-1);
        }
        
        if(siteBelowOpen(row, col)) {
            this.uf.union(convertInput(row+1, col), convertInput(row, col));
            //StdOut.printf("Unioning: %d %d  AND  %d %d \n", row+1, col, row, col);
        }
        
        if(siteRightOpen(row, col)) {
            this.uf.union(convertInput(row, col+1), convertInput(row, col));
            //StdOut.printf("Unioning: %d %d  AND  %d %d \n", row, col+1, row, col);
        }
        
        if(siteAboveOpen(row, col)) {
            this.uf.union(convertInput(row-1, col), convertInput(row, col));
            //StdOut.printf("Unioning: %d %d  AND  %d %d \n", row-1, col, row, col);
        }
            
        
    }
    
    public boolean isOpen(int row, int col) {
     
        // Convert the inputs
        row = row + 1;
        col = col + 1;
        
        checkInputs(row, col);
        return this.grid[row-1][col-1] == 1;
        
    }
    
    public boolean isFull(int row, int col) {
        
        checkInputs(row, col);
        
        return this.uf.connected(convertInput(row, col), this.top);
    }
        
    public int numberOfOpenSites() { return this.numSitesOpen;}
    
    public boolean percolates() { 
        return this.uf.connected(this.top, this.bottom);
    }
    
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
    }
    
    
}