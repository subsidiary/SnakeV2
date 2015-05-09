package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Snake {
    private final Bitmap bitmaps[];
    private Picture body;
    private Picture bigCorner;
    private Picture smallCorner;
    public int length;
    public ArrayList<Part> parts;
    public boolean broken;
    private int r, l, w;

    public Snake(int startX, int startY, Vector vector, int length, int color, int cellSize, int width, int headRadius) {
        l = cellSize;
        r = headRadius;
        w = width;
        initPics();
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
            parts.add(new Part(tail.p.x, tail.p.y, tail.vec, tail.dir.inverse()));
            //Update tail part
            tail = parts.get(length++);
            //Move tail part
            tail.p.plus(Vector.inverse(tail.vec));
        }
    }

    private void initPics() {
        int x, y;
        body = new Picture();
        Canvas c = body.beginRecording(l, l);
        Paint p = new Paint();

        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
        c.clipRect(0, 0, l, l);
        c.drawRect(0, 0, l, l, p);
        c.translate(-2 * l, -2 * l);

        p.setColor(Color.RED);

        x = (int) Math.round(((3 + Math.sqrt(3)) * l - w) / 2);
        y = 2 * l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 180, 30, true, p);

        x = (int) Math.round(((3 - Math.sqrt(3)) * l + w) / 2);
        y = l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 0, 30, true, p);

        //x = (int) Math.round(3 * l - Math.sqrt(3) / 2 * l + (Math.sqrt(3) - 1) * l / 2 - w / 2);
        x = (int) Math.round((5 * l - w) / 2);
        y = 2 * l;
        c.drawRect(x, y, x + w, y + l, p);

        p.setColor(Color.BLACK);
        x = (int) Math.round(((3 + Math.sqrt(3)) * l + w) / 2);
        y = 2 * l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 180, 30, true, p);

        x = (int) Math.round(((3 - Math.sqrt(3)) * l - w) / 2);
        y = l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 0, 30, true, p);

//        p.setColor(Color.BLUE);
//        p.setStyle(Paint.Style.STROKE);
//        x = (int) Math.round((3 - Math.sqrt(3)) * l / 2);
//        y = l;
//        c.drawArc(x, y, x + 2 * l, y + 2 * l, 0, 30, false, p);
//
//        x = (int) Math.round((3 + Math.sqrt(3)) * l / 2);
//        y = 2 * l;
//        c.drawArc(x, y, x + 2 * l, y + 2 * l, 180, 30, false, p);

        body.endRecording();


        bigCorner = new Picture();
        c = bigCorner.beginRecording(l, l);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
        c.clipRect(0, 0, l, l);
        c.drawRect(0, 0, l, l, p);
        c.rotate(-90, l * 0.5f, l * 0.5f);

        int t = (int) Math.round(((3 - Math.sqrt(3)) * l + w) / 2);
        p.setColor(Color.RED);
        c.drawArc(l - t, l - t, l + t, l + t, 180, 90, true, p);
        p.setColor(Color.BLACK);
        c.drawArc(l - t + w, l - t + w, l + t - w, l + t - w, 180, 90, true, p);
        bigCorner.endRecording();


        smallCorner = new Picture();
        c = smallCorner.beginRecording(l, l);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
        c.clipRect(0, 0, l, l);
        c.drawRect(0, 0, l, l, p);
        c.rotate(-90, l * 0.5f, l * 0.5f);

        p.setColor(Color.RED);
        c.drawArc(t - w, t - w, 2 * l + w - t, 2 * l + w - t, 180, 90, true, p);
        p.setColor(Color.BLACK);
        c.drawArc(t, t, 2 * l - t, 2 * l - t, 180, 90, true, p);
        smallCorner.endRecording();
    }


    public void draw(Canvas canvas, int k) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        //canvas.drawCircle(l * head().p.x + r, l * head().p.y + r, r, paint);
        /*switch (head().vec) {
            case NORTH:
        }*/
        int j, i, x, y;


        // canvas.translate(l * head().p.x + r, l * head().p.y + r);
        canvas.save();
        canvas.rotate(90, 3 * l / 2, 3 * l / 2);
        canvas.scale(-1, 1, 3 * l / 2, 3 * l / 2);
        canvas.translate(l, l);
        canvas.drawPicture(body);

//        Path path = new Path();
//        path.cubicTo();

        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.FILL);

        canvas.translate(-2 * l, -2 * l);
        if (k < 5) {
            x = (int) Math.round((5 + Math.sqrt(3)) * l / 2);
            y = 3 * l;
            double alpha = Math.PI - Math.PI / 30 * k;
            canvas.drawCircle(Math.round(x + Math.cos(alpha) * l), Math.round(y - Math.sin(alpha) * l), r, p);
        } else {
            x = (int) Math.round((5 - Math.sqrt(3)) * l / 2);
            y = 2 * l;
            double alpha = Math.PI / 30 * (k - 5) - Math.PI / 6;
            canvas.drawCircle(Math.round(x + Math.cos(alpha) * l), Math.round(y - Math.sin(alpha) * l), r, p);
        }
        canvas.restore();


        canvas.save();
        canvas.translate(2 * l, l);
        canvas.drawPicture(body);
        canvas.translate(l, 0);
        canvas.drawPicture(bigCorner);
        canvas.translate(l, 0);
        canvas.drawPicture(smallCorner);
        canvas.restore();
        //
        // canvas.translate(-l * head().p.x - r, -l * head().p.y - r);

        for (Part part : parts) {
            canvas.save();
            canvas.translate(part.p.x * l, part.p.y * l);
            canvas.rotate(part.vec.angle, l * 0.5f, l * 0.5f);
            if (part.oldVec == part.vec) {
                if (part.dir == Part.Direction.LEFT)
                    canvas.scale(-1, 1, l * 0.5f, l * 0.5f);
                if (part.equals(head()))
                    canvas.clipRect(0, l * (10 - k) / 10, l, l);
                if (part.equals(tail()))
                    canvas.clipRect(0, 0, l, l * (10 - k) / 10);
                //Log.d("k", String.valueOf(k));
                canvas.drawPicture(body);
            } else {
                if (part.vec.turn(1) == part.oldVec)
                    canvas.scale(-1, 1, l * 0.5f, l * 0.5f);
                if (part.equals(head())) {
                    Path path = new Path();
                    path.moveTo(l, 0);
                    path.arcTo(0, -l, 2 * l, l, 90, 9 * k, false);
                    path.close();
                    canvas.clipPath(path);
                }
                if (part.equals(tail())) {
                    Path path = new Path();
                    path.moveTo(l, 0);
                    path.arcTo(0, -l, 2 * l, l, 90 + 9 * k, 9 * (10 - k), false);
                    path.close();
                    canvas.clipPath(path);
                }
                if (part.vec.turn(1) == part.oldVec && part.dir == Part.Direction.LEFT || part.vec.turn(-1) == part.oldVec && part.dir == Part.Direction.RIGHT)
                    canvas.drawPicture(bigCorner);
                else canvas.drawPicture(smallCorner);
            }
            canvas.restore();
        }

/*GRID*/
//        p.setColor(Color.BLUE);
//        for (int h = 0; h < Values.Width; h += l)
//            canvas.drawLine(h, 0, h, Values.Height, p);
//        for (int h = 0; h < Values.Height; h += l)
//            canvas.drawLine(0, h, Values.Width, h, p);
/*GRID*/

        /*for (i = 0; i < length; ++i) {
            x = parts.get(i).p.x;
            y = parts.get(i).p.y;
            canvas.save();
            canvas.translate(x * l, y * l);
            if ((x & 1) == 0) {
                canvas.scale(-1, 1, l * 0.5f, l * 0.5f);
            }
            if ((y & 1) == 0) {
                canvas.scale(1, -1, l * 0.5f, l * 0.5f);
            }
            if (parts.get(i).vec == Vector.EAST || parts.get(i).vec == Vector.WEST)
                canvas.rotate(90, l * 0.5f, l * 0.5f);
            canvas.drawPicture(body);
            canvas.restore();
        }*/

//        x = 0;
//        y = 0;
//        i = length - 1;
//        switch (parts.get(i).vec) {
//            case NORTH:
//                y = -k;
//                break;
//            case EAST:
//                x = k;
//                break;
//            case SOUTH:
//                y = k;
//                break;
//            case WEST:
//                x = -k;
//                break;
//        }
//        j = parts.get(i).tail();
//        //canvas.drawBitmap(bitmaps[9 - (j & 1)], Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y, null);
//        c.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i).p.x + Values.SnakeSize * x / 10, Values.SnakeSize * parts.get(i).p.y + Values.SnakeSize * y / 10, null);
//                /*if (corner) {
//                    j = parts.get(i - 1).body(parts.get(i).vec);
//                    canvas.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i - 1).p.x, Values.SnakeSize * parts.get(i - 1).p.y, null);
//                }*/
//
//        for (i = 1; i < length - 1; ++i) {
//            j = parts.get(i).body(parts.get(i + 1).vec);
//            if (j != 8 && j != 9)
//                c.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y, null);
//        }
//
//
//        i = 0;
//        x = 0;
//        y = 0;
//        switch (parts.get(i).vec) {
//            case NORTH:
//                y = -k + 10;
//                break;
//            case EAST:
//                x = k - 10;
//                break;
//            case SOUTH:
//                y = k - 10;
//                break;
//            case WEST:
//                x = -k + 10;
//                break;
//        }
//        j = parts.get(i).head();
//        //canvas.drawBitmap(bitmaps[9 - (j & 1)], Values.SnakeSize * parts.get(i).p.x, Values.SnakeSize * parts.get(i).p.y, null);
//        c.drawBitmap(bitmaps[j], Values.SnakeSize * parts.get(i).p.x + Values.SnakeSize * x / 10, Values.SnakeSize * parts.get(i).p.y + Values.SnakeSize * y / 10, null);

    }

    public Part head() {
        return parts.get(0);
    }

    public Part tail() {
        return parts.get(length - 1);
    }

    public void turn(double direction) {
        head().vec = head().vec.turn(direction);
    }
}
