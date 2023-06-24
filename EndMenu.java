import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndMenu extends JPanel implements ActionListener, Constants {
    public EndMenu() { // Constructor
        this.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT)); // Set window size
        this.setBackground(Color.BLACK); // Set background color
        this.setFocusable(true); // Allows the window to be focused
        this.addKeyListener(mkl = new MyKeyListener()); // Adds the MyKeyListener.java class to the JPanel
        start(); // Calls the start() method
    }

    MyKeyListener mkl;
    Timer timer;
    Font font;

    public void start() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void drawMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(font = new Font("Arial", Font.PLAIN, 50));
        g.drawString("Victory",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Victory") / 2),
                Constants.HEIGHT / 2 - 100);
        g.setFont(font = new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press Enter to return to main menu",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth(
                        "Press Enter to return to main menu") / 2),
                Constants.HEIGHT / 2);
        g.drawString("Press Esc to exit",
                Constants.WIDTH / 2 - (g.getFontMetrics(font).stringWidth("Press Esc to exit") / 2),
                Constants.HEIGHT / 2 + 50);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        // Inside the actionPerformed method in the EndMenu class
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
        if (mkl.esc) {
            System.exit(0);
        }

    }
}
