// 322394545 Nimrod Netzer

/**
 * The CollisionInfo class represents information about a collision,
 * including the collision point and the collidable object involved.
 */
@SuppressWarnings("ClassCanBeRecord")
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * Constructs a CollisionInfo object with the given collision point and collidable object.
     *
     * @param collisionPoint the point where the collision occurred
     * @param collisionObject the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Gets the collision point where the collision occurred.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Gets the collidable object involved in the collision.
     *
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
