

public class Percolation {
    private boolean[] grid;
    private WeightedQuickUnionUF allUnionRecord;
    private WeightedQuickUnionUF gridUnionRecord;

    private int N; 



    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();

        this.N = N;
        // content of the grid set to 0 by default
        grid = new boolean[N*N];

        // add two nodes: top and bottom
        allUnionRecord = new WeightedQuickUnionUF(N*N + 2);
        gridUnionRecord = new WeightedQuickUnionUF(N*N + 1);
    } 
   
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {

        checkInRange(i, j);

        int cur = N*(i-1) + (j-1);

        if (grid[cur] == true)
            return;

        grid[cur] = true;
        int[] neighborhood = getNeighborhood(i, j);
        for (int t : neighborhood) {
            // neighbour exists
            if (t >= 0) {
                int ti, tj;
                ti = t / N + 1;
                tj = t - (ti - 1) * N + 1;
                if (isOpen(ti, tj)) {
                    // connect current site and its neighbours
                    allUnionRecord.union(cur, t);
                    gridUnionRecord.union(cur, t);
                }
            }

        }

        // connect to top or bottom
        if (i == 1) {
            // connect to the top node
            allUnionRecord.union(cur, N*N);
            gridUnionRecord.union(cur, N*N);
        }
        if (i == N) {
            // connect to the bottom node
            allUnionRecord.union(cur, N*N + 1);
        }
    }          

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkInRange(i, j);        
        return grid[N*(i-1) + (j-1)];
    }     

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkInRange(i, j);        
        int cur = N*(i-1) + (j-1);
        return gridUnionRecord.connected(cur, N*N);
    }    
    public boolean percolates() {
        return allUnionRecord.connected(N*N, N*N+1);
    }               // does the system percolate?

    private boolean connectedToBottom(int n) {
        return allUnionRecord.connected(n, N*N+1);
    }
/*    public static void main(String[] args) {
        Percolation p = new Percolation(3);
    }*/

    private int[] getNeighborhood(int i, int j) {
        if (N == 1) {
            int[] tmp = {-1};
            return tmp;
        }

        int cur = N * (i - 1) + (j - 1);

        int[] neighborhood = {cur - N, cur + N, cur - 1, cur + 1};

        if (i == 1) {
            neighborhood[0] = -1;
        }
        if (i == N) {
            neighborhood[1] = -1;
        }
        if (j == 1) {
            neighborhood[2] = -1;
        }
        if (j == N) {
            neighborhood[3] = -1;
        }

        return neighborhood;
    }


    private void checkInRange(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException();
    }


    public static void main(String[] args) {
    }
}