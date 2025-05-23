// 322394545 Nimrod Netzer
package sprites;

import biuoop.DrawSurface;
import game.Game;
import game.GameEnvironment;
import geometry.Point;
import geometry.Velocity;
import geometry.Line;
import geometry.CollisionInfo;
import geometry.Collidable;

import java.awt.Color;

/**
 * The sprites.Ball class represents a ball with a center point, radius, color, and velocity.
 * It can move, detect collisions, and draw itself on a given surface.
 */
public class Ball implements Sprite {
    private Point center; // The center point of the ball
    private final int radius; // The radius of the ball
    private Color color; // The color of the ball
    private Velocity velocity; // The velocity of the ball
    private final GameEnvironment gameEnvironment; // The game environment the ball interacts with

    /**
     * Constructs a sprites.Ball object with the specified center point, radius, color, and game environment.
     *
     * @param center          the center point of the ball
     * @param radius          the radius of the ball
     * @param color           the color of the ball
     * @param gameEnvironment the game environment the ball interacts with
     */
    public Ball(Point center, int radius, Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Sets the velocity of the ball using dx and dy components.
     *
     * @param dx the x component of the velocity
     * @param dy the y component of the velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Moves the ball one step based on its current velocity and checks for collisions.
     */
    public void moveOneStep() {
        // Calculate the ball's trajectory
        Point nextCenter = this.velocity.applyToPoint(this.center);
        Point afterNextCenter = this.velocity.applyToPoint(nextCenter);
        Point nextAfterNextCenter = this.velocity.applyToPoint(afterNextCenter);
        Line trajectory = new Line(afterNextCenter, nextAfterNextCenter);

        // Check for collision
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            // There is a collision, move the ball to "almost" the collision point
            Point collisionPoint = collisionInfo.collisionPoint();
            Collidable collisionObject = collisionInfo.collisionObject();

            // Adjust the position to just before the collision
            double adjustment = 2.0;
            double newX = collisionPoint.getX() - adjustment * this.velocity.getDx();
            double newY = collisionPoint.getY() - adjustment * this.velocity.getDy();
            this.center = new Point(newX, newY);

            // Get new velocity after collision
            this.velocity = collisionObject.hit(this, collisionPoint, this.velocity);
        } else {
            // No collision, move the ball to the new position
            this.center = nextCenter;
        }
    }

    /**
     * Notifies the ball that time has passed, it should move one step.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Draws the ball on the given surface.
     *
     * @param surface the surface to draw the ball on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * Adds the ball to the game.
     *
     * @param game the game to add the ball to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    /**
     * Gets the color of the ball.
     *
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the ball.
     *
     * @param color the new color of the ball
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Removes the ball from the game.
     *
     * @param game the game to remove the ball from
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }
}
