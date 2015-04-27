package com.snakev2v42.tiny.snakev2;

/**
 * Created by user on 27.04.2015.
 */
public enum Vector {
    NORTH(0, -1), EAST (1, 0), SOUTH(0, 1), WEST (-1, 0);
/*public class Vector {

    public final static Vector NORTH = new Vector(0, -1),
                               EAST  = new Vector(1, 0),
                               SOUTH = new Vector(0, 1),
                               WEST  = new Vector(-1, 0);*/

    public int x, y;

    Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the inverse of a vector
     * @param vector the vector to inverse
     */
    public static Vector inverse(Vector vector) {
        switch (vector) {
            case NORTH: return SOUTH;
            case EAST: return WEST;
            case SOUTH: return NORTH;
            case WEST: return EAST;
        }
        return null;
    }
}
