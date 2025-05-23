// 322394545 Nimrod Netzer
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * A Block class that implements both Collidable and Sprite interfaces.
 * Represents a rectangular block that can collide with other objects and be drawn on a DrawSurface.
 */
public class Block implements Collidable, Sprite {
    private final Rectangle rectangle; // The rectangle shape of the block
    private final Color color; // The color of the block

    /**
     * Constructs a block with a given upper-left point, width, and height.
     * Sets the default color of the block to RED.
     *
     * @param upperLeft the upper-left point of the block
     * @param width the width of the block
     * @param height the height of the block
     */
    public Block(Point upperLeft, double width, double height) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = Color.RED; // Default color
    }

    /**
     * Constructs a block with a given upper-left point, width, height, and color.
     *
     * @param upperLeft the upper-left point of the block
     * @param width the width of the block
     * @param height the height of the block
     * @param color the color of the block
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = color;
    }

    /**
     * Returns the collision rectangle of the block.
     * Implements the Collidable interface method.
     *
     * @return the collision rectangle of the block
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handles a collision with the block.
     * Determines the new velocity of an object colliding with the block.
     * Implements the Collidable interface method.
     *
     * @param collisionPoint the point of collision with the block
     * @param currentVelocity the current velocity of the colliding object
     * @return the new velocity of the colliding object after the collision
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Determine which edge the collision occurs on and reverse the velocity accordingly
        if (collisionPoint.getY() == this.rectangle.getUpperLeft().getY()
                || collisionPoint.getY() == this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight()) {
            dy = -dy; // Reverse vertical velocity
        }
        if (collisionPoint.getX() == this.rectangle.getUpperLeft().getX()
                || collisionPoint.getX() == this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()) {
            dx = -dx; // Reverse horizontal velocity
        }

        // Return the new velocity after the collision
        return new Velocity(dx, dy);
    }

    /**
     * Draws the block on a given DrawSurface.
     * Implements the Sprite interface method.
     *
     * @param surface the DrawSurface to draw the block on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.rectangle.getUpperLeft().getX(),
                (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(),
                (int) this.rectangle.getHeight());
        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) this.rectangle.getUpperLeft().getX(),
                (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(),
                (int) this.rectangle.getHeight());
    }

    /**
     * Performs actions on the block as time passes.
     * Currently, no actions are performed.
     * Implements the Sprite interface method.
     */
    @Override
    public void timePassed() {
        // Currently, we do nothing here.
    }

    /**
     * Adds the block to the game as both a collidable and a sprite.
     *
     * @param game the game to add the block to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }
}
