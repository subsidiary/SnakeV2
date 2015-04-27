package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Part {
    /**
     * Position of this part
     */
    public Point p;

    /**
     * Direction of this part
     */
    public Vector vec;

    public Part(Point p, Vector vec) {
        this.p = p;
        this.vec = vec;
    }

    public Part(int x, int y, Vector vec) {
        new Part(new Point(x, y), vec);
    }

    /**
     * Moves this part of snake
     */
    public void move() {
        p.plus(vec);
        outOfBounds();
    }

    /**
     * Checks if part is out of game field bounds,
     * and if it is so then returns it to game field
     */
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
