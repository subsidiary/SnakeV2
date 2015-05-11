package com.snakev2v42.tiny.snakev2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Picture;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Snake {
    private final int color, bgColor, eyeColor, speed;
    private Picture headPicture, bodyPicture, bigCornerPicture, smallCornerPicture;
    public int length;
    public ArrayList<Part> parts;
    public boolean broken;
    private float eyeR, eyeDist, headR, l, w;
    public static Path bodyPath, bigCornerPath, smallCornerPath;
    private boolean flag;

    public Snake(int startX, int startY, Vector vector, int length, int color, int bgColor, int eyeColor, float cellSize, int speed) {
        this(startX, startY, vector, length, color, bgColor, eyeColor, cellSize, cellSize * 0.375f, cellSize / 3, cellSize / 12, cellSize / 7, speed);
    }

    public Snake(int startX, int startY, Vector vector, int length, int color, int bgColor, int eyeColor, float cellSize, float width, float headRadius, float eyeRadius, float eyeDist, int speed) {
        l = cellSize;
        headR = headRadius;
        eyeR = eyeRadius;
        this.eyeDist = eyeDist;
        this.color = color;
        this.bgColor = bgColor;
        this.eyeColor = eyeColor;
        this.speed = speed;
        w = width;
        initPics();
        parts = new ArrayList<>();
        //Add head
        parts.add(new Part(startX, startY, vector, l));
        this.broken = false;
        this.length = 1;
        grow(length);
    }

    /**
     * Moves every {@code Part} of this {@code Snake} into next cell
     */
    public void move() {
        for (int i = length - 1; i > 0; --i) {
            Part part = parts.get(i);
            part.move(parts.get(i - 1).vec);
        }
        parts.get(0).move(parts.get(0).vec);
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
            parts.add(new Part(tail.p.x, tail.p.y, tail.vec, tail.dir, tail.pointDir.inverse(), l));
            //Update tail part
            tail = parts.get(length++);
            //Move tail part
            tail.p.plus(Vector.inverse(tail.vec));
        }
    }

    private void initPics() {
        float x, y;
        Paint bodyPaint = new Paint();
        bodyPaint.setStyle(Paint.Style.STROKE);
        bodyPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bodyPaint.setColor(Color.RED);
        bodyPaint.setStrokeWidth(w);
        Paint eyePaint = new Paint();
        eyePaint.setStyle(Paint.Style.FILL);
        eyePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        eyePaint.setColor(Color.BLACK);
        Canvas c;

        bodyPath = new Path();
        x = l;
        y = l;
        bodyPath.arcTo(x - l / 2, y - l / 2, x + l / 2, y + l / 2, 180, 30, false);
        x = (float) (1 - Math.sqrt(0.75)) * l;
        y = l / 2;
        bodyPath.arcTo(x - l / 2, y - l / 2, x + l / 2, y + l / 2, 30, -60, false);
        x = l;
        y = 0;
        bodyPath.arcTo(x - l / 2, y - l / 2, x + l / 2, y + l / 2, 150, 30f, false);

        smallCornerPath = new Path();
        x = l;
        y = 0;
        smallCornerPath.arcTo(x - l / 2, y - l / 2, x + l / 2, y + l / 2, 90, 90, false);

        bigCornerPath = new Path();
        x = l;
        y = l;
        bigCornerPath.arcTo(x - l / 2, y - l / 2, x + l / 2, y + l / 2, 270, -30, false);
        x = (float) ((3 - Math.sqrt(3)) * l / 2);
        y = (float) ((Math.sqrt(3) - 1) * l / 2);
        float r = (float) ((Math.sqrt(3) - 1) * l - l / 2);
        bigCornerPath.arcTo(x - r, y - r, x + r, y + r, 60, 150, false);
        x = 0;
        y = 0;
        bigCornerPath.arcTo(x - l / 2, y - l / 2, x + l / 2, y + l / 2, 30, -30f, false);

        /*for (int i = 0; i < speed; ++i) {
            PathMeasure pm = new PathMeasure(smallCornerPath, false);
            float[] pos = {0f, 0f}, tan = {0f, 0f};
            pm.getPosTan(i * pm.getLength() / speed, pos, tan);
            Log.d("angle1", String.valueOf(Math.toDegrees(Math.atan2(tan[1], tan[0]))));
        }
        Log.d("space", "space");

        for (int i = 0; i < speed; ++i) {
            PathMeasure pm = new PathMeasure(bigCornerPath, false);
            float[] pos = {0f, 0f}, tan = {0f, 0f};
            pm.getPosTan(i * pm.getLength() / speed, pos, tan);
            Log.d("angle2", String.valueOf(Math.toDegrees(Math.atan2(tan[1], tan[0]))));
        }
        Log.d("space", "space");*/

        bodyPicture = new Picture();
        c = bodyPicture.beginRecording((int) l, (int) l);
        c.clipRect(0, 0, l, l);
        c.drawPath(bodyPath, bodyPaint);
        bodyPicture.endRecording();

        smallCornerPicture = new Picture();
        c = smallCornerPicture.beginRecording((int) l, (int) l);
        c.clipRect(0, 0, l, l);
        c.drawPath(smallCornerPath, bodyPaint);
        smallCornerPicture.endRecording();

        bigCornerPicture = new Picture();
        c = bigCornerPicture.beginRecording((int) l, (int) l);
        c.clipRect(0, 0, l, l);
        c.drawPath(bigCornerPath, bodyPaint);
        bigCornerPicture.endRecording();


        headPicture = new Picture();
        c = headPicture.beginRecording((int) (2 * headR), (int) (2 * headR));
        bodyPaint.setStyle(Paint.Style.FILL);
        c.drawCircle(headR, headR, headR, bodyPaint);
        c.drawCircle(headR + eyeDist, headR + eyeDist, eyeR, eyePaint);
        c.drawCircle(headR + eyeDist, headR - eyeDist, eyeR, eyePaint);

        headPicture.endRecording();
    }


    public void draw(Canvas canvas, int k) {
        flag = true;
        Paint bodyPaint = new Paint();
        bodyPaint.setStyle(Paint.Style.STROKE);
        bodyPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bodyPaint.setColor(Color.RED);
        bodyPaint.setStrokeWidth(w);

        k %= speed;
        Paint p = new Paint();
        p.setColor(Color.RED);

        for (int i = length - 1; i >= 0; --i) {
            Path path = null;
            Part part = parts.get(i);
            Picture picture;
            canvas.save();
            canvas.translate(part.p.x * l, part.p.y * l);

            if (part.oldVec == part.vec) {
                picture = bodyPicture;
                path = new Path(Snake.bodyPath);
            } else {
                if (part.vec.turn(1) == part.oldVec && part.dir == Part.Direction.LEFT || part.vec.turn(-1) == part.oldVec && part.dir == Part.Direction.RIGHT) {
                    picture = smallCornerPicture;
                    path = new Path(Snake.smallCornerPath);
                } else {
                    picture = bigCornerPicture;
                    path = new Path(Snake.bigCornerPath);
                }
            }
            canvas.rotate(part.vec.angle, l * 0.5f, l * 0.5f);
            if (part.oldVec == part.vec && part.dir == Part.Direction.LEFT || part.vec.turn(1) == part.oldVec)
                canvas.scale(-1, 1, l * 0.5f, l * 0.5f);

            /*Matrix matrix = new Matrix();
            if (part.oldVec == part.vec && part.dir == Part.Direction.LEFT || part.vec.turn(1) == part.oldVec)
                matrix.preScale(-1, 1, l * 0.5f, l * 0.5f);
            matrix.postRotate(part.vec.angle, l * 0.5f, l * 0.5f);
            path.transform(matrix);*/

            canvas.save();
            canvas.clipRect(0, 0, l, l);
            if (part.equals(head())) {
                Path path1 = new Path();
                PathMeasure pm = new PathMeasure(path, false);
                pm.getSegment(0, pm.getLength() * (k + 1) / speed, path1, true);
                canvas.drawPath(path1, bodyPaint);
                path = path1;
            } else if (part.equals(tail())) {
                Path path1 = new Path();
                PathMeasure pm = new PathMeasure(path, false);
                pm.getSegment(pm.getLength() * k / speed, pm.getLength(), path1, true);
                canvas.drawPath(path1, bodyPaint);
                path = path1;
            } else {
                canvas.drawPicture(picture);
            }
            canvas.restore();

            if (!part.equals(head()) && !part.equals(tail())) {
                PathMeasure pm = new PathMeasure(path, false);
                float[] pos = {0f, 0f}, tan = {0f, 0f};
                pm.getPosTan(k * pm.getLength() / speed, pos, tan);
                canvas.save();
                canvas.rotate((float) Math.toDegrees(Math.atan2(tan[1], tan[0])) - 90, pos[0], pos[1]);
                //canvas.translate(pos[0] - eyeR, pos[1] - eyeR);
                p.setColor(Color.WHITE);
                canvas.drawCircle(pos[0] + (w / 2 - eyeR * 1.5f) * (part.dir == Part.Direction.LEFT ? -1 : 1) * (part.pointDir == Part.Direction.LEFT ? 1 : -1), pos[1], eyeR, p);
                canvas.restore();
            }

            //Draw head
            if (part.equals(head())) {
                PathMeasure pm = new PathMeasure(path, false);
                float[] pos = {0f, 0f}, tan = {0f, 0f};
                pm.getPosTan(pm.getLength(), pos, tan);
                canvas.save();
                canvas.translate(pos[0] - headR, pos[1] - headR);
                if (part.vec == part.oldVec)
                    canvas.rotate(-90, headR, headR);
                else
                    canvas.rotate((float) Math.toDegrees(Math.atan2(tan[1], tan[0])), headR, headR);
                p.setColor(Color.YELLOW);
                canvas.drawPicture(headPicture);
                canvas.restore();
            }

            if (part.equals(tail())) {
                bodyPaint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawPath(path, bodyPaint);
            }
            canvas.restore();
        }

       /* p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.STROKE);
        canvas.save();
        canvas.scale(10, 10);
        canvas.translate(0, 1);
        //p.setStrokeWidth(w);
        canvas.drawPicture(bodyPicture);
        canvas.drawPath(bodyPath, p);
        canvas.translate(l, 0);
        canvas.drawPicture(smallCornerPicture);
        canvas.drawPath(smallCornerPath, p);
        canvas.translate(l, 0);
        canvas.drawPicture(bigCornerPicture);
        canvas.drawPath(bigCornerPath, p);
        canvas.restore();*/

/*GRID*/
        p.setColor(Color.BLUE);
        for (int h = 0; h <= Values.Width; h += l)
            canvas.drawLine(h, 0, h, Values.Height, p);
        for (int h = 0; h <= Values.Height; h += l)
            canvas.drawLine(0, h, Values.Width, h, p);
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
