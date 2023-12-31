import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener, Constants {
    public MainMenu() { // Constructor
        this.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT)); // Set window size
        this.setBackground(Color.BLACK); // Set background color
        this.setFocusable(true); // Allows the window to be focused
        this.addKeyListener(mkl = new MyKeyListener()); // Adds the MyKeyListener.java class to the JPanel
        start(); // Calls the start() method
    }

    // Variables
    MyKeyListener mkl;
    Timer timer;
    Font font;

    // Starts the game and initializes variables and objects to their default values and states
    public void start() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Draws the menu
    public void drawMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(font = new Font("Arial", Font.PLAIN, 50));
        g.drawString("Mazerunner",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Mazerunner") / 2),
                Constants.HEIGHT / 2 - 100);
        g.setFont(font = new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press Enter to start",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Press Enter to start") / 2),
                Constants.HEIGHT / 2);
        g.drawString("Press Esc to exit",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Press Esc to exit") / 2),
                Constants.HEIGHT / 2 + 50);
    }

    // Paints the menu
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
    }

    // Called when an action is performed
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        // If the enter key is pressed, the game will start
        if (mkl.enter) {
            timer.stop();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.remove(this);
            App app = new App();
            frame.add(app);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            app.requestFocus(); // Set focus on the App class or its parent component
        }

        // If the escape key is pressed, the program will exit
        if (mkl.esc) {
            System.exit(0);
        }

    }
}
