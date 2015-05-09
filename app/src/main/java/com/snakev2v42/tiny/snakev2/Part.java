package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Part {
    public enum Direction {
        LEFT {
            @Override
            public Direction inverse() {
                return RIGHT;
            }
        }, RIGHT {
            @Override
            public Direction inverse() {
                return LEFT;
            }
        };

        abstract public Direction inverse();
    }

    public Direction dir;
    public Point p;
    public Vector vec;
    public Vector oldVec;

    public Part(Point p, Vector vec, Direction dir) {
        this.p = p;
        this.oldVec = this.vec = vec;
        this.dir = dir;
    }

    public Part(Point p, Vector vec) {
        this(p, vec, Direction.LEFT);
    }

    public Part(int x, int y, Vector vec) {
        this(new Point(x, y), vec);
    }

    public Part(int x, int y, Vector vec, Direction dir) {
        this(new Point(x, y), vec, dir);
    }

    public void move() {
        p.plus(vec);
        if (vec != oldVec.turn(1) && vec != oldVec.turn(-1))
            dir = dir.inverse();

        oldVec = vec;
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
