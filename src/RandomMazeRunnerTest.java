import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Arrays;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This JUnit test class invokes the Maze solver(s) on a number of input mazes.
 * See following link on parameterized JUnit testing:
 * http://examples.javacodegeeks.com/core-java/junit/junit-parameterized-test-example/
 *
 */
@RunWith(Parameterized.class)
public class RandomMazeRunnerTest {

  private String mazeFile;
  private String expectedOutput;

  public RandomMazeRunnerTest(String inputFile, String expectedOutput) {
    this.mazeFile = inputFile;
    this.expectedOutput = expectedOutput;
  }

  // this is how you tell JUnit the values of the parameter

  @Parameters
  public static Iterable<String[]> inputFilesAndExpectedResults() {
    return Arrays.asList(new String[][] {
      {"sample-inputs/maze1.txt",""},
      {"sample-inputs/maze2.txt",""},
      {"sample-inputs/maze3.txt",""},
      {"sample-inputs/maze4.txt",         ""},
      {"sample-inputs/nocycles1.txt",     ""},
      {"sample-inputs/nocycles2.txt",     ""},
//      {"../sample-inputs/nopath1.txt",       ""},  // the random maze runner loops forever for these mazes
//      {"../sample-inputs/nopath2.txt",       ""},
      {"sample-inputs/obstacles1.txt",    ""},
      {"sample-inputs/obstacles2.txt",    ""},
      {"sample-inputs/bigmaze1.txt",      ""},
      {"sample-inputs/bigmaze2.txt",      ""},
      {"sample-inputs/bigmaze3.txt",      ""},
      {"sample-inputs/bigmaze4.txt",      ""},
      {"sample-inputs/bigmaze5.txt",      ""},
      {"sample-inputs/bigmaze6.txt",      ""},
      {"sample-inputs/bigmaze7.txt",      ""},
      {"sample-inputs/bigmaze9.txt",      ""}
    });
  }


  //~ Test the RandomMazeRunner ..........................................................
  @Test
  public void testRandomMazeRunner()
  {

		MazeRunner<SquareCell> runner = new RandomMazeRunner<SquareCell>();
		SquareCellMaze maze = null;
	  try {
      // Create a maze from the given filename (passed as a parameter to the
      // constructor of the JUnit test class, and stored in mazeFile).
	  	maze = SquareCellMaze.SquareCellMazeFactory.parseMaze(mazeFile);

      StringWriter output = new StringWriter();
	  	PrintWriter writer = new PrintWriter(output, true);

	  	// Solve the maze
	  	runner.solveMaze(maze, writer);

	  	// ensure the writer is closed so it flushes the output.
	  	writer.close();

      // this will fail for the random runner because each time it returns another path
      assertEquals("RandomMazeRunner solved maze " + mazeFile, "", output.toString());

	  } catch (FileNotFoundException fnfe) {
	  	System.err.println("Could not find file " + mazeFile);
	  } catch (IOException ioe) {
	  	System.err.println("Error reading file " + mazeFile);
	  }
	}

  //~ A main method to run the above tests ..........................................................
  //  from the command line, copy hamcrest-core-1.3.jar and junit-4.11.jar to the current directory, then:
  //  compile: javac -cp junit-4.11.jar:. MazeRunnerTest.java 
  //  run: java -cp hamcrest-core-1.3.jar:junit-4.11.jar:. MazeRunnerTest
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(RandomMazeRunnerTest.class);
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
  }
}
