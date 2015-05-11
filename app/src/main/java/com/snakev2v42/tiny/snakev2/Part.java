package com.snakev2v42.tiny.snakev2;

import android.graphics.Matrix;
import android.graphics.Path;

import java.util.Random;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Part {
    private final float size;

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
    public Direction pointDir;
    public Point p;
    public Vector vec;
    public Vector oldVec;
    public Path path;

    public Part(Point p, Vector vec, Direction dir, Direction pointDir, float size) {
        this.p = p;
        this.oldVec = this.vec = vec;
        this.dir = dir;
        this.pointDir = pointDir;
        this.size = size;
        calcPath();
        //this.pointDir = GameActivity.random.nextInt(2) == 0 ? Direction.LEFT : Direction.RIGHT;
    }

    public Part(Point p, Vector vec, float size) {
        this(p, vec, Direction.LEFT, Direction.LEFT, size);
    }

    public Part(int x, int y, Vector vec, float size) {
        this(new Point(x, y), vec, size);
    }

    public Part(int x, int y, Vector vec, Direction dir, Direction pointDir, float size) {
        this(new Point(x, y), vec, dir, pointDir, size);
    }

    public void move(Vector vec) {
        p.plus(this.vec);
        oldVec = this.vec;
        this.vec = vec;
        calcPath();
    }

    public void calcPath() {
        if (oldVec == vec) {
            path = new Path(Snake.bodyPath);
        } else {
            if (vec.turn(1) == oldVec && dir == Part.Direction.LEFT || vec.turn(-1) == oldVec && dir == Part.Direction.RIGHT) {
                path = new Path(Snake.smallCornerPath);
            } else {
                path = new Path(Snake.bigCornerPath);
            }
        }
        Matrix matrix = new Matrix();
        if (oldVec == vec && dir == Part.Direction.LEFT || vec.turn(1) == oldVec)
            matrix.preScale(-1, 1, size * 0.5f, size * 0.5f);
        matrix.postRotate(vec.angle, size * 0.5f, size * 0.5f);
        path.transform(matrix);
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
