public class PercolationStats {

    private double[] simulationResult;
    private int N, T;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) 
            throw new IllegalArgumentException();

        this.N = N;
        this.T = T;

        simulationResult = new double[T];

        for (int i = 0; i < T; i++) {
            simulationResult[i] = monteCarloSimulation();
        }
    } 

    private double monteCarloSimulation() {
        Percolation p = new Percolation(N);
        int count = 0;
        while (!p.percolates()) {
            int r1, r2;
            r1 = StdRandom.uniform(1, N+1);
            r2 = StdRandom.uniform(1, N+1);
            if (!p.isOpen(r1, r2)) {
                p.open(r1, r2);
                count++;
            }
        }
        return ((double) count) / (N*N);
    }

    // perform T independent computational experiments on an N-by-N grid
    public double mean() {
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += simulationResult[i];
        }
        return sum / T;
    }                           

    // sample mean of percolation threshold
    public double stddev() {
        double miu = mean();
        double tmpsum = 0;
        for (int i = 0; i < T; i++) {
            tmpsum += (simulationResult[i] - miu) * (simulationResult[i] - miu);
        }
        return Math.sqrt(tmpsum / (T - 1));
    }                       

    // sample standard deviation of percolation threshold
    public double confidenceLo() {
        double miu = mean();
        double sigma = stddev();
        return miu - (1.96 * sigma) / Math.sqrt(T);
    }                

    // returns lower bound of the 95% confidence interval
    public double confidenceHi() {
        double miu = mean();
        double sigma = stddev();
        return miu + (1.96 * sigma) / Math.sqrt(T);
    }           

    // returns upper bound of the 95% confidence interval
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        Stopwatch sw = new Stopwatch();
        PercolationStats p = new PercolationStats(N, T);
        System.out.printf("%23s = %f\n", "mean", p.mean());
        System.out.printf("%23s = %f\n", "stddev", p.stddev());
        System.out.printf("%23s = %f, %f\n", "95% confidence interval", p.confidenceLo(), p.confidenceHi());
        double time = sw.elapsedTime();
        System.out.printf("%23s = %f\n", "time", time);
    }  
}