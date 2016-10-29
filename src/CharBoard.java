import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class CharBoard {

  private final int size;
  
  // use one dimensional array to save memory
  private char[] blocks;

  // construct a board from an n-by-n array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public CharBoard(int[][] blocks) {

    if (blocks == null || blocks.length != blocks[0].length) {
      throw new java.lang.IllegalArgumentException();
    }

    this.blocks = new char[blocks.length * blocks.length];
    size = blocks.length;

    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        this.blocks[i * blocks.length + j] = (char) blocks[i][j];
      }
    }
  }

  // board dimension n
  public int dimension() {
    return size;
  }
  
  // for visualizer
  public int getFromRowCol(int i, int j) {
    return blocks[i * size + j];
  }

  // number of blocks out of place
  public int hamming() {
    int distance = 0;
    for (int i = 0; i < blocks.length; i++) {
      // blank square is not considered a block
      if (blocks[i] != 0 && blocks[i] != i + 1) {
        distance++;
      }
    }

    return distance;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() {
    int distance = 0;
    for (int i = 0; i < blocks.length; i++) {

      // blank square is not considered a block
      if (blocks[i] == 0) {
        continue;
      }
      
      // Since the values in the goal board are ordered starting from 1
      // in order to find the index of a value in the goal board we just
      // subtract 1 to find the zero-based index.
      // Once we have the index, finding row and column is straightforward.
      int index = blocks[i] - 1;
      // account for blank square being in the last position, not first
      index = index < 0 ? blocks.length - 1 : index;

      int currentValueRow = i / (size);
      int currentValueCol = i % (size);
      int goalValueRow = index / (size);
      int goalValueCol = index % (size);

      distance += Math.abs(currentValueRow - goalValueRow) +
          Math.abs(currentValueCol - goalValueCol);
    }
    return distance;
  }

  // is this board the goal board?
  public boolean isGoal() {
    for (int i = 0; i < blocks.length; i++) {
      if (blocks[i] != 0 && blocks[i] != i + 1) {
        return false;
      }
    }

    return true;
  }

  // a board that is obtained by exchanging any pair of blocks
  public CharBoard twin() {

    char[] blocksCopy = blocks.clone();

    int randomIndexFrom = StdRandom.uniform(0, blocks.length);
    int randomIndexTo = StdRandom.uniform(0, blocks.length);

    // blank square is not a block
    while (blocksCopy[randomIndexFrom] == 0) {
      randomIndexFrom = StdRandom.uniform(0, blocks.length);
    }

    // blank square is not a block
    while (blocksCopy[randomIndexTo] == 0 || 
        blocksCopy[randomIndexFrom] == blocksCopy[randomIndexTo]) {
      randomIndexTo = StdRandom.uniform(0, blocks.length);
    }

    // swap values to create the twin
    char tmpVal = blocksCopy[randomIndexFrom];
    blocksCopy[randomIndexFrom] = blocksCopy[randomIndexTo];
    blocksCopy[randomIndexTo] = tmpVal;

    return new CharBoard(monoToBidi(blocksCopy));
  }

  // convert a monodimensional array to a bidimensional one
  private int[][] monoToBidi(final char[] array) {
    int[][] bidi = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        bidi[i][j] = array[i * size + j];
      }
      //System.arraycopy(array, (i * size), bidi[i], 0, size);
    }
    return bidi;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == this) {
      return true;
    }

    if (y == null) {
      return false;
    }

    if (y.getClass() != this.getClass()) {
      return false;
    }

    CharBoard other = (CharBoard) y;

    if (this.dimension() != other.dimension()) {
      return false;
    }

    for (int i = 0; i < blocks.length; i++) {
      if (blocks[i] != other.blocks[i]) {
        return false;
      }
    }

    return true;
  }

  // all neighboring boards
  public Iterable<CharBoard> neighbors() {
    int[][] directions = new int[][] { { 1, 0 }, // BOTTOM
        { -1, 0 }, // TOP
        { 0, -1 }, // LEFT
        { 0, 1 } // RIGHT
    };

    Stack<CharBoard> neighbors = new Stack<>();

    for (int i = 0; i < blocks.length && neighbors.size() == 0; i++) {

      // In this case we use the blank square as a normal black
      //  and we move it in the allowed directions to find neighbors
      if (blocks[i] != 0) {
        continue;
      }

      char[] blocksCopy;

      for (int d = 0; d < directions.length; d++) {
        blocksCopy = blocks.clone();

        // convert index to row and column for simplicity
        int tmpi = (i / size) + directions[d][0];
        int tmpj = (i % size) + directions[d][1];

        if (isValidCoordinate(tmpi, tmpj)) {
          char tmpVal = blocksCopy[i];
          blocksCopy[i] = blocksCopy[tmpi * size + tmpj];
          blocksCopy[tmpi * size + tmpj] = tmpVal;
          neighbors.push(new CharBoard(monoToBidi(blocksCopy)));
        }
      }

    }

    return neighbors;
  }

  private boolean isValidCoordinate(int i, int j) {
    if (i >= 0 && i < size && j >= 0 && j < size) {
      return true;
    }

    return false;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder outString = new StringBuilder();
    outString.append(size);
    outString.append(System.lineSeparator());
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        outString.append(" ");
        outString.append((int)blocks[i * size + j]);
      }
      outString.append(System.lineSeparator());
    }

    return outString.toString();
  }
  
  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    
//    char a = (char)1;
//    int b = (int) a;
//    System.out.println(b);
    
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    CharBoard initial = new CharBoard(blocks);

    // solve the puzzle
    CharSolver solver = new CharSolver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (CharBoard board : solver.solution())
        StdOut.println(board);
    }
  }
}