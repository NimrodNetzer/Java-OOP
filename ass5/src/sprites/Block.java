// 322394545 Nimrod Netzer
package sprites;

import biuoop.DrawSurface;
import game.Game;
import geometry.Collidable;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.HitListener;
import listeners.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A sprites.Block class that implements both geometry.Collidable and sprites.Sprite interfaces.
 * Represents a rectangular block that can collide with other objects and be drawn on a DrawSurface.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private final Rectangle rectangle; // The rectangle shape of the block
    private final Color color; // The color of the block
    private final List<HitListener> hitListeners; // List of hit listeners

    /**
     * Constructs a block with a given upper-left point, width, height, and color.
     *
     * @param upperLeft the upper-left point of the block
     * @param width     the width of the block
     * @param height    the height of the block
     * @param color     the color of the block
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = color;
        this.hitListeners = new ArrayList<>(); // Initialize the hit listeners list
    }

    /**
     * Returns the collision rectangle of the block.
     * Implements the geometry.Collidable interface method.
     *
     * @return the collision rectangle of the block
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Adds a hit listener to the block.
     *
     * @param hl the hit listener to add
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes a hit listener from the block.
     *
     * @param hl the hit listener to remove
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies all registered hit listeners about a hit event.
     *
     * @param hitter the ball that hit the block
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Handles a collision with the block.
     * Determines the new velocity of an object colliding with the block.
     * Implements the geometry.Collidable interface method.
     *
     * @param collisionPoint  the point of collision with the block
     * @param currentVelocity the current velocity of the colliding object
     * @return the new velocity of the colliding object after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
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

        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
            hitter.setColor(this.color);
        }

        if (collisionPoint.getY() >= 680) {
            this.notifyHit(hitter);
        }


        // Return the new velocity after the collision
        return new Velocity(dx, dy);
    }

    /**
     * Draws the block on a given DrawSurface.
     * Implements the sprites.Sprite interface method.
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
     * Implements the sprites.Sprite interface method.
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

    /**
     * Checks if the ball's color matches the block's color.
     *
     * @param ball the ball to check
     * @return true if the colors match, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        if (this.rectangle.getHeight() > 780 || this.rectangle.getHeight() < 20
                || this.rectangle.getWidth() < 15 || this.rectangle.getWidth() > 790) {
            return true;
        }
        return this.color == ball.getColor();
    }

    /**
     * Removes the block from the game.
     *
     * @param game the game to remove the block from
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }
}