import java.io.File;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {

  public static void main(String[] args) {

    File folder = new File(args[0]);
    File[] listOfFiles = folder.listFiles();

    if (listOfFiles != null) {
      for (int f = 0; f < listOfFiles.length; f++) {
        if (listOfFiles[f].isFile() && listOfFiles[f].getName().endsWith(".txt")) {
          String filename = listOfFiles[f].getName();
          checkFile(args[0] + filename);
        }
      }
    } else {
      checkFile(args[0]);
    }
  }

  private static void checkFile(String filename) {

    // read in the board specified in the filename
    In in = new In(filename);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        tiles[i][j] = in.readInt();
      }
    }

    // solve the slider puzzle
    Board initial = new Board(tiles);

    long startTime = System.nanoTime();
    Solver solver = new Solver(initial);
    long estimatedTime = System.nanoTime() - startTime;

    StdOut.println(filename + ": " + solver.moves() + " | " + (float) estimatedTime / 1000000000 + " seconds");
  }
}