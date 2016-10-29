import java.awt.Font;

import edu.princeton.cs.algs4.StdDraw;

public class PuzzleVisualizer {

  // delay in miliseconds (controls animation speed)
  private static final int DELAY = 1000;

  // draw n-by-n percolation system
  public static void draw(Board b, int n, int solutionMoves, float solveTime) {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.GRAY);
    StdDraw.filledSquare(n / 2.0, n / 2.0, n);
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setXscale(-0.05 * n, 1.05 * n);
    StdDraw.setYscale(-0.05 * n, 1.05 * n); // leave a border to write text
    StdDraw.filledSquare(n / 2.0, n / 2.0, n / 2.0);

    StdDraw.setFont(new Font("SansSerif", Font.BOLD, 42));
    StdDraw.setPenColor(StdDraw.WHITE);
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(j - 0.5, n - i + 0.5, 0.48);
        StdDraw.setPenColor(StdDraw.BLACK);
        int num = b.getFromRowCol(i - 1, j - 1);
        if (num != 0) {
          StdDraw.text(j - 0.5, n - i + 0.5, String.format("%s", num));
        }
      }
    }

    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.text(0.25 * n, -0.025 * n, "Solved in " + solveTime + " seconds");
    if (solutionMoves != 0) {
      StdDraw.text(0.75 * n, -0.025 * n, "Minimum number of moves = " + solutionMoves);
    } else {
      StdDraw.text(0.75 * n, -0.025 * n, "No solution possible");
    }
  }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    long startTime = System.nanoTime();
    Solver solver = new Solver(initial);
    long estimatedTime = System.nanoTime() - startTime;

    // turn on animation mode
    StdDraw.enableDoubleBuffering();

    if (!solver.isSolvable()) {
      draw(initial, n, 0, (float)estimatedTime/1000000000);
      StdDraw.show();
    } else {
      for (Board board : solver.solution()) {
        draw(board, n, solver.moves(), (float)estimatedTime/1000000000);
        StdDraw.show();
        StdDraw.pause(DELAY);
      }
    }
  }

}
