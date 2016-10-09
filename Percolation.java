import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private char[][] sites;
    private WeightedQuickUnionUF uf;
    private int n;
    private int top, bottom;
    
    public Percolation(int n)               // create N-by-N grid, with all sites blocked 
    {
    	if (n <= 0) { throw (new IllegalArgumentException("N cannot be nagative!"));}
        this.n = n;
        top = 0;
        bottom = n*n+1;
        
        uf = new WeightedQuickUnionUF(n*n+2);
        
    	sites = new char[n+1][n+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                sites[i][j] = 'f';
            }
        }

    }
    
    private void checkBoundary (int i, int j) {
    	if (i<1 || i>n || j<1 || j>n) {
    		throw (new IndexOutOfBoundsException("Index out of bound!"));
    	}
    }
    
    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
    	checkBoundary(i, j);
    	if (isOpen(i, j)) return;
    	sites[i][j] = 'o';
    	int current = n*(i-1)+j;
        if (i == 1) { uf.union(current, top); }
        else { 
        	if (isOpen(i-1, j)) 
        	{ uf.union(current, current - n); } 
    	} // up
        if (i == n) { uf.union(current, bottom); }
        else { 
        	if (isOpen(i+1, j)) 
        		{ uf.union(current, current + n); } } // down
        if (j != 1) { 
        	if (isOpen(i, j-1)) 
        		{ uf.union(current, current - 1); } } // left
        if (j != n) 
        {	if (isOpen(i, j+1)) 
        	{ uf.union(current, current + 1); } } // right
    }
    
    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
	{
    	checkBoundary(i, j);
		return sites[i][j] == 'o';
	}
    

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
    	checkBoundary(i, j);
    	return uf.connected(top, n*(i-1)+j);
    }

    public boolean percolates()             // does the system percolate?
    {
    	return uf.connected(top, bottom);
    }
    
    public static void main(String[] args)  // test client (optional)
    {
    	String filename;
    	In in;
    	try 
    	{
    		filename = args[0];
    		in = new In(filename);
    		int N = in.readInt();
    		Percolation perc = new Percolation(N);
    		while (!in.isEmpty()) {	
    			int p = in.readInt();
    			int q = in.readInt();
    			perc.open(p, q);
    			//StdOut.println(p + " " + q);
    		}
    		StdOut.println("The system percolates: " + perc.percolates());
    	}
        catch (Exception e) 
    	{
            e.printStackTrace();        
        }

    }
}

