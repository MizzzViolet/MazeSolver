import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.PrintWriter;

/**
 * This implements a Random Maze Runner for any kind of mazes.  This class will run the maze
 * faster if you equip it with pink bunny slippers.    Speed +2
 *
 * @author Albert J. Wong (U Washington)
 * @author Hannah C. Tang (U Washington)
 */
public class RandomMazeRunner<MC extends MazeCell> extends MazeRunner<MC> {

	/**
	 * This class holds a data struture that is meant to be inserted
	 * as a closure in each cell along the expansion path so that we
	 * can retrace the path from the donut to the start.  It is
	 * essentially used to create a linked list...or kind of a
	 * reverse tree (where the children know their parents, but the
	 * parents don't know their children...stupid irresponsible
	 * parents).
	 */
	private class SolutionPathInfo {
		/**
		 * Link to the next cell in the solution path.
		 */
		public MC nextInSolution;
	}


	/**
	 * Tries to find a solution to the given maze.   This function
	 * is the core of the random maze runner.  It chooses a random
	 * neighboring node to expand and goes that way.  Think of it as
	 * a lost child in the woods looking for the way home stumbling
	 * around from spot to spot until it runs out of enery...err
	 * maybe not.
	 *
	 * @param maze		The maze to solve.
	 * @param writer	The printwriter on which to output the
	 * 			maze solution.
	 */
	public void solveMaze(Maze<MC> maze, PrintWriter writer) {
		int cellsExpanded;
		MC curCell;

		// This is the actual solving engine.  Beginning from the
		// start cell, a random path to a neighboring cell is chosen.
		// If the neighboring cell is the donut, we solved the maze.
		// If not, repeat.
		cellsExpanded = 0;
		curCell = maze.getStart();
		while(!curCell.isDonut()) {
			cellsExpanded++;

			// Mark the cell as visitInProgress so the listeners
			// (like the visualizer) can know what's happening.
			curCell.setState(MazeCell.CellState.visitInProgress);

			// Get a random neighbor and place it into the solution
			// path list.
			SolutionPathInfo info = getSolutionPathInfo(curCell);
			MC nextCell = getRandomNeighbor(maze, curCell);

			// Mark the cell VISITED now and move on.
			curCell.setState(MazeCell.CellState.visited);
			info.nextInSolution = nextCell;
			curCell = nextCell;
		}

		// We've solved the maze, so now we output the solution.
		writer.println("Random");

		// Loop through the solution list starting from the first cell
		// and print the path to "writer"
		curCell = maze.getStart();
		int pathLength = 0;
		while(!curCell.isDonut()) {
			pathLength++;

			writer.print(curCell + " ");

			// Mark the path as "ON_SOLUTION_PATH" so the listeners
			// can know that this is part of the solution.
			curCell.setState(MazeCell.CellState.solutionPath);

			SolutionPathInfo info = getSolutionPathInfo(curCell);
			curCell = info.nextInSolution;
		}

		// The loop above misses the last cell so just print it.
		writer.println(curCell + " ");
		writer.println(pathLength);
		writer.println(cellsExpanded);
	}

	/**
	 * A helper function that chooses a random path out of the current
	 * cell.  It picks a random neighboring node and moves that way.
	 *
	 * @param maze 	The maze in which we are trying to find the
	 * 	donut.
	 * @param curCell	The current cell we are on.  We choose a
	 * 	random neighbor of this cell in the previous maze.
	 */
	private MC getRandomNeighbor(Maze<MC> maze, MC curCell) {
		Iterator<MC> it = maze.getNeighbors(curCell);
		int pickNum = randSeq.nextInt(curCell.getMaxNumWalls() -
			curCell.getNumWalls());
		MC nextCell = null;

		// This is guaranteed to never iterate off the end of the
		// neighbors list so we don't need to check it.hasNext().
		for(int i=0; i <= pickNum; i++) {
			nextCell = it.next();
		}

		return nextCell;
	}

	/**
	 * A helper function that returns a pointer to the SolutionPathInfo
	 * associated with a given cell.  This funciton takes care of
	 * creating and associating an instance of SolutionPathInfo if
	 * one does not already exist.
	 *
	 * @param curCell	The cell from which we retrieve the
	 * 	SolutionPathInfo closure.
	 */
	private SolutionPathInfo getSolutionPathInfo(MazeCell curCell)
	{
		if(null == curCell.getExtraInfo()) {
			curCell.setExtraInfo( new SolutionPathInfo() );
		}

		return (SolutionPathInfo)curCell.getExtraInfo();
	}

	/**
	 * A random number sequence object that is used generate random
	 *  numbers for our random maze runner...how random.
	 */
	private Random randSeq = new Random();
	//private Random randSeq = new Random(0); // use this instead for debugging
}
