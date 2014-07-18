import java.util.Stack;

public class Board {
    private int[] tiles;
    private int N;
    private int dimension;
    private int blankIndex;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    // board dimension N
    public Board(int[][] blocks) {
        dimension = blocks.length;
        N = dimension * dimension;
        tiles = new int[N];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tiles[i * dimension + j] = blocks[i][j];

                // record where is the blank tile
                if (tiles[i * dimension + j] == 0)
                    blankIndex = i * dimension + j;
            }
        }
    }
    
    // copy constructor
    private Board(Board another) {
        this.dimension = another.dimension;
        this.N = another.N;
        this.blankIndex = another.blankIndex;
        this.tiles = new int[N];
        System.arraycopy(another.tiles, 0, this.tiles, 0, N);
    }

    // swap two values in the tiles
    private void swap(int i, int j) {
        assert i >= 0 && j >= 0 && i < N && j < N;
        tiles[i] ^= tiles[j];
        tiles[j] ^= tiles[i];
        tiles[i] ^= tiles[j];
    }
                          
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            // skip the blank tile and check if the tile is in the right position
            // to compare `tiles[i] != i + 1` is because our index starts from 0
            if (tiles[i] != 0 && tiles[i] != i + 1)
                distance++;
        }

        return distance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            int currentXCoordinate = i / dimension;
            int currentYCoordinate = i % dimension;

            int targetXCoordinate = (tiles[i] - 1) / dimension;
            int targetYCoordinate = (tiles[i] - 1) % dimension;

            if (tiles[i] != 0)
                distance += Math.abs(targetXCoordinate - currentXCoordinate) + 
                            Math.abs(targetYCoordinate - currentYCoordinate);
        }
        
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            if (tiles[i] != 0 && tiles[i] != i + 1)
                return false;
        }
        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int swapIndex = 0;
        Board twinBoard = new Board(this);

        /*
         * my initial approach
        for (int i = 0; i < N - 1; i++) {
            // get the index that we can swap tiles[i] and tiles[i + 1]
            // 1. make sure two tiles both are not 0
            // 2. make sure tiles[i] is not at the end of the line
            if (tiles[i] != 0 && tiles[i + 1] != 0 && i % dimension < dimension - 1) {
                twinBoard.swap(i, i + 1);
                break;
            }
        }
        */


        // swap the first two elements on the blank tile's next row
        int row = ( (blankIndex / dimension) + 1 ) % dimension;
        twinBoard.swap(row * dimension, row * dimension + 1);
        return twinBoard;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        if (this.blankIndex != that.blankIndex) return false;

        int N = that.dimension() * that.dimension();

        for (int i = 0; i < N; i++) {
            if (this.tiles[i] != that.tiles[i])
                return false;
        }

        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborStack = new Stack<Board>();

        // indicates its neighbors
        int left = 1;
        int right = 1;
        int up = 1;
        int down = 1;

        // on the top
        if (blankIndex / dimension == 0)
            up = 0;

        // at the bottom
        if (blankIndex / dimension == dimension - 1)
            down = 0;

        // on the left
        if (blankIndex % dimension == 0)
            left = 0;

        // on the right
        if (blankIndex % dimension == dimension - 1)
            right = 0;

        // push the neighborBoards to stack
        if (left == 1) {
            Board neighbor = new Board(this);
            neighbor.swap(blankIndex, blankIndex - 1);
            neighbor.blankIndex = blankIndex - 1;
            neighborStack.push(neighbor);
        }

        if (right == 1) {
            Board neighbor = new Board(this);
            neighbor.swap(blankIndex, blankIndex + 1);
            neighbor.blankIndex = blankIndex + 1;
            neighborStack.push(neighbor);
        }

        if (up == 1) {
            Board neighbor = new Board(this);
            neighbor.swap(blankIndex, blankIndex - dimension);
            neighbor.blankIndex = blankIndex - dimension;
            neighborStack.push(neighbor);
        }

        
        if (down == 1) {
            Board neighbor = new Board(this);
            neighbor.swap(blankIndex, blankIndex + dimension);
            neighbor.blankIndex = blankIndex + dimension;
            neighborStack.push(neighbor);
        }

        return neighborStack;
    }


    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension + "\n");
        for (int i = 0; i < N; i++) {
            sb.append(String.format("%2d ", tiles[i]));
            if (i % dimension == dimension - 1) sb.append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println("initial:\n" + initial.toString());

        for (Board board : initial.neighbors()) {
            System.out.println("======");
            System.out.println(board.toString());
            System.out.println("manhattan(): " + board.manhattan());
        }
/*

        System.out.println(initial.toString());


        System.out.println(b.toString());

        System.out.println(initial.isGoal());
*/
    }

}