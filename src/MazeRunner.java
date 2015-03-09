import java.io.PrintWriter;

/**
 *  Abstarct base class for MazeRunners.  It has a solveMaze method
 *  that, given a maze, will attempt to find a path from teh start to
 *  the goal in the maze.
 */
public abstract class MazeRunner<MC extends MazeCell> {
	/**
	 * Attempts to find a path from the start to the donut node of
	 * the maze given in <code>maze</code>
	 *
	 * @param maze		The maze to solve.
	 * @param writer	The printwriter on which to output the
	 * 			maze solution.
	 */
	public abstract void solveMaze(Maze<MC> maze, PrintWriter writer);
}
