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
}
