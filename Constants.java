import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public interface Constants {
    // Constants used throughout the program
    int WIDTH = 1600;
    int HEIGHT = 800;
    int SIZE = 40;
    int DELAY = 20;
    int NUM_RAYS = 500;
    int COUNTDOWN = 500;
    Color red = new Color(255, 0, 0);
    Color yellow = new Color(255, 255, 0);
    Color turquoise = new Color(0, 255, 255);
    Color green = new Color(0, 100, 0);

    int MAX_DEPTH = (int) (255 / 1.5);


    // Method that creates a maze using the recursive backtracking algorithm
    static int[][] createMap(int height, int width) {
        int[][] maze = new int[height][width];

        for (int i = 0; i < height; i++) {
            Arrays.fill(maze[i], 1);
        }

        createMaze(maze, 0, 0);  // Start generating maze from the top-left corner

        // Randomly select start and finish positions
        Random random = new Random();
        int start_x = random.nextInt(width);
        int start_y = random.nextInt(height);
        int finish_x = random.nextInt(width);
        int finish_y = random.nextInt(height);

        // Ensure start and finish positions are not the same
        while ((start_x == finish_x && start_y == finish_y) ||
                (maze[start_x][start_y] == 1 || maze[finish_x][finish_y] == 1)) {
            start_x = random.nextInt(width);
            start_y = random.nextInt(height);
            finish_x = random.nextInt(width);
            finish_y = random.nextInt(height);
        }

        // Set start and finish positions
        maze[start_y][start_x] = 2;  // Start position
        maze[finish_y][finish_x] = 2;  // Finish position

        return maze;
    }

    static void createMaze(int[][] maze, int x, int y) {
        maze[y][x] = 0;  // Mark current cell as empty

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        shuffleArray(directions);

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int nx = x + dx * 2;
            int ny = y + dy * 2;

            if (0 <= nx && nx < maze[0].length && 0 <= ny && ny < maze.length && maze[ny][nx] == 1) {
                maze[ny - dy][nx - dx] = 0;  // Knock down the wall between current and next cell
                createMaze(maze, nx, ny);  // Recursively visit next cell
            }
        }
    }

    // Method that shuffles a 2D array
    static void shuffleArray(int[][] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}