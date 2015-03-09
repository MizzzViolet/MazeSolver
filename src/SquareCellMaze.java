import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * SquareCellMaze is a Maze of square cells (cells with 4 sides).
 *
 * @author Albert J. Wong (U Washington)
 */
public class SquareCellMaze extends Maze<SquareCell> {
	/**
	 * Construct a trivial maze.  This constructor is private
	 * because it is essentially meaningless to create a maze that
	 * isn't based on a file.  Thus only the SquareCellMazeFactory will
	 * be allowed to create a maze.  This makes it so that only
	 * inner classes or member methods can call the constructor so
	 * we don't have to worry about people creating bad mazes.
	 *
	 */
	private SquareCellMaze()
	{
		startCell = null;
		donutCell = null;
		cells = null;
		width = 0;
		height = 0;
	}


	/**
	 * Returns a reference to the <code>SquareMazeCelll</code> object that
	 * is the start cell of the maze.
	 *
	 * @return The start cell of this maze.
	 */
	public SquareCell getStart() {
		return startCell;
	}


	/**
	 * Returns a reference to the <code>SquareCell</code> object that
	 * is the donut cell of the maze.
	 *
	 * @return The donut cell of this maze.
	 */
	public SquareCell getDonut() {
		return donutCell;
	}


	/**
	 * Returns an iterator that will iterate over all cells in this
	 * maze.
	 *
	 * @return an iterator that will iterate over all cells in this
	 * maze.
	 */
	public Iterator<SquareCell> getCells() {
		return new SquareCellMazeIterator();
	}



	/**
	 * Returns an iterator that will over all cells in this maze
	 * that can be reached from the given cell.
	 *
	 * @param cell	the cell for which the iterator will try to
	 * iterator over the neighbors of.
	 * @return an iterator that will over all cells in this maze
	 * that can be reached from the given cell.
	 */
	public Iterator<SquareCell> getNeighbors(SquareCell cell) {
		return new NeighborIterator(cell);
	}


	/**
	 * Return the number of cells in this maze
	 *
	 * @return the number of cells in this maze.
	 */
	public int getNumCells() {
		return width * height;
	}


	/**
	 * An accessor function that returns the cell located at
	 * (row,col).  All SquareCellMazes are rectangular matricies so
	 * this works.
	 *
	 * @param row	The row of the cell to retrieve.
	 * @param col	The column of the cell to retrieve.
	 * @return 	The cell located at (row,col).
	 */
	public SquareCell getCell(int row, int col) {
		return cells[row][col];
	}


	/**
	 * An mutator function that places a cell at a the location
	 * (row,col).  All SquareCellMazes are rectangular matricies so
	 * this works.
	 *
	 * @param row	The row to place the cell in.
	 * @param col	The column to place the cell in.
	 * @param cell	The cell to place at (row,col).
	 */
	private void setCell(int row, int col, SquareCell cell) {
		cells[row][col] = cell;
	}


	/**
	 * Accessor to the get the width, in number of cells, of the
	 * SquareCellMaze.
	 *
	 * @return	The width of the maze in number of cells.
	 */
	public int getWidth() {
		return width;
	}


	/**
	 * Accessor to the get the height, in number of cells, of the
	 * SquareCellMaze.
	 *
	 * @return	The height of the maze in number of cells.
	 */
	public int getHeight() {
		return height;
	}

	/** A reference to the start cell of this maze. */
	private SquareCell startCell;

	/** A reference to the donut cell of this maze. */
	private SquareCell donutCell;

	/**
	 *  A 2 dimentional array that holds/represents all the cells in
	 *  this maze.
	 */
	private SquareCell[][] cells;

	/** The width of the maze in number of cells. */
	private int width;

	/** The height of the maze in number of cells. */
	private int height;


	/**
	 * An iterator class that iterates over all the cells in a
	 * SquareCellMaze.  This class is implemented using the inner
	 * class language feature in Java which allows an inner (as
	 * opposed to nested) class to have an implicit reference to the
	 * containing class.  Thus, any SquareCellMazeIterator instance
	 * implicitly knows which maze it is associated with. Quite
	 * useful for iterators.
	 *
	 * @author Albert J. Wong (awong@cs)
	 */
	private class SquareCellMazeIterator implements Iterator<SquareCell> {
		/**
		 * Constrcuts a new SquareCellMazeIterator.
		 */
		public SquareCellMazeIterator() {
			curRow = 0;
			curCol = 0;
		}


		/**
		 * Returns true if there are still cells to be iterated
		 * over, false otherwise.
		 *
		 * @return true if there are still cells to be iterated
		 * over, false otherwise.
		 */
		public boolean hasNext() {
			return (curRow+1 != height || curCol != width);
		}


		/**
		 * If hasNext() is true, this returns the next cell in
		 * the iteration sequence.  Othewrise a
		 * NoSuchElementException is thrown.
		 *
		 * @return the next cell in the iteration sequence
		 * @exception NoSuchElementException  Thrown if next()
		 * 	is called when there are no more elements to be
		 * 	iterated over.
		 */
		public SquareCell next() {
			if( !hasNext() ) {
				throw new NoSuchElementException();
			}

			if(curCol == width) {
				curCol = 0;
				curRow++;
			}

			return cells[curRow][curCol++];
		}


		/**
		 * The remove function is not implemented in this
		 * iterator and it doesn't make sense to remove a cell
		 * from a SquareCellMaze.
		 *
		 * @exception UnsupportedOperationException Thrown when
		 * 	remove() is called.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

		/** Keeps Track of the current row we are iterating over. */
		private int curRow;

		/** Keeps Track of the current column we are iterating over. */
		private int curCol;
	}


	/**
	 * An iterator class that iterates over all neighbors that can
	 * be reached from the cell the iterator was created for.  This
	 * class is implemented using the inner class language feature
	 * in Java which allows an inner (as opposed to nested) class to
	 * have an implicit reference to the containing class.  Thus,
	 * any SquareCellMazeIterator instance implicitly knows which
	 * maze it is associated with. Quite useful for iterators.
	 *
	 * @author Albert J. Wong (awong@cs)
	 */
	private class NeighborIterator implements Iterator<SquareCell> {
		/**
		 *  Construct a new NeighborIterator that will
		 *  iterator over teh neighbors of <code>cell</code>
		 *
		 *  @param cell	The cell whose neighbors this iterator
		 *  	will iterate over.
		 */
		public NeighborIterator( SquareCell cell ) {
			refCell = cell;
			curWall = Direction.north;
		}


		/**
		 * Returns true if there are still cells to be iterated
		 * over, false otherwise.
		 *
		 * @return true if there are still cells to be iterated
		 * over, false otherwise.
		 */
		public boolean hasNext() {
			return numWallsFound < ( refCell.getMaxNumWalls() -
				refCell.getNumWalls() );
		}



		/**
		 * If hasNext() is true, this returns the next cell in
		 * the iteration sequence.  Othewrise a
		 * NoSuchElementException is thrown.
		 *
		 * @return the next cell in the iteration sequence
		 * @exception NoSuchElementException  Thrown if next()
		 * 	is called when there are no more elements to be
		 * 	iterated over.
		 */
		public SquareCell next() {
			int nextRow = refCell.getRow();
			int nextCol = refCell.getCol();

			while(hasNext()) {
				if( !refCell.isWall(curWall) ) {
					if( curWall == Direction.north ) {
						nextRow--;
					} else if ( curWall == Direction.east ) {
						nextCol++;
					} else if ( curWall == Direction.south ) {
						nextRow++;
					} else if ( curWall == Direction.west ) {
						nextCol--;
					}

					numWallsFound++;
					curWall = curWall.clockwise90();
					return getCell(nextRow, nextCol);
				}

				curWall = curWall.clockwise90();
			}

			return null;
		}


		/**
		 * The remove function is not implemented in this
		 * iterator and it doesn't make sense to remove a cell
		 * from a SquareCellMaze.
		 *
		 * @exception UnsupportedOperationException Thrown when
		 * 	remove() is called.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

		/**
		 *  The reference to the cell who we are finding the
		 *  neighbors of.
		 */
		private SquareCell refCell;


		/** The current wall we are examining. */
		private Direction curWall;


		/**
		 *  The number of walls we have found for this cell so
		 *  far.
		 */
		int numWallsFound = 0;
	}



	/**
	 * SquareCellMazeFactory is a Factory Design Pattern that
	 * generates a new SquareCellMaze.  It should throw a malformed
	 * input exception, but I didn't have time to go through and fix
	 * that. It currently just exits if it hits a parse error.
	 *
	 * @author Albert J. Wong
	 */
	public static class SquareCellMazeFactory {
		/**
		 * parseMaze takes a file in and tries to generate a
		 * SquareCellMaze from that file.
		 *
		 * @param filename	Name of file with data to
		 * 	generate a SquareCellMaze.
		 * @return Returns a new SquareCellMaze based on the
		 * 	given filename.
		 *
		 * @exception FileNotFoundException Thrown if the file
		 * 	cannot be found.
		 * @exception IOException Thrown on a generic IO
		 * 	Exception
		 */
		static  SquareCellMaze parseMaze(String filename)
			throws FileNotFoundException, IOException
		{
			SquareCellMaze maze = new SquareCellMaze();

			BufferedReader br = new BufferedReader(
				new FileReader(filename) );

			String topLine;
			String midLine;
			String bottomLine;

			try {
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);
				maze.width = Integer.parseInt(st.nextToken());
				maze.height = Integer.parseInt(st.nextToken());
				maze.cells = new SquareCell[maze.height][maze.width];

			} catch (NoSuchElementException nsee) {
				System.err.println("Error in Maze: line 1");
				System.err.println(nsee);
				System.exit(1);

			} catch (NumberFormatException nfe) {
				System.err.println("Error in Maze: line 1");
				System.err.println(nfe);
				System.exit(1);
			}



			topLine = br.readLine();
			for(int y=0; y < maze.height; y++) {
				midLine = br.readLine();
				bottomLine = br.readLine();

				if( topLine.length() != maze.width*2+1
				    || midLine.length() != maze.width*2+1
				    || bottomLine.length() != maze.width*2+1 )
				{
					System.err.println("Error in Maze"
						+ "near line " + (1+(1+y)*2));
					System.exit(1);
				}

				if( topLine.charAt(0) != '+'
				    || midLine.charAt(0) != '|'
				    || bottomLine.charAt(0) != '+' )
				{
					System.err.println("Error in Maze (Corrupt Border) near line "
						+ (1+(1+y)*2) + " col 1");
					System.exit(1);
				}

				if( topLine.charAt(maze.width*2) != '+'
				    || midLine.charAt(maze.width*2) != '|'
				    || bottomLine.charAt(maze.width*2) != '+' )
				{
					System.err.println("Error in Maze (Corrupt Border) "
						+"near line " + (1+(1+y)*2) + " col "
						+ topLine.length() );
					System.exit(1);
				}

				for(int x = 0; x < maze.width; x++) {
					boolean isStart = false;
					boolean isDonut = false;
					boolean northWall = false;
					boolean eastWall = false;
					boolean southWall = false;
					boolean westWall = false;

					int mid = x*2+1;
					int right = mid + 1;
					int left = mid - 1;

					if( y == 0 ) {
						if( topLine.charAt(left) != '+'
						   || topLine.charAt(mid) != '-'
						   || topLine.charAt(right) != '+' )
						{
							System.err.println("Error in Maze "
								+ "(Corrupt Border) near line "
								+ (1+(1+y)*2) + " col " + (1+mid) );
							System.exit(1);
						}
					}

					//bug fix, 2/07/06 changed width to height
					if( y == maze.height-1 ) {
						if( bottomLine.charAt(left) != '+'
						   || bottomLine.charAt(mid) != '-'
						   || bottomLine.charAt(right) != '+' )
						{
							System.err.println("Error in Maze "
								+"(Corrupt Border) near line "
								+ (1+(1+y)*2) + " col " + (1+mid) );
							System.exit(1);
						}
					}

					if(topLine.charAt(mid) == '-') {
						northWall = true;
					}

					if(bottomLine.charAt(mid) == '-') {
						southWall = true;
					}

					if(midLine.charAt(left) == '|') {
						westWall = true;
					}

					if(midLine.charAt(right) == '|') {
						eastWall = true;
					}

					switch(midLine.charAt(mid)) {
						case '*':
							isStart = true;
							break;

						case 'O':
							isDonut = true;
							break;

						case ' ':
							// Do nothing special.
							break;

						default:
							System.err.println("Other error in Maze near line "
								+ (1+(1+y)*2) + " col " + (1+mid) );
							System.exit(1);
					}

					// Create the cell here.
					maze.setCell(y, x, new SquareCell(y, x,
						isStart, isDonut,
						northWall, eastWall,
						southWall, westWall));

					if(isStart) {
						if(maze.startCell != null) {
							System.err.println( "Multiple start cells "
								+ "near line " + (1+(1+y)*2) + " col "
								+ topLine.length() );
							System.exit(1);
						}

						maze.startCell = maze.getCell(y,x);
					}

					if(isDonut) {
						if(maze.donutCell != null) {
							System.err.println( "Multiple exit cells "
								+ "near line " + (1+(1+y)*2) + " col "
								+ topLine.length() );
							System.exit(1);
						}

						maze.donutCell = maze.getCell(y,x);
					}
				}

				topLine = bottomLine;
			}

			if(maze.startCell == null ) {
				System.err.println("No start cell");
				System.exit(1);
			}

			if(maze.donutCell == null ) {
				System.err.println("No donut cell");
				System.exit(1);
			}

			return maze;
		}
	}
}
