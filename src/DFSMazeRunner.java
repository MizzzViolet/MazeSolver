import java.util.Random;
import java.util.Iterator;
import java.util.Stack;
import java.io.PrintWriter;
 
public class DFSMazeRunner<MC extends MazeCell> extends MazeRunner<MC> {
 
    /**
     *
     *
     *
     * This class holds a data struture that is meant to be inserted as a
     *
     *
     *
     * closure in each cell along the expansion path so that we can retrace the
     *
     *
     *
     * path from the donut to the start. It is essentially used to create a
     *
     *
     *
     * linked list...or kind of a reverse tree (where the children know their
     *
     *
     *
     * parents, but the parents don't know their children...stupid irresponsible
     *
     *
     *
     * parents).
     */
 
    private class SolutionPathInfo {
 
        public MC nextInSolution;
 
    }
 
    /**
     * Tries to find a solution to the given maze. This function is the core of
     * the random maze runner. It chooses a random neighboring node to expand
     * and goes that way. Think of it as a lost child in the woods looking for
     * the way home stumbling around from spot to spot until it runs out of
     * enery...err maybe not.
     *
     * @param maze
     *            The maze to solve.
     * @param writer
     *            The printwriter on which to output the maze solution.
     */
 
    public void solveMaze(Maze<MC> maze, PrintWriter writer) {
 
        Stack<MC> S = new Stack<MC>();
 
        int cellsExpanded;
 
        MC curCell;
 
        cellsExpanded = 0;
 
        curCell = maze.getStart();
 
        S.push(curCell);
 
        while (!S.isEmpty()) {
 
            cellsExpanded++;
 
            MC t = S.pop();
 
            if (t.isDonut()) {
 
                curCell = t;
 
                break;
 
            }
 
            Iterator<MC> it = maze.getNeighbors(t);
 
            while (it.hasNext()) {
 
                MC u = it.next();
 
                if (u.getState() == MazeCell.CellState.notVisited) {
 
                    u.setState(MazeCell.CellState.visitInProgress);
 
                    S.push(u);// push if visited
 
                    SolutionPathInfo info = getSolutionPathInfo(u);
 
                    u.setState(MazeCell.CellState.visited);
 
                    info.nextInSolution = t;
 
                }
 
            }
 
        }
 
        writer.println("DFS");
 
        Stack<MC> P = new Stack<MC>();
 
        while (curCell != maze.getStart()) {
            P.push(curCell);
            curCell.setState(MazeCell.CellState.solutionPath);
            SolutionPathInfo info = getSolutionPathInfo(curCell);
            curCell = info.nextInSolution;
        }
        P.push(curCell);
        int pathLength = 0;
 
        curCell = P.pop();
 
        while (!P.isEmpty()) {
 
            pathLength++;
 
            writer.print(curCell + " ");
 
            curCell = P.pop();
 
        }
 
        writer.println(curCell + " ");
 
        writer.println(pathLength);
 
        writer.println(cellsExpanded);
 
    }
 
    /**
     *
     *
     *
     * A helper function that returns a pointer to the SolutionPathInfo
     *
     *
     *
     * associated with a given cell. This funciton takes care of creating and
     *
     *
     *
     * associating an instance of SolutionPathInfo if one does not already
     *
     *
     *
     * exist.
     *
     *
     *
     *
     *
     *
     *
     * @param curCell
     *
     *
     *
     *            The cell from which we retrieve the SolutionPathInfo closure.
     */
 
    private SolutionPathInfo getSolutionPathInfo(MazeCell curCell) {
 
        if (null == curCell.getExtraInfo()) {
 
            curCell.setExtraInfo(new SolutionPathInfo());
 
        }
 
        return (SolutionPathInfo) curCell.getExtraInfo();
 
    }
 
    /**
     *
     *
     *
     * A random number sequence object that is used generate random numbers for
     *
     *
     *
     * our random maze runner...how random.
     */
 
    private Random randSeq = new Random();
 
    // private Random randSeq = new Random(0); // use this instead for debugging
 
}
