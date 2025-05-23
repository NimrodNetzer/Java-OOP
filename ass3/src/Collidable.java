/**
 * The Collidable interface represents objects that can collide with other objects in a game.
 * <p>
 * A collidable object provides methods to retrieve its collision shape and to handle collisions.
 * </p>
 */
public interface Collidable {

    /**
     * Returns the "collision shape" of the object.
     *
     * @return the collision rectangle of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision occurred with it at {@code collisionPoint} with
     * a given velocity {@code currentVelocity}.
     * <p>
     * The method calculates and returns the new velocity expected after the collision,
     * based on the force the object inflicted on the colliding object.
     * </p>
     *
     * @param collisionPoint  the point at which the collision occurred.
     * @param currentVelocity the current velocity of the object that collided with this object.
     * @return the new velocity expected after the collision.
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}
