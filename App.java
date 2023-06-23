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

    // Starts the timer and creates the map
    public void start() {
        timer = new Timer(DELAY, this);
        timer.start();
        counter = new Counter(Constants.WIDTH / 10, Constants.HEIGHT - Constants.HEIGHT / 2);
        blocks = new ArrayList<>();
        specialBlocks = new ArrayList<>();
        createMap();
        player = new Player(specialBlocks.get(1).x + (float) Constants.SIZE / 2,
                specialBlocks.get(1).y + (float) Constants.SIZE / 2);
    }

    // Creates the map
    public void createMap() {

         for (int i = -1; i < Constants.MAP.length; i++) {
            for (int j = -1; j < Constants.MAP.length; j++) {
                if (i == -1 || j == -1 || i == Constants.MAP.length - 1 || j == Constants.MAP.length - 1) {
                    blocks.add(new Block(j, i));
                }
            }
        }
        for (int i = 0; i < Constants.MAP.length; i++) {
            for (int j = 0; j < Constants.MAP[i].length; j++) {
                if (Constants.MAP[i][j] == 1) {
                    blocks.add(new Block(j, i));
                }
                if (Constants.MAP[i][j] == 2) {
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
        player.cast(blocks, g);
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
        if (player != null) player.move(blocks, specialBlocks, mkl);
    }
}