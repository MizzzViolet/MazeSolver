import java.io.PrintWriter;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * MazeCell is an abstract class that represents a single cell in a
 * <code>Maze</code>.  This class has constains the set of operations
 * necessary for a <code>MazeCell</code> to function in a
 * <code>Maze</code> and a <code>MazeRunner</code>.  It includes
 * functions to tell how many walls can a particular cell have, how many
 * walls does the cell actually have up, and what is the current
 * visitation state of the cell.   It also defines interfaces for
 * attaching listeners to state changes on this cell which makes it easy
 * to attach visualizers or tracers to Mazes componsed of
 * <code>MazeCell</code>.  There is also support for the storing of
 * "closures" in get/setExtraInfo.
 *
 * @author Albert J. Wong (U Washington)
 */
public abstract class MazeCell {

	/**
	 * Creates an empty MazeCell initialized to the notVisited state
	 */
	public MazeCell() {
		info = null;
		state = CellState.notVisited;
		mazeChangeListeners = new ArrayList<MazeChangeListener>();
	}


	/**
	 * Returns the number of walls that this cell has
	 *
	 * @return the number of walls this cell has
	 */
	public abstract int getNumWalls();


	/**
	 * Returns the total possible number of walls this cell has.
	 * This can be though of as the number of sides the cell has.
	 *
	 * @return the maximum number of wall this cell can have.
	 */
	public abstract int getMaxNumWalls();


	/**
	 * Returns true if this cell is a start cell.
	 *
	 * @return <code>true</code> if this cell is a start cell.
	 * 	<code>false</code> otherwise.
	 */
	public abstract boolean isStart();


	/**
	 * Returns true if this cell is a donut cell.
	 *
	 * @return <code>true</code> if this cell is a donut cell.
	 * 	<code>false</code> otherwise.
	 */
	public abstract boolean isDonut();


	/**
	 * Returns the current visitation state of the Cell. A cell can
	 * be marked as any value in <code>CellState</code>.
	 *
	 * @return  Returns the current visitation state of the cell.
	 */
	public CellState getState() {
		return state;
	}


	/**
	 * Sets the current visitation state of the Cell. A cell can be
	 * marked as any value in <code>CellState</code>.
	 *
	 * @param s	Sets the visitation state to the state in
	 * 	<code>s</code>
	 */
	public void setState(CellState s) {
		state = s;
		broadcastChange();
	}


	/**
	 * Gets the object that was previously stored in this cell with
	 * <code>setExtraInfo(Object newInfo)</code>.  This can be used
	 * to implement a form of closures on operations with this cell.
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
	 * Stores an object in this cell that can be later retrieved
	 * with a call to <code>getExtraInfo()</code>.  This can be used
	 * to implement a form of closures on operations with this cell.
	 *
	 * @param newInfo	The object to store in this cell. This
	 * 	object may later be retrieved with a call to
	 * 	<code>getExtraInfo()</code>.  <code>newInfo</code> may
	 * 	be <code>null</code>.  This will remove any previously
	 * 	stored object from this cell.
	 */
	public void setExtraInfo(Object newInfo) {
		info = newInfo;
	}


	/**
	 * Adds a new MazeChangeListener to listen to changes in this
	 * cell.
	 *
	 * @param listener	Adds <code>listener</code> to the list
	 * 	of <code>MazeChangeListener</code> that will be notified
	 * 	when the visitation state of this cell is changed via
	 * 	<code>setState(CellState s)</code>
	 */
	public void addMazeChangeListener( MazeChangeListener listener ) {
		mazeChangeListeners.add(listener);
	}


	/**
	 * Notifies all <code>MazeChangeListener</code> that have been
	 * previously registered via <code>addMazeChangeListener</code>
	 * of a change in this cell's visitation state.
	 */
	protected void broadcastChange() {
		Iterator<MazeChangeListener> it = mazeChangeListeners.iterator();

		while(it.hasNext()) {
			(it.next()).cellStateChangeEvent(this);
		}
	}

	/**
	 *  a generic object reference used by set/getExtraInfo for
	 *  implementing closures.
	 */
	private Object info;

	/**
	 *  The current visitation state of this cell.
	 */
	private CellState state;

	/**
	 *  A list of the <code>MazeChangeListener</code> that will be
	 *  notified on a visitation state change.
	 */
	private ArrayList<MazeChangeListener> mazeChangeListeners;


	/**
	 * CellState is a class that represents the visitation state of
	 * a given cell.  It is implemented to act like a primitive
	 * enum, but differs from a real enum in that it is essentially
	 * using a modified singleton pattern to ensure that only a
	 * couple of instances of this object ever exist.  This means
	 * that serialized versions of this object will not transfer to
	 * another JVM without a bit of work.  However, for the purposes
	 * of MazeRunner and most java code, this implementation is
	 * fine.  This is put in the MazeCell as a nested (static) class
	 * because the CellState is arguably a property of MazeCells
	 * and only MazeCells.
	 *
	 * @author Albert J. Wong (awong@cs)
	 */
	public static final class CellState {
		/** The state when a cell has never been visited */
		public static final CellState notVisited;

		/** The state when a cell has been traversed fully. */
		public static final CellState visited;

		/**
		 *  The state when a cell has been visited, but
		 *  hasn't been fully processed (perhaps its calculating
		 *  a heruristic, or its neighbors haven't been fully
		 *  traversed yet when executing an algorithm like dfs)
		 */
		public static final CellState visitInProgress;

		/**
		 *  The state when a cell is on the solution path (the
		 *  path from the start to the donut)
		 */
		public static final CellState solutionPath;

		/* Create our couple of objects */
		static {
			notVisited = new CellState(0);
			visited = new CellState(1);
			visitInProgress = new CellState(2);
			solutionPath = new CellState(3);
		}

		/**
		 *  Private constructor that creates our CellState
		 *  instances.  The constructor is private since we want
		 *  to be able to control exactly how many objects of
		 *  this type are in existence.  The constructor takes
		 *  an argument <code>i</code> which is the internal
		 *  number we use to differentiate between teh different
		 *  instances. I suppose we could actually use the
		 *  constant references themselves to implement
		 *  identification, but doing it this way allows us to
		 *  use a switch statement which just looks cool :P.
		 *
		 *  @param i	the value that will be used internally
		 *  to identify this cell state.
		 */
		private CellState(int i) {
			state = i;
		}

		/**
		 * The internal representation of which state we are in.
		 */
		private final int state;
	}
}
