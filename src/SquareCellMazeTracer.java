 import java.io.PrintWriter;

/**
 * SquareCellMazeTracer implements the MazeChangeListener interface and
 * outputs a text base trace of the progress of a mazerunner.
 *
 * @author Albert J. Wong (U Washington)
 */
public class SquareCellMazeTracer implements MazeChangeListener {
	/**
	 * Ensure the writer is properly closed when this object is
	 * shutdown.
	 */
	protected void finalize() {
		writer.close();
	}


	/**
	 * Creates a new <code>SquareCellMazeTracer</code> that outputs
	 * its trace onto the <code>PrintWriter w</code>.  Pass in a new
	 * print writer instance since this class will close the print
	 * writer when it finishes.
	 *
	 * @param w	A new print writer that this object can use to
	 * 	output its trace onto.
	 */
	public SquareCellMazeTracer (PrintWriter w) {
		writer = w;
	}


	/**
	 * This function prints out the state of the cell that has
	 * changed.
	 *
	 * @param cell	the cell whose state change is being broadcast
	 */
	public void cellStateChangeEvent(MazeCell cell) {
		
		writer.print("** Cell " + cell + " Accessed.  ");
	
		if(cell.getState() == MazeCell.CellState.visited) {
			writer.println("Marked Visited");
		} else if(cell.getState() == MazeCell.CellState.visitInProgress) {
			writer.println("Marked Visit In Progress");
		} else if(cell.getState() == MazeCell.CellState.notVisited) {
			writer.println("Marked Not Visited");
		} else if(cell.getState() == MazeCell.CellState.solutionPath) {
			writer.println("Mark Solution Path");
		}
	}


	/**
	 * Print outs an error if this is called.  This tracer only
	 * handles cell visitation state changes.
	 */
	public void stateChangeEvent() {
		writer.println("** Unknown Maze State Change Event");
	}

	/**
	 *  Reference to the print writer the tracer will output its
	 *  trace onto.
	 */
	private PrintWriter writer;
}
