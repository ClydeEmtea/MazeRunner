import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener, Constants {
    public MainMenu() { // Constructor
        this.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT)); // Set window size
        this.setBackground(Color.BLACK); // Set background color
        this.setFocusable(true); // Allows the window to be focused
        this.addKeyListener(mkz = new MyKeyListener()); // Adds the MyKeyListener.java class to the JPanel
        start(); // Calls the start() method
    }

    MyKeyListener mkz;
    Timer timer;

    public void start() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void drawMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 50));
        g.drawString("Mazerunner", Constants.WIDTH / 2 - 150, Constants.HEIGHT / 2 - 100);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press Enter to start", Constants.WIDTH / 2 - 100, Constants.HEIGHT / 2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        // Inside the actionPerformed method in the MainMenu class
        if (mkz.enter) {
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

    }
}
