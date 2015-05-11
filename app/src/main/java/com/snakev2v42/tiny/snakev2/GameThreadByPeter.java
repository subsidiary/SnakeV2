package com.snakev2v42.tiny.snakev2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by yuriy on 5/11/2015.
 */
public class GameThreadByPeter extends Thread {
    private final SurfaceHolder surfaceHolder;
    private boolean run = false;

    public GameThreadByPeter(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    private void render(Canvas canvas, int k) {
        canvas.drawColor(Color.BLACK);
        for (Snake snake : GameActivity.snakes) {
            snake.draw(canvas, k);
        }
    }

    @Override
    public void run() {
        Canvas c;
        int i = 0, fps = 0, lastFps = 0, averageFps = 0, sum = 0, n = 0;
        long lastTime = System.nanoTime();
        while (run) {
            c = null;

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {}
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    //call methods to draw and process next fame
                    render(c, i);
                }
                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(20);
                c.drawText(String.format("FPS: %d; Average FPS: %d", lastFps, averageFps), 0, 20, paint);
                ++fps;
                long time = System.nanoTime();
                if (time - lastTime >= 1000000000) {
                    lastTime = time;
                    lastFps = fps;
                    sum += fps;
                    ++n;
                    averageFps = sum / n;
                    fps = 0;
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }

            if(++i >= GameActivity.snakeSpeed)
                for (Snake snake : GameActivity.snakes) {
                    //snake.head().vec=Brains.YuraBot(snake);
                    snake.move();
                    snake.turn((GameActivity.random.nextInt(9) - 4) / 4);
                    i = 0;
                }
        }
    }
}

