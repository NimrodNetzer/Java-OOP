// 322394545 Nimrod Netzer
package geometry;

/**
 * A class to represent a point in a 2D plane.
 */
@SuppressWarnings("ClassCanBeRecord")
public class Point {
    // x and y coordinates of the point
    private final double x;
    private final double y;

    /**
     * Constructs a new geometry.Point with the given x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        // distance -- return the distance of this point to the other point
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param other The other point.
     * @return The distance between this point and the other point.
     */
    public double distance(Point other) {
        double dx = other.getX() - this.x;
        double dy = other.getY() - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    /**
     * Gets the x-coordinate of this point.
     *
     * @return The x-coordinate of this point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of this point.
     *
     * @return The y-coordinate of this point.
     */
    public double getY() {
        return this.y;
    }
}

