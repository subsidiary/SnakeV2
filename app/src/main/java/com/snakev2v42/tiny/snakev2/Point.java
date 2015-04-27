package com.snakev2v42.tiny.snakev2;

/**
 * Created by user on 27.04.2015.
 */
public class Point {
    public int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the specified vector to this point
     * @param vector the vector to add
     */
    public void plus(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    /**
     * Returns a new point which equals to sum of a specified point and vector
     * @param vector the vector to add
     */
    public static Point plus(Point point, Vector vector) {
        Point pt = new Point(point.x, point.y);
        pt.plus(vector);
        return pt;
    }
}
