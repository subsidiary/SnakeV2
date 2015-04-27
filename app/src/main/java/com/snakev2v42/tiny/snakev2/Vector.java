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
    public int VectorToInt(Vector vector){
        int vecID;
        switch (vector){
            case NORTH:vecID=1;break;
            case SOUTH:vecID=3;break;
            case EAST :vecID=2;break;
            case WEST :vecID=4;break;
            default   :vecID=1;
        }
        return vecID;
    }
}
