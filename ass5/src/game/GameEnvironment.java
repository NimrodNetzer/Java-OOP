// 322394545 Nimrod Netzer
package game;

import geometry.Collidable;
import geometry.CollisionInfo;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment class manages collidable objects and provides collision detection functionality.
 */
public class GameEnvironment {
    private final List<Collidable> collides;

    /**
     * Constructs a new GameEnvironment object with an empty list of collidables.
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
     * Removes a collidable object from the game environment.
     *
     * @param collidable the collidable object to remove
     */
    public void removeCollidable(Collidable collidable) {
        this.collides.remove(collidable);
    }

    /**
     * Finds the closest collision point between a trajectory and any collidable object in the environment.
     *
     * @param trajectory the trajectory (line) to check for collisions
     * @return information about the closest collision point and the collidable object, or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.MAX_VALUE;

        // Iterate through all collidables to find the closest collision
        for (Collidable c : this.collides) {
            // Find the closest intersection point of the trajectory with the collidable's collision rectangle
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());

            // Check if a collision point exists
            if (collisionPoint != null) {
                // Calculate the distance from the start of the trajectory to the collision point
                double distance = trajectory.start().distance(collisionPoint);

                // Update the closest collision if this one is closer
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(collisionPoint, c);
                }
            }
        }
        // Return the closest collision found, or null if no collision was detected
        return closestCollision;
    }
}
