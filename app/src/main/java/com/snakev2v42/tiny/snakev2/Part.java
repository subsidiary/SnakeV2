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
        new Part(new Point(x, y), vec);
    }

    public void move() {
        p.plus(vec);
        outOfBounds();
    }


    public void outOfBounds() {
        if (p.x < 0){
            p.x += Values.CellWidth;
            outOfBounds();
        }
        if (p.x >= Values.CellWidth){
            p.x -= Values.CellWidth;
            outOfBounds();
        }
        if (p.y < 0){
            p.y += Values.CellHeight;
            outOfBounds();
        }
        if (p.y >= Values.CellHeight){
            p.y -= Values.CellHeight;
            outOfBounds();
        }
    }
}
