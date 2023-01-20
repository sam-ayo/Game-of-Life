import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Sim247 {
   private int iterations;
   // Grid size (fixed at 100x100)
   private final int GRID_SIZE = 100;
   // Cell size
   private final int CELL_SIZE = 8;
   private boolean[][] grid;
   private enum Pattern {RANDOM, MAZING, TURTLE}
   private Pattern pattern;
   private JFrame frame;
   private JPanel panel;

   public Sim247(int iterations, String patternType) {
      this.iterations = iterations;
      // Initialize grid
      grid = new boolean[GRID_SIZE][GRID_SIZE];
      // Set pattern type
      switch (patternType) {
         case "R":
            pattern = Pattern.RANDOM;
            break;
         case "M":
            pattern = Pattern.MAZING;
            break;
         case "T":
            pattern = Pattern.TURTLE;
            break;
         default:
            break;
      }
      // Initialize the cells based on the selected pattern
      initializeCells();
      // Create the frame and panel for the simulation
      createDisplay();
   }

   private void initializeCells() {
      switch (pattern) {
         case RANDOM:
            // Initialize cells with 50% chance of being alive or dead
            for (int i = 0; i < GRID_SIZE; i++) {
               for (int j = 0; j < GRID_SIZE; j++) {
                  grid[i][j] = Math.random() < 0.5;
               }
            }
            break;
         case MAZING:
            // Initialize cells in a Mazing pattern
            initializePattern("M");
            break;
         case TURTLE:
            // Initialize cells in a turtle pattern
            initializePattern("T");
            break;
      }
   }

   private void createDisplay() {
      frame = new JFrame("Sim247");
      panel = new JPanel() {
         @Override
         public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the cells
            for (int i = 0; i < GRID_SIZE; i++) {
               for (int j = 0; j < GRID_SIZE; j++) {
                  if (grid[i][j]) {
                     g.setColor(Color.BLACK);
                     g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                  }
               }
            }
         }
      };
      panel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
      frame.add(panel);
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }

   public void start() {
      for (int i = 0; i < iterations; i++) {
         // Update the grid based on the Game of Life rules
         update();
         // Repaint the panel to display the updated grid
         panel.repaint();
         //
         try {
            Thread.sleep(100); // sufficient delay between each iteration
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }
   private void update() {
      boolean[][] newGrid = new boolean[GRID_SIZE][GRID_SIZE];
      for (int i = 0; i < GRID_SIZE; i++) {
         for (int j = 0; j < GRID_SIZE; j++) {
            // Count the number of alive neighbors
            int neighbours = countLivingNeighbours(i, j);
            // Apply the Game of Life rules
            if (grid[i][j]) {
               if (neighbours < 2 || neighbours > 3) {
                  newGrid[i][j] = false;
               } else {
                  newGrid[i][j] = true;
               }
            } else {
               if (neighbours == 3) {
                  newGrid[i][j] = true;
               } else {
                  newGrid[i][j] = false;
               }
            }
         }
      }
      grid = newGrid;
   }

   private int countLivingNeighbours(int x, int y) {
      int count = 0;
      for (int i= -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            int neighborX = (x + i + GRID_SIZE) % GRID_SIZE;
            int neighborY = (y + j + GRID_SIZE) % GRID_SIZE;
            if (grid[neighborX][neighborY] && !(i == 0 && j == 0)) {
               count++;
            }
         }
      }
      return count;
   }


   public void initializePattern(String patternType){
      switch (patternType){
         case "T":
            grid[52][51] = true;
            grid[53][51] = true;
            grid[54][51] = true;
            grid[62][51] = true;

            grid[52][52] = true;
            grid[53][52] = true;
            grid[56][52] = true;
            grid[58][52] = true;
            grid[59][52] = true;
            grid[61][52] = true;
            grid[62][52] = true;

            grid[54][53] = true;
            grid[55][53] = true;
            grid[56][53] = true;
            grid[61][53] = true;

            grid[52][54] = true;
            grid[55][54] = true;
            grid[57][54] = true;
            grid[61][54] = true;

            grid[51][55] = true;
            grid[56][55] = true;
            grid[61][55] = true;

            grid[51][56] = true;
            grid[56][56] = true;
            grid[61][56] = true;

            grid[52][57] = true;
            grid[55][57] = true;
            grid[57][57] = true;
            grid[61][57] = true;

            grid[54][58] = true;
            grid[55][58] = true;
            grid[56][58] = true;
            grid[61][58] = true;

            grid[52][59] = true;
            grid[53][59] = true;
            grid[56][59] = true;
            grid[58][59] = true;
            grid[59][59] = true;
            grid[61][59] = true;
            grid[62][59] = true;

            grid[52][60] = true;
            grid[53][60] = true;
            grid[54][60] = true;
            grid[62][60] = true;
            for (int i = 0; i < GRID_SIZE; i++) {
               for (int j = 0; j < GRID_SIZE; j++) {
                  if (!grid[i][j]){
                     grid[i][j] = false;
                  };
               }
            }
            break;
         case "M":
            grid[54][51] = true;
            grid[55][51] = true;
            grid[52][52] = true;
            grid[54][52] = true;
            grid[51][53] = true;
            grid[57][53] = true;
            grid[52][54] = true;
            grid[56][54] = true;
            grid[57][54] = true;
            grid[54][56] = true;
            grid[56][56] = true;
            grid[55][57] = true;
            for (int i = 0; i < GRID_SIZE; i++) {
               for (int j = 0; j < GRID_SIZE; j++) {
                  if (!grid[i][j]){
                     grid[i][j] = false;
                  };
               }
            }
      }
   }

   public static void main(String[] args) {
      int iterations = Integer.parseInt(args[0]);
      String patternType = args[1];
      Sim247 sim = new Sim247(iterations, patternType);
      sim.start();
   }

}

