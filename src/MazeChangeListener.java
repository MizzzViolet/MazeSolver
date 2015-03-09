/**
 * Interface used to receive maze change events.  Objects that wish to
 * register themselves as listeners for changes in the maze state must
 * implement this interface.
 *
 * @author Albert J. Wong (U Washington)
 */
public interface MazeChangeListener {

	/**
	 * This function is called when a single cell has had its state
	 * changed.  The cell in which the change occurred is given in
	 * as the parameter.
	 *
	 * @param cell	the cell whose state change is being broadcast
	 */
	public void cellStateChangeEvent(MazeCell cell);

	/**
	 * This function is called when the general state of the maze
	 * has been changed.
	 */
	public void stateChangeEvent();
}
