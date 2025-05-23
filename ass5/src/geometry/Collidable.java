package geometry;

import sprites.Ball;

/**
 * The Collidable interface represents objects that can collide with other objects in a game.
 * <p>
 * A collidable object provides methods to retrieve its collision shape and to handle collisions.
 * </p>
 */
public interface Collidable {

    /**
     * Handles a collision between this collidable object and a ball.
     *
     * @param hitter the ball that is colliding with this object
     * @param collisionPoint the point where the collision occurs
     * @param currentVelocity the current velocity of the ball before the collision
     * @return the new velocity of the ball after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Returns the "collision shape" of the object.
     *
     * @return the collision rectangle of the object
     */
    Rectangle getCollisionRectangle();
}
