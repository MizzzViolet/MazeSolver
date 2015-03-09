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
 * http://examples.javacodegeeks
 * .com/core-java/junit/junit-parameterized-test-example/
 *
 */
@RunWith(Parameterized.class)
public class BFSMazeRunnerTest {
 
    private String mazeFile;
    private String expectedOutput;
    static String ls = System.getProperty("line.separator");
 
    public BFSMazeRunnerTest(String inputFile, String expectedOutput) {
        this.mazeFile = inputFile;
        this.expectedOutput = expectedOutput;
    }
 
    // this is how you tell JUnit the values of the parameter
 
    @Parameters
    public static Iterable<String[]> inputFilesAndExpectedResults() {
        return Arrays.asList(new String[][] {
                {"sample-inputs/maze1.txt","BFS"+ls+ "(0,0) (0,1) (0,2) (1,2) (2,2) "+ls+"4"+ls+"0"+ls},
                {"sample-inputs/maze2.txt","BFS"+ls+"(0,0) (1,0) (2,0) (3,0) (3,1) (3,2) (4,2) (4,3) (4,4) (4,5) (4,6) "+ls+"10"+ls+"0"+ls},
                {"sample-inputs/maze3.txt","BFS"+ls+"(0,0) (0,1) (0,2) (0,3) (1,3) (1,4) (0,4) (0,5) (0,6) (0,7) (0,8) (1,8) (1,7) "+ls+"12"+ls+"0"+ls},
                {"sample-inputs/maze4.txt","BFS"+ls+"(0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) (0,7) (0,8) (0,9) (0,10) (0,11) (0,12) (0,13) (1,13) (2,13) (3,13) (4,13) (4,12) (5,12) (6,12) (6,11) (7,11) (7,10) (7,9) (7,8) (7,7) (7,6) (7,5) (7,4) (7,3) (7,2) (6,2) (5,2) (5,1) (4,1) (3,1) (3,0) "+ls+"37"+ls+"0"+ls},
                {"sample-inputs/nocycles1.txt","BFS"+ls+"(7,0) (8,0) (8,1) (8,2) (7,2) (6,2) (5,2) (5,3) (4,3) (3,3) (2,3) (1,3) (1,4) (2,4) (3,4) (3,5) (3,6) (3,7) (3,8) (4,8) (4,9) (3,9) (2,9) (1,9) (1,8) (0,8) (0,9) "+ls+"26"+ls+"0"+ls},
                {"sample-inputs/nocycles2.txt","BFS"+ls+"(14,19) (14,18) (13,18) (13,17) (13,16) (12,16) (12,15) (12,14) (12,13) (13,13) (14,13) (14,12) (13,12) (12,12) (11,12) (10,12) (9,12) (9,11) (9,10) (8,10) (8,9) (8,8) (8,7) (8,6) (8,5) (7,5) (7,6) (6,6) (6,7) (5,7) (5,8) (5,9) (5,10) (5,11) (4,11) (3,11) (3,10) (2,10) (2,9) (1,9) (0,9) (0,8) (1,8) (2,8) (2,7) (3,7) (3,6) (3,5) (3,4) (2,4) (1,4) (1,3) (0,3) (0,2) (0,1) (1,1) (1,0) (0,0) "+ls+"57"+ls+"0"+ls},
                { "sample-inputs/nopath1.txt", "BFS"+ls+"(0,0) "+ls+"0"+ls+"0" +ls},
                {"sample-inputs/nopath2.txt","BFS"+ls+"(0,0) "+ls+"0"+ls+"0"+ls},
                {"sample-inputs/obstacles1.txt","BFS"+ls+"(0,0) (0,1) (0,2) (0,3) (1,3) (2,3) (3,3) "+ls+"6"+ls+"0"+ls},
                {"sample-inputs/obstacles2.txt","BFS"+ls+"(2,2) (2,3) (3,3) (3,4) (3,5) (3,6) (3,7) (3,8) (3,9) (3,10) (3,11) (3,12) (4,12) (5,12) (5,13) (5,14) (5,15) (6,15) (7,15) (8,15) (9,15) (10,15) (11,15) (12,15) (13,15) "+ls+"24"+ls+"0"+ls},
                {"sample-inputs/bigmaze1.txt","BFS"+ls+"(0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) (0,7) (0,8) (0,9) (0,10) (0,11) (0,12) (0,13) (1,13) (2,13) (3,13) (4,13) (4,12) (5,12) (6,12) (6,11) (6,10) (5,10) (5,9) (6,9) (6,8) (5,8) (5,7) (5,6) (6,6) (7,6) (7,5) (7,4) (7,3) (7,2) (6,2) (5,2) (5,1) (4,1) (3,1) (3,0) "+ls+"41"+ls+"0"+ls},
                {"sample-inputs/bigmaze2.txt","BFS"+ls+"(0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (1,5) (1,6) (1,7) (2,7) (3,7) (3,8) (3,9) (3,10) (3,11) (3,12) (2,12) (2,13) (3,13) (3,14) (3,15) (2,15) (2,16) (2,17) (2,18) (3,18) (3,19) (3,20) (3,21) (4,21) (4,22) (5,22) (6,22) (6,23) (6,24) (6,25) (6,26) (6,27) (6,28) (7,28) (7,29) (8,29) (9,29) "+ls+"42"+ls+"0"+ls},
                {"sample-inputs/bigmaze3.txt","BFS"+ls+"(0,9) (1,9) (1,8) (2,8) (2,7) (2,6) (3,6) (4,6) (5,6) (5,5) (6,5) (7,5) (8,5) (8,4) (7,4) (7,3) (8,3) (9,3) "+ls+"17"+ls+"0"+ls},
                {"sample-inputs/bigmaze4.txt","BFS"+ls+"(2,0) (3,0) (3,1) (3,2) (4,2) (4,3) (4,4) (4,5) (5,5) (6,5) (6,6) (6,7) (7,7) (8,7) (8,8) (9,8) (9,9) (8,9) (8,10) (9,10) "+ls+"19"+ls+"0"+ls},
                {"sample-inputs/bigmaze5.txt","BFS"+ls+"(5,17) (4,17) (3,17) (3,16) (3,15) (3,14) (3,13) (3,12) (3,11) (3,10) (3,9) (2,9) (2,8) (2,7) (2,6) (1,6) (1,5) (1,4) (1,3) (1,2) (0,2) (0,1) "+ls+"21"+ls+"0"+ls},
                {"sample-inputs/bigmaze6.txt","BFS"+ls+"(0,0) (1,0) (1,1) (1,2) (1,3) (1,4) (1,5) (2,5) (3,5) (3,4) (4,4) (4,5) (4,6) (5,6) (6,6) (6,5) "+ls+"15"+ls+"0"+ls},
                {"sample-inputs/bigmaze7.txt","BFS"+ls+"(0,5) (1,5) (2,5) (2,4) (2,3) (2,2) (2,1) (3,1) (4,1) (5,1) (6,1) (6,2) (7,2) (7,3) (7,4) (7,5) (7,6) (8,6) (8,7) (9,7) (10,7) (11,7) (12,7) (12,6) (13,6) (13,7) (14,7) (15,7) (16,7) (17,7) (17,6) (18,6) (18,7) (19,7) "+ls+"33"+ls+"0"+ls},
                {"sample-inputs/bigmaze9.txt","BFS"+ls+
                "(0,0) (0,1) (0,2) (1,2) (1,3) (0,3) (0,4) (0,5) (0,6) (1,6) (2,6) (2,5) (3,5) (3,6) (3,7) (3,8) (3,9) (4,9) (4,10) (5,10) (6,10) (6,9) (7,9) (7,8) (7,7) (8,7) (9,7) (9,8) (10,8) (11,8) (11,9) (11,10) (12,10) (12,11) (13,11) (13,12) (13,13) (13,14) (14,14) (14,15) (14,16) (13,16) (13,17) (14,17) (15,17) (15,18) (16,18) (17,18) (18,18) (19,18) (20,18) (21,18) (21,19) (22,19) (23,19) (24,19) (24,20) (25,20) (25,21) (25,22) (26,22) (27,22) (28,22) (28,23) (29,23) (30,23) (31,23) (31,24) (32,24) (32,25) (32,26) (32,27) (31,27) (30,27) (29,27) (29,28) (29,29) (30,29) (30,30) (31,30) (32,30) (33,30) (34,30) (34,31) (35,31) (36,31) (37,31) (37,32) (37,33) (37,34) (38,34) (38,35) (38,36) (38,37) (39,37) (39,38) (39,39) "+ls+"96"+ls+"0"+ls}
        });
    }
 
    // ~ Test the BFSMazeRunner
    // ..........................................................
    @Test
    public void testBFSMazeRunner() {
 
        MazeRunner<SquareCell> bfs = new BFSMazeRunner<SquareCell>();
        SquareCellMaze maze = null;
        try {
            // Create a maze from the given filename (passed as a parameter to
            // the
            // constructor of the JUnit test class, and stored in mazeFile).
            maze = SquareCellMaze.SquareCellMazeFactory.parseMaze(mazeFile);
 
            StringWriter output = new StringWriter();
            PrintWriter writer = new PrintWriter(output, true);
 
            // Solve the maze
            bfs.solveMaze(maze, writer);
 
            // ensure the writer is closed so it flushes the output.
            writer.close();
 
            // this will fail for the random runner because each time it returns
            // another path
            assertEquals("BFSMazeRunner solved maze " + mazeFile,
                    expectedOutput, output.toString());
 
        } catch (FileNotFoundException fnfe) {
            System.err.println("Could not find file " + mazeFile);
        } catch (IOException ioe) {
            System.err.println("Error reading file " + mazeFile);
        }
    }
 
    // ~ A main method to run the above tests
    // ..........................................................
    // from the command line, copy hamcrest-core-1.3.jar and junit-4.11.jar to
    // the current directory, then:
    // compile: javac -cp junit-4.11.jar:. MazeRunnerTest.java
    // run: java -cp hamcrest-core-1.3.jar:junit-4.11.jar:. MazeRunnerTest
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DFSMazeRunnerTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}

