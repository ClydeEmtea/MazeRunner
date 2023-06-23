import java.awt.*;

public class Counter implements Constants {
    // x and y are the coordinates of the top left corner of the block
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    public int count = COUNTDOWN;

    public Counter(int x, int y) { // Constructor
        this.x = x;
        this.y = y;
        this.width = SIZE;
        this.height = this.width * 8;
    }

    // Draw the block
    public void draw(Graphics g) {
        g.setColor(red);
        g.fillRect(this.x, this.y, width, height);
        g.setColor(green);
        g.fillRect(this.x, this.y + height - (int) (height * ((double) count / COUNTDOWN)), width, (int) (height * ((double) count / COUNTDOWN)));
    }
}
