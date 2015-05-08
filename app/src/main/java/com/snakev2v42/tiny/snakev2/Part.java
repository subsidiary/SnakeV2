package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Part {
    public Point p;

    public Vector vec;

    public Part(Point p, Vector vec) {
        this.p = p;
        this.vec = vec;
    }

    public Part(int x, int y, Vector vec) {
        this(new Point(x, y), vec);
    }

    public void move() {
        p.plus(vec);
    }

    public int head() {
        switch (vec) {
            case NORTH:
                return 0;
            case EAST:
                return 1;
            case SOUTH:
                return 2;
            case WEST:
                return 3;
            default:
                return 0;
        }
    }

    public int tail() {
        switch (vec) {
            case NORTH:
                return 6;
            case EAST:
                return 7;
            case SOUTH:
                return 4;
            case WEST:
                return 5;
            default:
                return 6;
        }
    }

    public int body(Vector before) {
        switch (vec) {
            case NORTH:
                switch (before) {
                    case NORTH:
                        return 9;
                    case EAST:
                        return 13;
                    case WEST:
                        return 10;
                    default:
                        return 9;
                }

            case EAST:
                switch (before) {
                    case NORTH:
                        return 11;
                    case EAST:
                        return 8;
                    case SOUTH:
                        return 10;
                    default:
                        return 8;
                }

            case SOUTH:
                switch (before) {
                    case EAST:
                        return 12;
                    case SOUTH:
                        return 9;
                    case WEST:
                        return 11;
                    default:
                        return 9;
                }

            case WEST:
                switch (before) {
                    case NORTH:
                        return 12;
                    case SOUTH:
                        return 13;
                    case WEST:
                        return 8;
                    default:
                        return 8;
                }

            default:
                return 10;
        }
    }
}
