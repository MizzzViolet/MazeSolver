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
public class DFSMazeRunnerTest {
 
    private String mazeFile;
    private String expectedOutput;
    static String ls = System.getProperty("line.separator");
 
    public DFSMazeRunnerTest(String inputFile, String expectedOutput) {
        this.mazeFile = inputFile;
        this.expectedOutput = expectedOutput;
    }
 
    // this is how you tell JUnit the values of the parameter
 
    @Parameters
    public static Iterable<String[]> inputFilesAndExpectedResults() {
        return Arrays
                .asList(new String[][] {
                        {
                                "sample-inputs/maze1.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (1,0) (2,0) (3,0) (3,1) (3,2) (2,2) "
                                        + ls + "6" + ls + "13" + ls },
                        {
                                "sample-inputs/maze2.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (1,0) (2,0) (3,0) (3,1) (3,2) (4,2) (4,3) (4,4) (4,5) (4,6) "
                                        + ls + "10" + ls + "13" + ls },
                        {
                                "sample-inputs/maze3.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (1,0) (1,1) (2,1) (2,2) (1,2) (1,3) (1,4) (2,4) (3,4) (3,3) (3,2) (3,1) (3,0) (4,0) (5,0) (6,0) (7,0) (7,1) (7,2) (7,3) (7,4) (7,5) (7,6) (7,7) (7,8) (7,9) (6,9) (5,9) (5,8) (4,8) (3,8) (3,9) (2,9) (1,9) (0,9) (0,8) (1,8) (1,7) "
                                        + ls + "38" + ls + "53" + ls },
                        {
                                "sample-inputs/maze4.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) (0,7) (0,8) (0,9) (0,10) (0,11) (0,12) (0,13) (1,13) (2,13) (3,13) (4,13) (4,12) (5,12) (6,12) (6,11) (6,10) (7,10) (7,9) (7,8) (7,7) (7,6) (7,5) (7,4) (7,3) (7,2) (6,2) (5,2) (5,1) (4,1) (3,1) (3,0) "
                                        + ls + "37" + ls + "83" + ls },
                        {
                                "sample-inputs/nocycles1.txt",
                                "DFS"
                                        + ls
                                        + "(7,0) (8,0) (8,1) (8,2) (7,2) (6,2) (5,2) (5,3) (4,3) (3,3) (2,3) (1,3) (1,4) (2,4) (3,4) (3,5) (3,6) (3,7) (3,8) (4,8) (4,9) (3,9) (2,9) (1,9) (1,8) (0,8) (0,9) "
                                        + ls + "26" + ls + "85" + ls },
                        {
                                "sample-inputs/nocycles2.txt",
                                "DFS"
                                        + ls
                                        + "(14,19) (14,18) (13,18) (13,17) (13,16) (12,16) (12,15) (12,14) (12,13) (13,13) (14,13) (14,12) (13,12) (12,12) (11,12) (10,12) (9,12) (9,11) (9,10) (8,10) (8,9) (8,8) (8,7) (8,6) (8,5) (7,5) (7,6) (6,6) (6,7) (5,7) (5,8) (5,9) (5,10) (5,11) (4,11) (3,11) (3,10) (2,10) (2,9) (1,9) (0,9) (0,8) (1,8) (2,8) (2,7) (3,7) (3,6) (3,5) (3,4) (2,4) (1,4) (1,3) (0,3) (0,2) (0,1) (1,1) (1,0) (0,0) "
                                        + ls + "57" + ls + "280" + ls },
                        {
                                "sample-inputs/nopath2.txt",
                                "DFS" + ls + "(0,0) " + ls + "0" + ls + "288"
                                        + ls },
                        {
                                "sample-inputs/obstacles1.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (1,0) (2,0) (3,0) (3,1) (3,2) (3,3) "
                                        + ls + "6" + ls + "7" + ls },
                        {
                                "sample-inputs/obstacles2.txt",
                                "DFS"
                                        + ls
                                        + "(2,2) (2,1) (2,0) (3,0) (4,0) (5,0) (6,0) (7,0) (8,0) (9,0) (10,0) (11,0) (12,0) (13,0) (14,0) (14,1) (14,2) (14,3) (14,4) (14,5) (14,6) (14,7) (14,8) (14,9) (14,10) (14,11) (14,12) (14,13) (14,14) (14,15) (13,15) "
                                        + ls + "30" + ls + "250" + ls },
                        {
                                "sample-inputs/bigmaze1.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) (0,7) (0,8) (0,9) (0,10) (0,11) (0,12) (0,13) (1,13) (2,13) (3,13) (4,13) (4,12) (5,12) (6,12) (6,11) (6,10) (5,10) (5,9) (6,9) (6,8) (5,8) (5,7) (5,6) (6,6) (7,6) (7,5) (7,4) (7,3) (7,2) (6,2) (5,2) (5,1) (4,1) (3,1) (3,0) "
                                        + ls + "41" + ls + "87" + ls },
                        {
                                "sample-inputs/bigmaze2.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (1,0) (2,0) (3,0) (4,0) (4,1) (5,1) (5,0) (6,0) (6,1) (7,1) (8,1) (8,2) (7,2) (7,3) (8,3) (9,3) (9,4) (8,4) (7,4) (7,5) (6,5) (6,6) (7,6) (7,7) (7,8) (7,9) (8,9) (8,8) (9,8) (9,9) (9,10) (9,11) (8,11) (8,12) (8,13) (9,13) (9,14) (9,15) (8,15) (8,16) (7,16) (7,17) (8,17) (8,18) (8,19) (8,20) (9,20) (9,21) (9,22) (9,23) (9,24) (9,25) (9,26) (8,26) (8,27) (8,28) (9,28) (9,29) "
                                        + ls + "58" + ls + "76" + ls },
                        {
                                "sample-inputs/bigmaze3.txt",
                                "DFS"
                                        + ls
                                        + "(0,9) (0,8) (0,7) (0,6) (1,6) (2,6) (2,5) (3,5) (3,4) (3,3) (2,3) (2,2) (3,2) (4,2) (4,3) (5,3) (6,3) (6,4) (6,5) (7,5) (8,5) (8,4) (7,4) (7,3) (8,3) (9,3) "
                                        + ls + "25" + ls + "49" + ls },
                        {
                                "sample-inputs/bigmaze4.txt",
                                "DFS"
                                        + ls
                                        + "(2,0) (3,0) (3,1) (4,1) (5,1) (5,0) (6,0) (6,1) (6,2) (6,3) (6,4) (5,4) (5,3) (4,3) (4,4) (4,5) (5,5) (6,5) (6,6) (7,6) (7,7) (8,7) (8,8) (9,8) (9,9) (8,9) (8,10) (9,10) "
                                        + ls + "27" + ls + "50" + ls },
                        {
                                "sample-inputs/bigmaze5.txt",
                                "DFS"
                                        + ls
                                        + "(5,17) (4,17) (3,17) (3,16) (3,15) (3,14) (3,13) (3,12) (3,11) (3,10) (3,9) (3,8) (2,8) (2,7) (2,6) (2,5) (3,5) (3,4) (3,3) (2,3) (2,4) (1,4) (1,3) (1,2) (1,1) (0,1) "
                                        + ls + "25" + ls + "63" + ls },
                        {
                                "sample-inputs/bigmaze6.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (1,0) (1,1) (1,2) (1,3) (1,4) (1,5) (2,5) (3,5) (3,4) (4,4) (4,5) (4,6) (5,6) (6,6) (6,5) "
                                        + ls + "15" + ls + "33" + ls },
                        {
                                "sample-inputs/bigmaze7.txt",
                                "DFS"
                                        + ls
                                        + "(0,5) (0,4) (1,4) (1,3) (1,2) (2,2) (2,1) (3,1) (4,1) (5,1) (6,1) (7,1) (8,1) (9,1) (10,1) (10,2) (10,3) (10,4) (10,5) (11,5) (11,6) (11,7) (12,7) (12,6) (13,6) (13,7) (14,7) (14,6) (14,5) (14,4) (15,4) (15,3) (15,2) (16,2) (16,3) (17,3) (18,3) (18,4) (18,5) (18,6) (18,7) (19,7) "
                                        + ls + "41" + ls + "90" + ls },
                        {
                                "sample-inputs/bigmaze9.txt",
                                "DFS"
                                        + ls
                                        + "(0,0) (0,1) (0,2) (1,2) (1,3) (1,4) (0,4) (0,5) (0,6) (1,6) (2,6) (2,5) (3,5) (3,6) (3,7) (3,8) (3,9) (4,9) (5,9) (5,10) (6,10) (6,9) (7,9) (7,8) (7,7) (8,7) (9,7) (9,6) (9,5) (10,5) (11,5) (12,5) (12,4) (13,4) (13,3) (13,2) (12,2) (12,1) (13,1) (14,1) (15,1) (16,1) (17,1) (18,1) (18,2) (18,3) (17,3) (17,4) (17,5) (16,5) (15,5) (15,6) (16,6) (16,7) (17,7) (18,7) (19,7) (20,7) (20,6) (20,5) (20,4) (21,4) (21,5) (22,5) (22,6) (22,7) (22,8) (23,8) (24,8) (24,7) (24,6) (25,6) (25,5) (24,5) (24,4) (23,4) (23,3) (23,2) (23,1) (23,0) (24,0) (25,0) (26,0) (26,1) (26,2) (27,2) (27,1) (28,1) (29,1) (30,1) (31,1) (32,1) (32,2) (31,2) (30,2) (30,3) (29,3) (29,4) (29,5) (30,5) (31,5) (31,4) (32,4) (32,3) (33,3) (33,4) (33,5) (34,5) (35,5) (35,4) (36,4) (37,4) (38,4) (38,5) (38,6) (38,7) (38,8) (39,8) (39,9) (39,10) (39,11) (39,12) (39,13) (39,14) (38,14) (38,15) (38,16) (38,17) (38,18) (38,19) (37,19) (36,19) (36,18) (35,18) (35,17) (35,16) (36,16) (36,15) (36,14) (35,14) (34,14) (34,13) (34,12) (33,12) (33,11) (33,10) (33,9) (33,8) (33,7) (32,7) (32,6) (31,6) (30,6) (30,7) (30,8) (29,8) (29,9) (28,9) (28,10) (29,10) (30,10) (31,10) (31,11) (30,11) (30,12) (31,12) (31,13) (30,13) (29,13) (29,14) (30,14) (31,14) (32,14) (32,15) (32,16) (32,17) (31,17) (31,18) (30,18) (29,18) (29,17) (29,16) (28,16) (28,17) (27,17) (27,18) (26,18) (26,19) (25,19) (24,19) (24,18) (24,17) (24,16) (23,16) (22,16) (22,17) (21,17) (21,18) (21,19) (21,20) (20,20) (20,21) (21,21) (21,22) (22,22) (23,22) (23,23) (24,23) (25,23) (25,22) (26,22) (27,22) (28,22) (28,23) (29,23) (30,23) (31,23) (31,24) (32,24) (32,25) (32,26) (32,27) (31,27) (30,27) (29,27) (29,28) (29,29) (30,29) (30,30) (31,30) (32,30) (33,30) (34,30) (34,29) (35,29) (35,28) (36,28) (37,28) (38,28) (38,29) (37,29) (37,30) (37,31) (37,32) (37,33) (37,34) (38,34) (38,35) (38,36) (38,37) (39,37) (39,38) (39,39) "
                                        + ls + "252" + ls + "506" + ls } });
    }
 
    // ~ Test the DFSMazeRunner
    // ..........................................................
    @Test
    public void testDFSMazeRunner() {
 
        MazeRunner<SquareCell> dfs = new DFSMazeRunner<SquareCell>();
        SquareCellMaze maze = null;
 
        try {
            // Create a maze from the given filename (passed as a parameter to
            // the
            // constructor of the JUnit test class, and stored in mazeFile).
            maze = SquareCellMaze.SquareCellMazeFactory.parseMaze(mazeFile);
 
            StringWriter output = new StringWriter();
            PrintWriter writer = new PrintWriter(output, true);
     
            // Solve the maze
            dfs.solveMaze(maze, writer);
 
            // ensure the writer is closed so it flushes the output.
            writer.close();
 
            // this will fail for the random runner because each time it returns
            // another path
            assertEquals("DFSMazeRunner solved maze " + mazeFile,
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

