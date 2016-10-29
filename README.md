# 8-Puzzle
http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html

Programming Assignment 4 for the "Algorithms, Part I" course on Coursera.


[Problem specification](assignment/ProgrammingAssignment4_Specification.pdf) (Course starting date October 3rd, 2016).  
[Problem checklist](assignment/ProgrammingAssignment4_Checklist.pdf) (Course starting date October 3rd, 2016).

## The problem
The 8-puzzle problem is a puzzle invented and popularized by Noyes Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square blocks labeled 1 through 8 and a blank square. Your goal is to rearrange the blocks so that they are in order, using as few moves as possible. You are permitted to slide blocks horizontally or vertically into the blank square. 

### Visualization
I created an helper class (PuzzleVisualizer) to visualize the step by step solution found by the Solver.  
Here the solution for puzzle36.txt.  
![puzzle36Solution](src/resources/puzzle36.gif?raw=true) 

### Performances  
[Here](PuzzleChecker_out.txt?raw=true) the results of running the PuzzleChecker on all the test files contained in [8puzzle-testing](http://coursera.cs.princeton.edu/algs4/testing/8puzzle-testing.zip).  
For the initially designed algorithm the most challenging puzzle to solve seems to be puzzle49 that runs out of memory despite allowing the JVM to use ~4.5GB of memory. This prevents PuzzleChecker from moving forward and checking on other files.  
Changing the priority function as suggested [here](http://coursera.cs.princeton.edu/algs4/checklists/8puzzle.html) by breaking the ties by using just the Manhattan distance lead to significant improvements preventing the OutOfMemoryError encountered with the simpler version. The results of running PuzzleChecker with the improved Solver are reported [here](PuzzleChecker(Improv)_out.txt?raw=true).  
  
Notable improvements are:  [withImprovements  ->  withoutImprovements]  
puzzle32.txt: 32 | 6.682331 seconds   ->  puzzle32.txt: 32 | 3.2369819 seconds  
puzzle42.txt: 42 | 59.016415 seconds  ->  puzzle42.txt: 42 | 17.168354 seconds  
puzzle47.txt: 47 | 139.01219 seconds  ->  puzzle47.txt: 47 | 96.6707 seconds  
puzzle49.txt: 49 | OutOfMemoryError   ->  puzzle49.txt: 49 | 161.00056 seconds  

The only drop in performance can be seen in puzzle36.txt which in the assignment we are warned being _especially difficult_:  
puzzle36.txt: 36 | 14.724384 seconds  ->  puzzle36.txt: 36 | 19.874403 seconds  

Even with the aforementioned improvement and by allowing the JVM to use ~6GB of memory, the algorithm fails to run puzzle4x4-49.txt by running out of memory thus preventing the other following puzzles from being tested. This seems to be normal as stated in the assignment checklist: "**My program can't solve some of the 4-by-4 puzzles, even if I give it a huge amount of space. What am I doing wrong?** Probably nothing. The A* algorithm (with the Manhattan priority function) will struggle to solve even some 4-by-4 instances".


## Assessment Summary
Compilation:  PASSED  
Style:        PASSED  
Findbugs:     No potential bugs found.  
API:          PASSED  

Correctness:  42/42 tests passed  
Memory:       8/8 tests passed  
Timing:       17/17 tests passed  

Aggregate score: 100.00% [Correctness: 65%, Memory: 10%, Timing: 25%, Style: 0%]

------
(Note that for this project to work a reference to [algs4.jar](http://algs4.cs.princeton.edu/code/algs4.jar) has to be added.) 
