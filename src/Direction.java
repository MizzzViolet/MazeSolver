/**  
 *  Represents a cardinal direction (North, South, East, West) and
 *  provides some functions to iterate over the directions.  This
 *  class is a bit of a hack to attempt to provide some sort of 
 *  enumerated type functionality since Java still lacks enums from
 *  the language.  The drawback on this kind of "enum" implementation
 *  is that these objects will not serialize and restore to a different
 *  JVM properly since we're depending on the actual reference value
 *  being the same for the comparison to Direction.north.  (We are using
 *  the == operator for equality comparisons here)  This dependency
 *  breaks when we have move between JVMs.   However, for the purposes
 *  of MazeRunner and many other java programs, this is not an issue.
 *
 *  @author Albert J. Wong (U Washington)
 */ 
public final class Direction {

	/** constant representing North. */
	public static final Direction north;

	/** constant representing East */
	public static final Direction east;
	
	/** constant representing South */
	public static final Direction south;

	/** constant representing West */
	public static final Direction west;

	/* Initialize the public constants here. */
	static {
		north = new Direction(0);
		east = new Direction(1);
		south = new Direction(2);
		west = new Direction(3);
	}



	/** 
	 *  Private constructor that creates our Direction instances.
	 *  The constructor is private since we to control the number of
	 *  instance that are made of this class.  In fact, we only ever
	 *  want 4 instances of this class to be in existence since
	 *  there are only 4 cardinal directions.  The constructor takes
	 *  an argument <code>i</code> which is the internal number we
	 *  use to differentiate between teh different instances. I
	 *  suppose we could actually use the constant references
	 *  themselves to implement identification, but doing it this
	 *  way allows us to use a switch statement which just looks
	 *  cool :P.
	 *
	 *  @param i	The number that should be used for the internal
	 *  representation of the value this object is suppose to be.
	 */
	private Direction(int i) 
	{
		dir = i;
	}




	/**
	 * Returns the direction that is 90 degrees clockwise of the
	 * current direction.
	 *
	 * @return the direction 90 degrees clockwise from the current
	 * direction
	 */
	public Direction clockwise90() {
		switch(dir) {
			case 0:
				return Direction.east;

			case 1:
				return Direction.south;

			case 2:
				return Direction.west;

			case 3:
				return Direction.north;
		}

		// This is dead code, but the Java compiler tries to be
		//  too smart and doesn't let you compile without this.
		// This line just shuts the compiler up.
		return null;
	}




	/**
	 * Returns the direction that is 90 degrees counter-clockwise of
	 * the current direction.
	 *
	 * @return the direction 90 degrees coutnerclockwise from the current
	 * direction
	 */
	public Direction counterClockwise90() {
		switch(dir) {
			case 0:
				return Direction.west;

			case 1:
				return Direction.north;

			case 2:
				return Direction.east;

			case 3:
				return Direction.south;
		}

		// This is dead code, but the Java compiler tries to be
		//  too smart and doesn't let you compile without this.
		// This line just shuts the compiler up.
		return null;
	}

	
	/**
	 *  Return the internal integer representation of this
	 *  direction.  This is mainly useful for people wanting to
	 *  implement other methods of iterating over the directions.
	 *
	 *  @return the internal integer representing this direction
	 */
	public int toInt() {
		return dir;
	}

	
	/** stores the internal representation of this direction */
	private final int dir;
}

