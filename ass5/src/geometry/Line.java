// 322394545 Nimrod Netzer
package geometry;

import java.util.List;

/**
 * A class to represent a line segment between two points.
 */
public class Line {
    private final Point start;
    private final Point end;

    /**
     * Constructs a line segment using two points.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line segment using coordinates.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the start point of the line.
     *
     * @return The starting point of the line.
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return The ending point of the line.
     */
    public Point end() {
        return end;
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other The other line to find the intersection with.
     * @return The intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();
        double x3 = other.start().getX();
        double y3 = other.start().getY();
        double x4 = other.end().getX();
        double y4 = other.end().getY();

        double denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

        // Lines are parallel or coincident
        if (denominator == 0) {
            return null;
        }

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        // Intersection point is not within the line segments
        if (ua < 0 || ua > 1 || ub < 0 || ub > 1) {
            return null;
        }

        double x = x1 + ua * (x2 - x1);
        double y = y1 + ua * (y2 - y1);

        return new Point(x, y);
    }

    /**
     * Returns the closest intersection point between the line and the given rectangle to the start of the line.
     * If there are no intersection points, returns null.
     *
     * @param rect The rectangle to find intersection points with.
     * @return The closest intersection point to the start of the line, or null if no intersections.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }
        Point closestPoint = intersections.get(0);
        double minDistance = start.distance(closestPoint);

        for (Point intersection : intersections) {
            double distance = start.distance(intersection);
            if (distance < minDistance) {
                closestPoint = intersection;
                minDistance = distance;
            }
        }

        return closestPoint;
    }
}
