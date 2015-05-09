package com.snakev2v42.tiny.snakev2;

/**
 * Created by user on 27.04.2015.
 */
public enum Vector {
    NORTH(0), EAST(90), SOUTH(180), WEST(270);

    public final int angle;

    Vector(int angle) {
        this.angle = angle;
    }

    public Vector turn(double direction) {
        if (direction > 0)
            return IntToVector(VectorToInt(this) % 4 + 1);
        else if (direction < 0)
            return IntToVector(Point.mod(VectorToInt(this) - 2, 4) + 1);
        else return this;
    }


    public static Vector inverse(Vector vector) {
        switch (vector) {
            case NORTH: return SOUTH;
            case EAST: return WEST;
            case SOUTH: return NORTH;
            case WEST: return EAST;
        }
        return null;
    }

    public static Vector IntToVector(int vecID){
        Vector vector;
        switch (vecID){
            case 1 :vector=NORTH;break;
            case 2 :vector=EAST; break;
            case 3 :vector=SOUTH;break;
            case 4 :vector=WEST; break;
            default:vector = NORTH;
        }
        return vector;
    }

    public static int VectorToInt(Vector vector){
        int vecID;
        switch (vector){
            case NORTH :vecID=1; break;
            case EAST  :vecID=2; break;
            case SOUTH :vecID=3; break;
            case WEST  :vecID=4; break;
            default    :vecID=1;
        }
        return vecID;
    }
}
