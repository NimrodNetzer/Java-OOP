// 322394545 Nimrod Netzer

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment class manages callable objects and provides collision detection functionality.
 */
public class GameEnvironment {
    private final List<Collidable> collides;

    /**
     * Constructs a new GameEnvironment object with an empty list of collides.
     */
    public GameEnvironment() {
        this.collides = new ArrayList<>();
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.collides.add(c);
    }

    /**
     * Finds the closest collision point between a trajectory and any collidable object.
     *
     * @param trajectory the trajectory (line) for which to find collisions
     * @return information about the closest collision point and collidable object, or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.MAX_VALUE;

        // Iterate through all collidables to find the closest collision
        for (Collidable c : this.collides) {
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());

            // Check if a collision point exists
            if (collisionPoint != null) {
                double distance = trajectory.start().distance(collisionPoint);

                // Update the closest collision if this one is closer
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(collisionPoint, c);
                }
            }
        }
        return closestCollision; // Return the closest collision found
    }
}
