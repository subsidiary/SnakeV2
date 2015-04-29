package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Color;

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
        for (int i = length-1; i > 0; --i) {
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
    public void PaintSnake(boolean fast) {
        int j;
        for(int i=length;i>=0;--i){
            if((fast && (i==0 || i==1 || i==length-1 || i==length)) || !fast) {
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
                            j = 6;
                            break;
                        case EAST:
                            j = 7;
                            break;
                        case SOUTH:
                            j = 4;
                            break;
                        case WEST:
                            j = 5;
                            break;
                        default:
                            j = 6;
                    }
                else if (i == length)//It is a black square
                    j = 14;
                else
                    switch (parts.get(i).vec) {
                        case NORTH:
                            switch (parts.get(i + 1).vec) {
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
                            switch (parts.get(i + 1).vec) {
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
                            switch (parts.get(i + 1).vec) {
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
                            switch (parts.get(i + 1).vec) {
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

                Bitmaps.DrawToMainB(bitmaps[j], Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y);
            }
        }
    }

    public Part head(){
        return parts.get(0);
    }
}
