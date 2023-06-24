import java.awt.*;
import java.util.List;

public class Player implements Constants {
    // x and y are the coordinates of the top left corner of the player
    private float x;
    private float y;
    private final int radius = 6;
    private double angle = Math.PI;
    private final int speed;
    private final double sense;
    public boolean end = false;
    public boolean victory = false;

    public Player(float x, float y) { // Constructor
        this.x = x;
        this.y = y;
        this.speed = 2;
        this.sense = 0.05;
    }

    // Draw the player
    public void draw(Graphics g) {
        g.setColor(red);
        g.fillOval((int) this.x + WIDTH / 4, (int) this.y + SIZE / 2, 2 * radius, 2 * radius);
    }

    // Move the player
    public void move(List<Block> blocks, List<SpecialBlock> specialBlocks, MyKeyListener mkl) {
        double dx = 0; // The x component of the movement vector
        double dy = 0; // The y component of the movement vector

        if (mkl.up) { // If the up key is pressed, move the player forward
            dx += -1 * (speed * Math.cos(angle));
            dy += -1 * (speed * Math.sin(angle));
        }
        if (mkl.down) { // If the down key is pressed, move the player backward
            dx += (speed * Math.cos(angle));
            dy += (speed * Math.sin(angle));
        }
        if (mkl.left) { // If the left key is pressed, move the player left
            dx += -1 * (speed * Math.sin(angle));
            dy += (speed * Math.cos(angle));
        }
        if (mkl.right) { // If the right key is pressed, move the player right
            dx += (speed * Math.sin(angle));
            dy += -1 * (speed * Math.cos(angle));
        }

        // Normalize the movement vector
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length > speed) { // If the length is greater than the speed, normalize it
            double factor = speed / length;
            dx *= factor;
            dy *= factor;
        }

        if (mkl.rotateLeft) { // If the left arrow key is pressed, rotate the player left
            angle -= sense;
        }
        if (mkl.rotateRight) { // If the right arrow key is pressed, rotate the player right
            angle += sense;
        }

        if (dx != 0 || dy != 0) { // If the player is moving, check for collisions
            for (Block block : blocks) { // Check for collisions with each block
                if (block.isColliding((float) (this.x + dx), (float) (this.y + dy), radius)) {
                    dx = 0;
                    dy = 0;
                    break;
                }
            }
            for (SpecialBlock specialBlock : specialBlocks) {
                if (specialBlock.isColliding((float) (this.x + dx), (float) (this.y + dy), radius)) {
                    end = true;
                    victory = true;
                }
            }
        }

        this.x += dx; // Move the player
        this.y += dy;
    }

    // Cast rays to render the 3D view
    public void cast(List<Block> blocks, List<SpecialBlock> specialBlocks, Graphics g) {
        double startAngle = angle + (Math.PI / 3); // The angle of the first ray

        for (int ray = 0; ray < NUM_RAYS; ray++) { // Cast NUM_RAYS rays
            Block hitBlock = null; // The block that the ray hits
            SpecialBlock hitSpecialBlock = null; // The special block that the ray hits

            // Cast the ray until it hits a block or special block
            for (double depth = 0; depth < MAX_DEPTH; depth++) {
                double targetX = this.x - (depth * Math.sin(startAngle)); // The x coordinate of the target
                double targetY = this.y + (depth * Math.cos(startAngle)); // The y coordinate of the target

                for (Block block : blocks) { // Check if the ray hits a block
                    if (block.x <= targetX + radius && targetX + radius <= block.x + SIZE &&
                            block.y <= targetY + radius && targetY + radius <= block.y + SIZE) {
                        hitBlock = block;
                        break;
                    }
                }

                for (SpecialBlock specialBlock : specialBlocks) { // Check if the ray hits a special block
                    if (specialBlock.x <= targetX + radius && targetX + radius <= specialBlock.x + SIZE &&
                            specialBlock.y <= targetY + radius && targetY + radius <= specialBlock.y + SIZE) {
                        hitSpecialBlock = specialBlock;
                        break;
                    }
                }

                // If the ray hits a block or special block, render the 3D view
                if (hitBlock != null || hitSpecialBlock != null) {
                    int color = 255 - (int) (depth * 1.5);

                    if (color <= 0) {
                        color = 0;
                    }

                    depth *= (Math.cos(this.angle - startAngle) * 1.1); // Correct the fisheye effect
                    if (depth == 0) {
                        depth = 0.001;
                    }

                    int height = (int) ((HEIGHT / depth) * Math.cos(startAngle - this.angle)
                            * 50); // The height of the wall
                    if (height > HEIGHT) {
                        height = HEIGHT;
                    }

                    // The coordinates of the rectangle to be drawn
                    int rectX = (ray * WIDTH / NUM_RAYS);
                    int rectY = (HEIGHT - height) / 2;
                    int rectWidth = (WIDTH / NUM_RAYS + 1);
                    int rectHeight = height;

                    if (hitBlock != null) {
                        g.setColor(new Color(color, color, color));
                    } else {
                        color *= 2;
                        if (color > 255) {
                            color = 255;
                        }
                        g.setColor(new Color(0, color, color));
                    }

                    g.fillRect(rectX, rectY, rectWidth, rectHeight);

                    break;
                }
            }
            startAngle += Math.PI / (NUM_RAYS * 3); // Increment the angle of the next ray
        }
    }

}
