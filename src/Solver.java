import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

  private class SearchNode {

    private final Board board;
    private final int moves;
    private final SearchNode previous;

    private SearchNode(Board board, int moves, SearchNode previous) {
      this.board = board;
      this.moves = moves;
      this.previous = previous;
    }
  }

  private class SearchNodesOrder implements Comparator<SearchNode> {
    @Override
    public int compare(SearchNode s1, SearchNode s2) {

      int s1Manhattan = s1.board.manhattan();
      int s2Manhattan = s2.board.manhattan();
      int s1Priority = s1.moves + s1Manhattan; 
      int s2Priority = s2.moves + s2Manhattan;
      
      if (s1Priority < s2Priority) {
        return -1;
      }

      if (s1Priority > s2Priority) {
        return 1;
      }
      
      if (s1Manhattan < s2Manhattan) {
        return -1;
      }

      if (s1Manhattan > s2Manhattan) {
        return 1;
      }
      
      return 0;
    }
  }

  private boolean solvable = true;
  private Stack<Board> solution = new Stack<>();

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new java.lang.NullPointerException();
    }

    MinPQ<SearchNode> mainSearch = new MinPQ<>(new SearchNodesOrder());
    // mainSearch.insert(new SearchNode(initial, 0, null));

    MinPQ<SearchNode> parallelSearch = new MinPQ<>(new SearchNodesOrder());
    parallelSearch.insert(new SearchNode(initial.twin(), 0, null));

    boolean mainSearchIsRunning = true;
    SearchNode currentSearchNode = new SearchNode(initial, 0, null);
    
    while (true) {

      if (currentSearchNode.board.isGoal()) {
        // found it
        if (!mainSearchIsRunning) {
          // parallel search on twin has found the goal board
          this.solvable = false;
          return;
        }

        while (currentSearchNode.previous != null) {
          solution.push(currentSearchNode.board);
          currentSearchNode = currentSearchNode.previous;
        }
        solution.push(initial);
        return;
      }

      for (Board neighbor : currentSearchNode.board.neighbors()) {

        if (currentSearchNode.previous == null || 
            !currentSearchNode.previous.board.equals(neighbor)) {

          SearchNode nodeToInsert = 
              new SearchNode(neighbor, currentSearchNode.moves + 1, 
                  currentSearchNode);

          (mainSearchIsRunning ? mainSearch : parallelSearch).insert(nodeToInsert);
        }
      }
      
      mainSearchIsRunning = !mainSearchIsRunning;
      
      if (mainSearchIsRunning) {
        if (!mainSearch.isEmpty()) {
          currentSearchNode = mainSearch.delMin();
        } else {
          mainSearchIsRunning = false;
          currentSearchNode = parallelSearch.delMin();
        }
      } else {
        if (!parallelSearch.isEmpty()) {
          currentSearchNode = parallelSearch.delMin();
        } else {
          mainSearchIsRunning = true;
          currentSearchNode = mainSearch.delMin();
        }
      }          
    }
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return solvable;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return solvable ? solution.size() - 1 : -1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return solvable ? solution : null;
  }
}