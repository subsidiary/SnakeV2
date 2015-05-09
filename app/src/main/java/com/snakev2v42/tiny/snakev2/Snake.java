package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Snake {
    private final int color, bgColor, eyeColor;
    private final Bitmap bitmaps[];
    private Picture head, body, bigCorner, smallCorner;
    public int length;
    public ArrayList<Part> parts;
    public boolean broken;
    private float eyeR, eyeDist, headR, l, w;
    private Path bodyPath, bigCornerPath, smallCornerPath;

    public Snake(int startX, int startY, Vector vector, int length, int color, int bgColor, int eyeColor, float cellSize) {
        this(startX, startY, vector, length, color, bgColor, eyeColor, cellSize, cellSize / 2, cellSize / 3, cellSize / 12, cellSize / 7);
    }

    public Snake(int startX, int startY, Vector vector, int length, int color, int bgColor, int eyeColor, float cellSize, float width, float headRadius, float eyeRadius, float eyeDist) {
        l = cellSize;
        headR = headRadius;
        eyeR = eyeRadius;
        this.eyeDist = eyeDist;
        this.color = color;
        this.bgColor = bgColor;
        this.eyeColor = eyeColor;
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
        float x, y;
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
        Canvas c;


        body = new Picture();
        c = body.beginRecording((int) l, (int) l);
        c.clipRect(0, 0, l, l);
        c.translate(-2 * l, -2 * l);

        p.setColor(color);
        x = (float) (((3 + Math.sqrt(3)) * l - w) / 2);
        y = 2 * l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 180, 30, true, p);

        x = (float) (((3 - Math.sqrt(3)) * l + w) / 2);
        y = l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 0, 30, true, p);

        x = (float) ((5 * l - w) / 2);
        y = 2 * l;
        c.drawRect(x, y, x + w, y + l, p);

        p.setColor(bgColor);
        x = (float) (((3 + Math.sqrt(3)) * l + w) / 2);
        y = 2 * l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 180, 30, true, p);

        x = (float) (((3 - Math.sqrt(3)) * l - w) / 2);
        y = l;
        c.drawArc(x, y, x + 2 * l, y + 2 * l, 0, 30, true, p);

        body.endRecording();


        bodyPath = new Path();
        x = (float) ((Math.sqrt(3) - 1) * l / 2);
        y = 0;
        bodyPath.arcTo(x, y, x + 2 * l, y + 2 * l, 180, 30, false);

        x = (float) ((-1 - Math.sqrt(3)) * l / 2);
        y = -l;
        bodyPath.arcTo(x, y, x + 2 * l, y + 2 * l, 30, -30, false);


        head = new Picture();
        c = head.beginRecording((int) (2 * headR), (int) (2 * headR));

        p.setColor(color);
        c.drawCircle(headR, headR, headR, p);
        p.setColor(eyeColor);
        c.drawCircle(headR + eyeDist, headR + eyeDist, eyeR, p);
        c.drawCircle(headR + eyeDist, headR - eyeDist, eyeR, p);

        head.endRecording();


        bigCorner = new Picture();
        c = bigCorner.beginRecording((int) l, (int) l);
        c.clipRect(0, 0, l, l);
        c.rotate(-90, l * 0.5f, l * 0.5f);

        float t = (float) (((3 - Math.sqrt(3)) * l + w) / 2);
        p.setColor(color);
        c.drawArc(l - t, l - t, l + t, l + t, 180, 90, true, p);
        p.setColor(bgColor);
        c.drawArc(l - t + w, l - t + w, l + t - w, l + t - w, 180, 90, true, p);
        bigCorner.endRecording();


        bigCornerPath = new Path();
        bigCornerPath.arcTo(l - t + w * 0.5f, - t + w * 0.5f, l + t - w * 0.5f, t - w * 0.5f, 90, 90, false);


        smallCorner = new Picture();
        c = smallCorner.beginRecording((int) l, (int) l);
        c.clipRect(0, 0, l, l);
        c.rotate(-90, l * 0.5f, l * 0.5f);

        p.setColor(color);
        c.drawArc(t - w, t - w, 2 * l + w - t, 2 * l + w - t, 180, 90, true, p);
        p.setColor(bgColor);
        c.drawArc(t, t, 2 * l - t, 2 * l - t, 180, 90, true, p);
        smallCorner.endRecording();


        smallCornerPath = new Path();
        smallCornerPath.arcTo(t - w * 0.5f, t - l - w * 0.5f , 2 * l - t + w * 0.5f, l - t + w * 0.5f, 90, 90, false);
    }


    public void draw(Canvas canvas, int k) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        float x, y;

        for (int h = length - 1; h >= 0; --h) {
            Path path;
            Part part = parts.get(h);
            canvas.save();
            canvas.translate(part.p.x * l, part.p.y * l);
            canvas.rotate(part.vec.angle, l * 0.5f, l * 0.5f);
            if (part.oldVec == part.vec && part.dir == Part.Direction.LEFT || part.vec.turn(1) == part.oldVec)
                canvas.scale(-1, 1, l * 0.5f, l * 0.5f);

            if (part.oldVec == part.vec) {
                canvas.save();
                if (part.equals(head())) {
                    canvas.clipRect(0, l * (10 - k) / 10, l, l);
                }
                if (part.equals(tail()))
                    canvas.clipRect(0, 0, l, l * (10 - k) / 10);
                //Log.d("k", String.valueOf(k));
                canvas.drawPicture(body);
                path = bodyPath;
                canvas.restore();
            } else {
                canvas.save();
                if (part.equals(head())) {
                    Path path1 = new Path();
                    path1.moveTo(l, 0);
                    path1.arcTo(0, -l, 2 * l, l, 90, 9 * k, false);
                    path1.close();
                    canvas.clipPath(path1);
                }
                if (part.equals(tail())) {
                    Path path1 = new Path();
                    path1.moveTo(l, 0);
                    path1.arcTo(0, -l, 2 * l, l, 90 + 9 * k, 9 * (10 - k), false);
                    path1.close();
                    canvas.clipPath(path1);
                }
                if (part.vec.turn(1) == part.oldVec && part.dir == Part.Direction.LEFT || part.vec.turn(-1) == part.oldVec && part.dir == Part.Direction.RIGHT) {
                    canvas.drawPicture(bigCorner);
                    path = bigCornerPath;
                } else {
                    canvas.drawPicture(smallCorner);
                    path = smallCornerPath;
                }
                canvas.restore();
            }

            if (part.equals(head())) {
                PathMeasure pm = new PathMeasure(path, false);
                float[] pos = {0f, 0f}, tan = {0f, 0f};
                pm.getPosTan(pm.getLength() / 10 * k, pos, tan);
                canvas.save();
                canvas.translate(pos[0] - headR, pos[1] - headR);
                canvas.rotate((float) Math.toDegrees(Math.atan2(tan[1], tan[0])), headR, headR);
                p.setColor(Color.YELLOW);
                canvas.drawPicture(head);
                canvas.restore();
            }
            canvas.restore();
        }

        p.setColor(Color.YELLOW);
        canvas.drawPath(smallCornerPath, p);

/*GRID*/
//        p.setColor(Color.BLUE);
//        for (int h = 0; h < Values.Width; h += l)
//            canvas.drawLine(h, 0, h, Values.Height, p);
//        for (int h = 0; h < Values.Height; h += l)
//            canvas.drawLine(0, h, Values.Width, h, p);
/*GRID*/
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
