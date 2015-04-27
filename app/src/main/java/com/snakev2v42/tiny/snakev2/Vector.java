package com.snakev2v42.tiny.snakev2;

/**
 * Created by user on 27.04.2015.
 */
public enum Vector {
    NORTH, EAST, SOUTH, WEST;


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
