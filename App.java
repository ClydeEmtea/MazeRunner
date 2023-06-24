import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class App extends JPanel implements ActionListener, Constants {
    public App() { // Constructor
        this.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT)); // Set window size
        this.setBackground(Color.BLACK); // Set background color
        this.setFocusable(true); // Allows the window to be focused
        this.addKeyListener(mkl = new MyKeyListener()); // Adds the MyKeyListener.java class to the JPanel
        start(); // Calls the start() method
    }
    // Variables
    MyKeyListener mkl;
    Timer timer;
    Counter counter;
    List<Block> blocks;
    List<SpecialBlock> specialBlocks;
    public Player player;
    int[][] map;
    public long start;
    public long end;
    public float time;

    // Starts the game and initializes variables and objects to their default values and states
    public void start() {
        timer = new Timer(DELAY, this);
        timer.start();
        counter = new Counter(Constants.WIDTH / 10, Constants.HEIGHT - Constants.HEIGHT / 2);
        blocks = new ArrayList<>();
        specialBlocks = new ArrayList<>();
        map = Constants.createMap(Constants.HEIGHT / SIZE, Constants.HEIGHT / SIZE);
        createMap();
        player = new Player(specialBlocks.get(1).x + (float) Constants.SIZE / 2,
                specialBlocks.get(1).y + (float) Constants.SIZE / 2);
        start = System.currentTimeMillis();
    }

    // Creates the map
    public void createMap() {

         for (int i = -1; i < map.length; i++) {
            for (int j = -1; j < map.length; j++) {
                if (i == -1 || j == -1 || i == map.length - 1 || j == map.length - 1) {
                    blocks.add(new Block(j, i));
                }
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    blocks.add(new Block(j, i));
                }
                if (map[i][j] == 2) {
                    specialBlocks.add(new SpecialBlock(j, i));
                }
            }
        }
    }

    // Draws the map
    public void drawMap(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
        specialBlocks.get(0).draw(g);
    }

    // Paints the JPanel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draws the map, the player, rays and moves the player
    public void draw(Graphics g) {
        player.cast(blocks, specialBlocks.subList(0, 1),g);
        if (mkl.space && counter.count > 0) {
            drawMap(g);
            player.draw(g);
            counter.count--;
        }
        counter.draw(g);
    }

    // Repaints the JPanel
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
        end = System.currentTimeMillis();
        if (player != null) player.move(blocks, specialBlocks.subList(0,1), mkl);

        // If the player has reached the end of the map, stop the timer and display the end menu
        if (player != null && player.end) {
            time = (end - start) / 1000F;
            timer.stop();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.remove(this);
            EndMenu endMenu = new EndMenu(time, player.victory);
            frame.add(endMenu);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            endMenu.requestFocus(); // Set focus on the MainMenu class or its parent component
        }
        // If the player has run out of time, stop the timer and display the end menu
        if (player != null && end - start > 100000) {
            player.end = true;
        }
    }
}