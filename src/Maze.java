import java.util.Iterator;
import java.util.ArrayList;

/**
 * This class represents the abstract concept of a maze.  A Maze is a
 * graph of MazeCells where one cell is designated as a "start" and one
 * cell is designated as an "donut."  This class provides functions
 * for finding neighbors of a cell, for iterating over all the cells in
 * the maze, reseting the visittion states of all the cells, and for
 * registering listeners to state chagnes in the maze as well as an
 * interface for associating a closure object with the maze.
 *
 * @author Albert J. Wong (U Washington)
 */
public abstract class Maze<MC extends MazeCell> {
	/**
	 * Default constructor for a maze.  Just initializes the basic
	 * variables to a reasonable state.
	 */
	public Maze() {
		info = null;
		mazeChangeListeners = new ArrayList<MazeChangeListener>();
	}


	/**
	 * Returns a reference to the <code>MazeCell</code> object that
	 * is the start cell of the maze.
	 *
	 * @return The start cell of this maze.
	 */
	public abstract MC getStart();


	/**
	 * Returns a reference to the <code>MazeCell</code> object that
	 * is the donut cell of the maze.
	 *
	 * @return The donut cell of this maze.
	 */
	public abstract MC getDonut();


	/**
	 * Returns an iterator that will iterate over all cells in this
	 * maze.
	 *
	 * @return an iterator that will iterate over all cells in this
	 * maze.
	 */
	public abstract Iterator<MC> getCells();


	/**
	 * Returns an iterator that will over all cells in this maze
	 * that can be reached from the given cell.
	 *
	 * @param cell	the cell for which the iterator will try to
	 * iterator over the neighbors of.
	 * @return an iterator that will over all cells in this maze
	 * that can be reached from the given cell.
	 */
	public abstract Iterator<MC> getNeighbors(MC cell);


	/**
	 * Return the number of cells in this maze
	 *
	 * @return the number of cells in this maze.
	 */
	public abstract int getNumCells();

	/**
	 * Goes through all the cells in the maze and sets their state
	 * to notVisited.
	 */
	public void resetCellState() {
		Iterator<MC> it = getCells();

		while(it.hasNext()) {
			(it.next()).setState(
				MC.CellState.notVisited);
		}
	}



	/**
	 * Gets the object that was previously stored in this maze with
	 * <code>setExtraInfo(Object newInfo)</code>.  This can be used
	 * to implement a form of closures on operations with this maze.
	 *
	 * @return 	The object that was last stored in this
	 * 	node with <code>setExtraInfo(Object newInfo)</code>.  If
	 * 	there was no previous call to <code>setExtraInfo</code>,
	 * 	<code>null</code> is returned.
	 */
	public Object getExtraInfo() {
		return info;
	}



	/**
	 * Stores an object in this maze that can be later retrieved
	 * with a call to <code>getExtraInfo()</code>.  This can be used
	 * to implement a form of closures on operations with this maze.
	 *
	 * @param newInfo	The object to store in this maze. This
	 * 	object may later be retrieved with a call to
	 * 	<code>getExtraInfo()</code>.  <code>newInfo</code> may
	 * 	be <code>null</code>.  This will remove any previously
	 * 	stored object from this maze.
	 */
	public void setExtraInfo(Object newInfo) {
		info = newInfo;
	}


	/**
	 * Adds a new MazeChangeListener to listen to changes in this
	 * maze.
	 *
	 * @param listener	Adds <code>listener</code> to the list
	 * 	of <code>MazeChangeListener</code> that will be notified
	 * 	on a state change to this maze.
	 */
	public void addMazeChangeListener( MazeChangeListener listener ) {
		mazeChangeListeners.add(listener);

		Iterator<MC> it = getCells();

		while(it.hasNext()) {
			(it.next()).addMazeChangeListener(listener);
		}
	}



	/**
	 * Notifies all <code>MazeChangeListener</code> that have been
	 * previously registered via <code>addMazeChangeListener</code>
	 * of a change in this maze.
	 */
	protected void broadcastChange() {
		Iterator<MazeChangeListener> it = mazeChangeListeners.iterator();

		while(it.hasNext()) {
			(it.next()).stateChangeEvent();
		}
	}


	/**
	 *  a generic object reference used by set/getExtraInfo for
	 *  implementing closures.
	 */
	private Object info;


	/**
	 *  A list of the <code>MazeChangeListener</code> that will be
	 *  notified on a visitation state change.
	 */
	private ArrayList<MazeChangeListener> mazeChangeListeners;
}
