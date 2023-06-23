import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
    // This class is used to detect key presses and releases
    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    public boolean rotateLeft = false;
    public boolean rotateRight = false;
    public boolean space = false;


    @Override
    public void keyPressed(KeyEvent keyEvent) { // If a key is pressed, set the corresponding boolean to true
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_LEFT -> rotateLeft = true;
            case KeyEvent.VK_RIGHT -> rotateRight = true;
            case KeyEvent.VK_SPACE -> space = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) { // If a key is released, set the corresponding boolean to false
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_LEFT -> rotateLeft = false;
            case KeyEvent.VK_RIGHT -> rotateRight = false;
            case KeyEvent.VK_SPACE -> space = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

}
