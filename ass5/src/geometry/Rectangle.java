// 322394545 Nimrod Netzer
package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a rectangle defined by an upper-left corner point, width, and height.
 */
@SuppressWarnings("ClassCanBeRecord")
public class Rectangle {
    private final Point upperLeft;
    private final double width;
    private final double height;

    /**
     * Constructs a new geometry.Rectangle with the given upper-left corner point, width, and height.
     *
     * @param upperLeft The upper-left corner point of the rectangle.
     * @param width     The width of the rectangle.
     * @param height    The height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the upper-left corner point of the rectangle.
     *
     * @return The upper-left corner point.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return The width of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return The height of the rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns a list of intersection points between the rectangle and the given line.
     *
     * @param line The line to check for intersections with the rectangle.
     * @return A (possibly empty) list of intersection points.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();

        // Define the four corners of the rectangle
        Point upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        Point lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        Point lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);

        // Define the four edges of the rectangle as lines
        Line topEdge = new Line(upperLeft, upperRight);
        Line bottomEdge = new Line(lowerLeft, lowerRight);
        Line leftEdge = new Line(upperLeft, lowerLeft);
        Line rightEdge = new Line(upperRight, lowerRight);

        // Check for intersections with each edge and add to the list if found
        Point intersection;

        intersection = line.intersectionWith(topEdge);
        if (intersection != null) {
            intersections.add(intersection);
        }

        intersection = line.intersectionWith(bottomEdge);
        if (intersection != null) {
            intersections.add(intersection);
        }

        intersection = line.intersectionWith(leftEdge);
        if (intersection != null) {
            intersections.add(intersection);
        }

        intersection = line.intersectionWith(rightEdge);
        if (intersection != null) {
            intersections.add(intersection);
        }
        return intersections;
    }
}
