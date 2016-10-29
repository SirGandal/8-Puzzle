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
To improve the performances of the starting algorithm a few enhancements have been implemented as suggested [here](http://coursera.cs.princeton.edu/algs4/checklists/8puzzle.html). We run PuzzleChecker on all the test files contained in [8puzzle-testing](http://coursera.cs.princeton.edu/algs4/testing/8puzzle-testing.zip).  

* **Use a 1d array instead of a 2d array**  
PuzzleChecker results with this improvement [here](PuzzleChecker_1.txt?raw=true). The most challenging puzzle to solve seems to be puzzle49 where the Solver runs out of memory despite allowing the JVM to use ~4.5GB of memory. This prevents PuzzleChecker from moving forward and checking on other files.  

* **When two search nodes have the same Manhattan priority, you can break ties however you want, e.g., by comparing either the Hamming or Manhattan distances of the two boards**  
PuzzleChecker results with this improvement [here](PuzzleChecker_2.txt?raw=true). This improvement builds on top of the one of the previous point. Changing the priority function by breaking ties using just the Manhattan distance lead to significant improvements preventing the OutOfMemoryError encountered prevously with puzzle49. Even with this improvement and by allowing the JVM to use ~6GB of memory, the Solver fails fails to run on puzzle4x4-49.txt because it runs out of memory thus preventing the other following puzzles from being tested. This seems to be normal as stated in the assignment checklist: "**My program can't solve some of the 4-by-4 puzzles, even if I give it a huge amount of space. What am I doing wrong?** Probably nothing. The A* algorithm (with the Manhattan priority function) will struggle to solve even some 4-by-4 instances".  

At this point we notice the following improvements:  
puzzle32.txt: 32 | 6.682331 seconds   ->  puzzle32.txt: 32 | 3.2369819 seconds  
puzzle42.txt: 42 | 59.016415 seconds  ->  puzzle42.txt: 42 | 17.168354 seconds  
puzzle47.txt: 47 | 139.01219 seconds  ->  puzzle47.txt: 47 | 96.6707 seconds  
puzzle49.txt: OutOfMemoryError        ->  puzzle49.txt: 49 | 161.00056 seconds  

The only drop in performance can be seen in puzzle36.txt which in the assignment we are warned being _especially difficult_:  
puzzle36.txt: 36 | 14.724384 seconds  ->  puzzle36.txt: 36 | 19.874403 seconds  

* **To save memory, consider using an n-by-n char[][] array or a length n^2 char[] array**  
PuzzleChecker results with this improvement [here](PuzzleChecker_3.txt?raw=true). This improvement builds on top of the one of the previous point. Given that the Solver stills run out of memory we try to improve by saving space when storing the board switching from a int[] to a char[]. This improvement. successfully manages to solve puzzle4x4-49.txt while failing on the following one puzzle4x4-50.txt. Even here the JVM has been allowed to use up to ~6GB.  

Despite with this last improvement we manage to solve most of the puzzles we couldn't solve at the beginning by improving memory usage we have affected the running time. It is evident when we look at the latest puzzles solved:  
puzzle4x4-45.txt: 45 | 97.442955 seconds  ->  puzzle4x4-45.txt: 45 | 140.26974 seconds  
puzzle4x4-46.txt: 46 | 77.95158 seconds   ->  puzzle4x4-46.txt: 46 | 112.644905 seconds  
puzzle4x4-47.txt: 47 | 90.60449 seconds   ->  puzzle4x4-47.txt: 47 | 125.802 seconds  
puzzle4x4-48.txt: 48 | 72.02925 seconds   ->  puzzle4x4-48.txt: 48 | 101.53501 seconds  


## Assessment Summary
Compilation:  PASSED  
Style:        PASSED  
Findbugs:     No potential bugs found.  
API:          PASSED  

Correctness:  42/42 tests passed  
Memory:       8/8 tests passed  
Timing:       17/17 tests passed  

Aggregate score: 100.00% [Correctness: 65%, Memory: 10%, Timing: 25%, Style: 0%]

Note: The submitted version includes just the first improvement.
------
(Note that for this project to work a reference to [algs4.jar](http://algs4.cs.princeton.edu/code/algs4.jar) has to be added.) 
