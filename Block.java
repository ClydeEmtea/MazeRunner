import java.awt.*;

public class Block implements Constants {
    // x and y are the coordinates of the top left corner of the block
    public final int x;
    public final int y;
    public Color color = grey;

    public Block(int x, int y) { // Constructor
        this.x = x * SIZE;
        this.y = y * SIZE;
    }

    // Draw the block
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.x + WIDTH / 4, this.y + SIZE / 2, SIZE, SIZE);
    }

    // Check if the player is colliding with the block
    public boolean isColliding(float x, float y, int radius) {
        return (this.x <= x + radius * 2 && x <= (this.x + SIZE) &&
                this.y <= y + radius * 2 && y <= (this.y + SIZE));
    }
}
