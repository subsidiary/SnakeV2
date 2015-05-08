package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Snake {
    private final Bitmap bitmaps[];
    public int length;
    public ArrayList<Part> parts;
    public boolean broken;

    public Snake(int startX, int startY, Vector vector, int length, int color) {
        parts = new ArrayList<>();
        //Add head
        parts.add(new Part(startX, startY, vector));
        this.broken = false;
        this.length = 1;
        grow(length);

        bitmaps = new Bitmap[15];
        for (int i = 0; i < bitmaps.length; ++i) {
            bitmaps[i] = Bitmap.createBitmap(Bitmaps.SnakeParts[i]);
            for (int ux = 0; ux < bitmaps[i].getWidth(); ux++)
                for (int uy = 0; uy < bitmaps[i].getHeight(); uy++) {
                    if (bitmaps[i].getPixel(ux, uy) == Color.parseColor("#f5f5f5"))
                        bitmaps[i].setPixel(ux, uy, color);
                }
        }
        //PaintSnake(false);
    }

    /**
     * Moves every {@code Part} of this {@code Snake} into next cell
     */
    public void move() {
        for (int i = length - 1; i > 0; --i) {
            Part part = parts.get(i);
            part.move();
            part.vec = parts.get(i - 1).vec;
        }
        parts.get(0).move();
    }

    /**
     * Increases the length of the snake on the <i>n</i> parts
     *
     * @param n number of parts to add
     */
    public void grow(int n) {
        while (n-- > 0) {
            Part tail = parts.get(length - 1);
            //Add new part
            parts.add(new Part(tail.p.x, tail.p.y, tail.vec));
            //Update tail part
            tail = parts.get(length++);
            //Move tail part
            tail.p.plus(Vector.inverse(tail.vec));
        }
    }

    //GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS
//    public void PaintSnake(boolean fast) {
    public void draw(Canvas canvas, int k) {
        int j;
        for (int i = 1; i < length - 1; ++i) {
            j = parts.get(i).body(parts.get(i + 1).vec);
            canvas.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y, null);
        }
        int x = 0, y = 0, i = 0;
        switch (parts.get(i).vec) {
            case NORTH:
                y = -k + 10;
                break;
            case EAST:
                x = k - 10;
                break;
            case SOUTH:
                y = k - 10;
                break;
            case WEST:
                x = -k + 10;
                break;
        }
        j = parts.get(i).head();
        //canvas.drawBitmap(bitmaps[9 - (j & 1)], Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y, null);
        canvas.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i).p.x + Values.SnakeSize * x / 10, Values.SnakeSize * parts.get(i).p.y + Values.SnakeSize  * y / 10, null);

        x = 0; y = 0;
        i = length - 1;
        switch (parts.get(i).vec) {
            case NORTH:
                y = -k;
                break;
            case EAST:
                x = k;
                break;
            case SOUTH:
                y = k;
                break;
            case WEST:
                x = -k;
                break;
        }
        j = parts.get(i).tail();
        //canvas.drawBitmap(bitmaps[9 - (j & 1)], Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y, null);
        canvas.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i).p.x + Values.SnakeSize * x / 10, Values.SnakeSize * parts.get(i).p.y + Values.SnakeSize * y / 10, null);
                /*if (corner) {
                    j = parts.get(i - 1).body(parts.get(i).vec);
                    canvas.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i - 1).p.x, Values.SnakeSize * parts.get(i - 1).p.y, null);
                }*/
    }

    public Part head() {
        return parts.get(0);
    }

    public void turn(double direction) {
        head().vec = head().vec.turn(direction);
    }
}
