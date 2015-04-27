package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Snake {
    public int length;
    private int color;
    public ArrayList<Part> parts;

    public Snake(int startX, int startY, Vector vector, int length, int color) {
        parts = new ArrayList<>();
        //Add head
        parts.add(new Part(startX, startY, vector));
        this.length = 1;
        grow(length - 1);
        /*for (int i = 0; i <= length; i++)
            parts.add(new Part(startX + vector.x * i, startY + vector.y * i, vector));*/
        this.color = color;
    }

    /**
     * Moves every {@code Part} of this {@code Snake} into next cell
     */
    public void move() {
        for (int i = length; i > 0; --i) {
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
            Part tail = parts.get(parts.size() - 1);
            //Add new part
            parts.add(new Part(tail.p, tail.vec));
            //Update tail part
            tail = parts.get(parts.size() - 1);
            //Move tail part
            tail.p.plus(Vector.inverse(tail.vec));
            //Check if tail is out of bounds
            tail.outOfBounds();
            //Increase length :)    (so informative)
            ++length;
        }
    }


    //GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS
    public void PaintSnake() {
        Bitmap b;
        int j;
        for (int i = 0; i < parts.size(); ++i) {
            if (i == 0)
                switch (parts.get(i).vec) {
                    case NORTH:
                        j = 0;
                        break;
                    case EAST:
                        j = 1;
                        break;
                    case SOUTH:
                        j = 2;
                        break;
                    case WEST:
                        j = 3;
                        break;
                    default:
                        j = 0;
                }
            else if (i == length - 1)
                switch (parts.get(i).vec) {
                    case NORTH:
                        j = 4;
                        break;
                    case EAST:
                        j = 5;
                        break;
                    case SOUTH:
                        j = 6;
                        break;
                    case WEST:
                        j = 7;
                        break;
                    default:
                        j = 4;
                }
            else
                switch (parts.get(i).vec) {
                    case NORTH:
                        switch (parts.get(i - 1).vec) {
                            case NORTH:
                                j = 9;
                                break;
                            case EAST:
                                j = 13;
                                break;
                            case WEST:
                                j = 10;
                                break;
                            default:
                                j = 9;
                        }
                        break;
                    case EAST:
                        switch (parts.get(i - 1).vec) {
                            case NORTH:
                                j = 11;
                                break;
                            case EAST:
                                j = 8;
                                break;
                            case SOUTH:
                                j = 10;
                                break;
                            default:
                                j = 8;
                        }
                        break;
                    case SOUTH:
                        switch (parts.get(i - 1).vec) {
                            case EAST:
                                j = 12;
                                break;
                            case SOUTH:
                                j = 9;
                                break;
                            case WEST:
                                j = 11;
                                break;
                            default:
                                j = 9;
                        }
                        break;
                    case WEST:
                        switch (parts.get(i - 1).vec) {
                            case NORTH:
                                j = 12;
                                break;
                            case SOUTH:
                                j = 13;
                                break;
                            case WEST:
                                j = 8;
                                break;
                            default:
                                j = 8;
                        }
                        break;
                    default:
                        j = 10;
                }

            b = Bitmap.createBitmap(Bitmaps.SnakeParts[j]);
            for (int ux = 0; ux < b.getWidth(); ux++)
                for (int uy = 0; uy < b.getHeight(); uy++) {
                    if (b.getPixel(ux, uy) == Color.parseColor("#f5f5f5"))
                        b.setPixel(ux, uy, color);
                }

            Bitmaps.DrawToMainB(b, Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y);
            b.recycle();
        }
    }
}
