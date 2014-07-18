
public class Solver {

    private int moves;
    private Queue<Board> solutionQueue;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        
        Board twin = initial.twin();

        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();

        // create two search trees, one is the original board, 
        // the other is its twin,
        // only one of them can terminate
        SearchNode currentSearchNode = new SearchNode(initial, null);
        SearchNode currentSearchNodeTwin = new SearchNode(twin, null);

        solutionQueue = new Queue<Board>();

        minPQ.insert(currentSearchNode);
        minPQTwin.insert(currentSearchNodeTwin);

        // terminate when one of it gets to destination
        while (!(currentSearchNode.board.isGoal() || currentSearchNodeTwin.board.isGoal())) {
            currentSearchNode = minPQ.delMin();
            currentSearchNodeTwin = minPQTwin.delMin();
            solutionQueue.enqueue(currentSearchNode.board);

            // iterate through its neighbors, if it is not the same as its parent,
            // insert it to the priority queue
            for (Board neighbor : currentSearchNode.board.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighbor, currentSearchNode);
                if (currentSearchNode.previous == null || !neighbor.equals(currentSearchNode.previous.board))
                    minPQ.insert(neighborNode);
            }


            for (Board neighbor : currentSearchNodeTwin.board.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighbor, currentSearchNodeTwin);
                if (currentSearchNodeTwin.previous == null || !neighbor.equals(currentSearchNodeTwin.previous.board))
                    minPQTwin.insert(neighborNode);
            }
        }

        if (currentSearchNode.board.isGoal()) {
            solvable = true;
            moves = currentSearchNode.moves;
        } else {
            moves = -1;
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;

        private SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.moves = previous != null ? previous.moves + 1 : 0;
            this.previous = previous;
        }

        private int priority() {
            return board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            if (this.priority() != that.priority())
                return this.priority() - that.priority();
            else
                return this.board.manhattan() - that.board.manhattan();

        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board -1 if no solution
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (isSolvable())
            return solutionQueue;
        else
            return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }


}