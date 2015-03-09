import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * SqureCell is the representation of a Cell in a
 * SquareCellMaze.  It is a nested class (as opposed to an inner
 * class like the Iterators...notice the "static" keyword in the
 * class's delcaration.  This is because a SquareCell does not
 * really need to, and should have no knowledge of the maze it
 * is a part of. (Keeping a strict hierarchy of knowledge makes
 * most OO inheritence hierarchy sane.  Otherwise they blow up
 * like a big big balloon).  This class is nested because,
 * basically, no one else should really have interest in
 * creating a SquareCell.  It's really a property of the
 * SquareCellMaze.
 *
 * @author Albert J. Wong (awong@cs)
 */
class SquareCell extends MazeCell {
	/**
	 * Creates a new SquareCell with the given parameters.
	 *
	 * @param cellRow	The row this cell is on
	 * 	(yeah, this shouldn't be part of the SquareCell.
	 * 	But it's soo convenient).
	 * @param cellCol	The column this cell is on
	 * 	(yeah, this shouldn't be part of the SquareCell.
	 * 	But it's soo convenient).
	 *
	 * @param isStart	<code>true</code> if this cell
	 * 	is the start cell.
	 * @param isDonut	<code>true</code> if this cell
	 * 	is the donut cell.
	 *
	 * @param northWall	<code>true</code> if this cell
	 * 	has a wall to the North.
	 * @param eastWall	<code>true</code> if this cell
	 * 	has a wall to the East.
	 * @param southWall	<code>true</code> if this cell
	 * 	has a wall to the South.
	 * @param westWall	<code>true</code> if this cell
	 * 	has a wall to the West.
	 */
	public SquareCell(int cellRow, int cellCol,
		boolean isStart, boolean isDonut,
		boolean northWall, boolean eastWall,
		boolean southWall, boolean westWall)
	{
		walls = new boolean[] {northWall, eastWall,
			southWall, westWall};

		numWalls = 0;
		for(int i=0; i < 4; i++) {
			if(walls[i]) {
				numWalls++;
			}
		}

		isStartCell = isStart;
		isDonutCell = isDonut;

		row = cellRow;
		col = cellCol;
	}


	/**
	 * Returns true if this cell is a start cell.
	 *
	 * @return <code>true</code> if this cell is a start cell.
	 * 	<code>false</code> otherwise.
	 */
	public boolean isStart() {
		return isStartCell;
	}


	/**
	 * Returns true if this cell is a donut cell.
	 *
	 * @return <code>true</code> if this cell is a donut cell.
	 * 	<code>false</code> otherwise.
	 */
	public boolean isDonut() {
		return isDonutCell;
	}


	/**
	 * A defined toString function that will output a
	 * nicely formated string that specifies the location
	 * (row,col) of this cell.
	 *
	 * @return a string of the form "(row,col)".
	 */
	public String toString() {
		return "(" + row + "," + col + ")";
	}


	/**
	 * Returns <code>true</code> if there is a wall in the
	 * direction <code>d</code>.
	 *
	 * @param d	The direction in which to check for a
	 * 	wall.
	 * @return	<code>true</code> if there is a wall in the
	 * direction <code>d</code>.
	 */
	public boolean isWall(Direction d) {
		return walls[d.toInt()];
	}


	/**
	 * Returns the number of walls that this cell has
	 *
	 * @return the number of walls this cell has
	 */
	public int getNumWalls() {
		return numWalls;
	}


	/**
	 * Returns the total possible number of walls this cell
	 * has.  This can be though of as the number of sides
	 * the cell has.
	 *
	 * @return the maximum number of wall this cell can
	 * 	have.
	 */
	public int getMaxNumWalls() {
		return 4;
	}


	/**
	 * Retuns the row this cell is in.
	 *
	 * @return the row this cell is in.
	 */
	public int getRow() {
		return row;
	}


	/**
	 * Retuns the column this cell is in.
	 *
	 * @return the column this cell is in.
	 */
	public int getCol() {
		return col;
	}

	/**
	 *  An array to keep track of which walls exist on this
	 *  cell.
	 */
	private boolean[] walls;

	/**
	 *  The number of walls this cell has.
	 */
	private int numWalls;


	/** Whether or not this cell is a start cell. */
	private boolean isStartCell;


	/** Whether or not this cell is a donut cell. */
	private boolean isDonutCell;


	/** The row this cell is on. */
	private int row;


	/** The column this cell is on. */
	private int col;
}


