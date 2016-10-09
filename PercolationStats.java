import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {
	private int n;
	private int t;
	private double[] threshold;
	private Percolation p;
	
   public PercolationStats(int n, int t)     // perform T independent experiments on an N-by-N grid
   {
   		if (n <= 0 || t <= 1) {
   			throw (new IllegalArgumentException("N cannot be nagative!"));
   			// If T=1, cannot compute stddev
		}
   		this.n = n;
   		this.t = t;
   		threshold = new double[t];
   		
   		simulate();
   }
   public double mean()                      // sample mean of percolation threshold
   {
	   double sum = 0;
	   for (double x : threshold) {
		   sum += x;
		   //StdOut.println(x);
	   }
	   return sum / t;
   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
	   double sqrsum = 0;
	   double m = mean();
	   for (double x : threshold) {
		   sqrsum += (m - x) * (m - x);
	   }
	   return sqrsum / (t-1);
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
	   return mean() - 1.96 * stddev() / Math.sqrt(t);
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
	   return mean() + 1.96 * stddev() / Math.sqrt(t);
   }
   
   private void simulate() {
	   int j, k;
	   for (int i = 0; i < t; i++) {
		   threshold[i] = 0;
		   p = new Percolation(n);
		   while (!p.percolates()) {
			   j = StdRandom.uniform(1, n+1);
			   k = StdRandom.uniform(1, n+1);
			   if (!p.isOpen(j, k)) {
				   p.open(j, k);
				   threshold[i] ++;
			   }
			   
		   }
		   threshold[i] /= n*n;
	   }
   }
   
   // java-algs4 PercolationStats 10 10
   public static void main(String[] args)    // test client (described below)
   {
	   int N = Integer.parseInt(args[0]);
	   int T = Integer.parseInt(args[1]);
	   PercolationStats ps = new PercolationStats(N, T);
	   //ps.simulate();
	   StdOut.println("mean                    = " + ps.mean());
	   StdOut.println("stddev                  = " + ps.stddev());
	   StdOut.println("95% confidence interval = " + ps.confidenceLo() 
			   + ", " + ps.confidenceHi());

   }
}
