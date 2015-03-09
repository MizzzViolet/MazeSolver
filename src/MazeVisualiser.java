import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.Iterator;
import java.awt.image.BufferedImage;
  
public class MazeVisualiser extends Panel implements MazeChangeListener {
  
    private SquareCellMaze maze;
    private int wallLength;
    private int screenSize;
    public BufferedImage image;
    public MazeVisualiser(SquareCellMaze maze) {
  
        JFrame frame = new JFrame("Maze!");
  
        frame.getContentPane().setBackground(Color.WHITE);
  
        frame.getContentPane().add(this);
  
        screenSize = 500;
  
        this.maze = maze;
  
        this.wallLength = (int) (500.0 / this.maze.getWidth());
  
        frame.setSize(screenSize + wallLength, screenSize + wallLength);
  
        frame.setVisible(true);
  
        frame.setResizable(false);
  
    }
  
    @Override
    public void paint(Graphics g) {
  
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        g2d.setStroke(new BasicStroke(3));
  
        int xLength = (int) (500.0 / this.maze.getWidth());
  
        int yLength = (int) (500.0 / this.maze.getWidth()); // use width for
  
        Iterator<SquareCell> it = maze.getCells();
  
        while (it.hasNext()) {
  
            SquareCell temp = it.next();
  
            if (temp.getState() == MazeCell.CellState.solutionPath) {
  
                g2d.setColor(Color.GREEN);
  
                int x = temp.getCol() * xLength;
  
                int y = temp.getRow() * xLength;
  
                g2d.fillRect(x, y, xLength, xLength);
  
            } else if (temp.getState() == MazeCell.CellState.visitInProgress) {
  
                g2d.setColor(Color.YELLOW);
  
                int x = temp.getCol() * xLength;
  
                int y = temp.getRow() * xLength;
  
                g2d.fillRect(x, y, xLength, xLength);
  
            } else if (temp.getState() == MazeCell.CellState.visited) {
  
                g2d.setColor(Color.RED);
  
                int x = temp.getCol() * xLength;
  
                int y = temp.getRow() * xLength;
  
                g2d.fillRect(x, y, xLength, xLength);
            }
  
            if (temp.isStart()) {
                // g2d.setColor(Color.YELLOW);
                try {
                    image = ImageIO.read(new File("images/drunkenmouse.gif"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int x = temp.getCol() * xLength;
                int y = temp.getRow() * xLength;
                g2d.drawImage(image,x, y, xLength, xLength, null);
                // g2d.fillRect(x, y, xLength, xLength);
            }
  
            if (temp.isDonut()) {
  
                //g2d.setColor(Color.PINK);
                try {
                    image = ImageIO.read(new File("images/donut.gif"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int x = temp.getCol() * xLength;
                int y = temp.getRow() * xLength;
                g2d.drawImage(image,x, y, xLength, xLength, null);
                //g2d.fillRect(x, y, xLength, xLength);
  
            }
        }
        g2d.setColor(Color.darkGray);
  
        for (int i = 0; i < this.maze.getWidth(); i++) {
  
            for (int j = 0; j < this.maze.getHeight(); j++) {
  
                SquareCell temp = this.maze.getCell(j, i);
  
                if (temp.isWall(Direction.north)) {
  
                    g2d.drawLine(i * xLength, j * yLength, i * xLength
  
                    + xLength, j * yLength);
  
                }
  
                if (temp.isWall(Direction.south)) {
  
                    g2d.drawLine(i * xLength, (j + 1) * yLength, i
  
                    * xLength + xLength, (j + 1) * yLength);
  
                }
  
                if (temp.isWall(Direction.east)) {
  
                    g2d.drawLine((i + 1) * xLength, j * yLength, (i + 1)
  
                    * xLength, (j) * yLength + yLength);
  
                }
  
                if (temp.isWall(Direction.west)) {
  
                    g2d.drawLine((i) * xLength, j * yLength, i * xLength,
  
                    (j) * yLength + yLength);
  
                }
            }
        }
    }
  
    public void cellStateChangeEvent(MazeCell cell) {
  
        try {
  
            Thread.sleep(30);
  
        } catch (InterruptedException e) {
  
            // TODO Auto-generated catch block
  
            e.printStackTrace();
  
        }
        repaint();
    }
  
    public void stateChangeEvent() {
  
    }
  
} 
