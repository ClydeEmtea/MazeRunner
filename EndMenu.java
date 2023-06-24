import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndMenu extends JPanel implements ActionListener, Constants {
    public EndMenu(float end, boolean victory) { // Constructor
        this.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT)); // Set window size
        this.setBackground(Color.BLACK); // Set background color
        this.setFocusable(true); // Allows the window to be focused
        this.addKeyListener(mkl = new MyKeyListener()); // Adds the MyKeyListener.java class to the JPanel
        this.end = end;
        this.victory = victory;
        start(); // Calls the start() method
    }

    // Variables
    MyKeyListener mkl;
    Timer timer;
    Font font;

    private final float end;
    private final boolean victory;

    // Starts the game and initializes variables and objects to their default values and states
    public void start() {
        timer = new Timer(DELAY, this);
        timer.start();

    }

    // Draws the menu
    public void drawMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(font = new Font("Arial", Font.PLAIN, 50));
        if (victory) {
            g.drawString("Victory",
                    Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Victory") / 2),
                    Constants.HEIGHT / 2 - 100);
            g.setFont(font = new Font("Arial", Font.PLAIN, 40));
            g.drawString("Time: " + end + " seconds",
                    Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Time: " + end + " seconds") / 2),
                    Constants.HEIGHT / 2);
        } else {
            g.drawString("Game Over",
                    Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Game Over") / 2),
                    Constants.HEIGHT / 2 - 100);
        }
        g.setFont(font = new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press Enter to return to main menu",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth(
                        "Press Enter to return to main menu") / 2),
                Constants.HEIGHT / 2 + 100);
        g.drawString("Press Esc to exit",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Press Esc to exit") / 2),
                Constants.HEIGHT / 2 + 150);
    }

    // Paints the menu
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
    }

    // Handles the events
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        // If the enter key is pressed, the game will stop and the main menu will be displayed
        if (mkl.enter) {
            timer.stop();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.remove(this);
            MainMenu mainMenu = new MainMenu();
            frame.add(mainMenu);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            mainMenu.requestFocus(); // Set focus on the App class or its parent component
        }
        // If the esc key is pressed, the game will exit
        if (mkl.esc) {
            System.exit(0);
        }

    }
}
