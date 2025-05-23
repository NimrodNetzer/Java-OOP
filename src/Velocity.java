// 322394545 Nimrod Netzer


/**
 * A class to represent a velocity in 2D space.
 */
@SuppressWarnings("ClassCanBeRecord")
public class Velocity {
    private final double dx; // The change in position along the x-axis
    private final double dy; // The change in position along the y-axis

    /**
     * Constructs a new Velocity with the given change in position along the x-axis and y-axis.
     *
     * @param dx The change in position along the x-axis.
     * @param dy The change in position along the y-axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets the change in position along the x-axis.
     *
     * @return The change in position along the x-axis.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Gets the change in position along the y-axis.
     *
     * @return The change in position along the y-axis.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Creates a new Velocity from an angle and speed.
     *
     * @param angle The angle in degrees.
     * @param speed The speed of the velocity.
     * @return A new Velocity object based on the given angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radAngle = Math.toRadians(angle); // Convert angle to radians
        double dx = speed * Math.cos(radAngle); // Calculate change in position along x-axis
        double dy = speed * Math.sin(radAngle); // Calculate change in position along y-axis
        return new Velocity(dx, dy);
    }

    /**
     * Applies this velocity to a given point, moving it by dx and dy.
     *
     * @param p The point to which the velocity should be applied.
     * @return A new point after applying the velocity.
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx;
        double newY = p.getY() + this.dy;
        return new Point(newX, newY);
    }

    /**
     * Calculates and returns the speed of the object based on its velocity components.
     *
     * @return the speed, calculated as the Euclidean norm of the velocity components (dx and dy).
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }

}
