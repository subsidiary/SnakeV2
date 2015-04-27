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
    //That is more simple for me.Don't ask me to return everything :)
    public void plus(Vector vector) {
        switch (vector){
            case NORTH: --this.y;break;
            case SOUTH: ++this.y;break;
            case EAST : ++this.x;break;
            case WEST : --this.x;break;
        }
    }

    public static Point plus(Point point, Vector vector) {
        Point pt = new Point(point.x, point.y);
        pt.plus(vector);
        return pt;
    }
}
