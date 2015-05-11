package com.snakev2v42.tiny.snakev2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

/**
 * Created by yuriy on 5/11/2015.
 */
public class GameThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private boolean running=false;
    private Canvas canvas;
    private int pos=0;

    public GameThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean flag){
        running=flag;
    }
    @Override
    public void run() {
        super.run();
        while(running){
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    if(running)
                        GameActivity.render(canvas,pos);
                }
            }
            finally {
                if (canvas != null) {
                    if(running)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            if(++pos >= GameActivity.snakeSpeed)
                for (int i=0;i<Values.AMOUNT_OF_SNAKES;++i) {
                    Snake snake=GameActivity.snakes.get(i);
                    if(i==0 && GameActivity.currVector!=Vector.inverse(snake.head().vec))
                        snake.head().vec=GameActivity.currVector;
                    else
                        snake.turn((GameActivity.random.nextInt(9) - 4) / 4);
                    snake.move();
                    pos = 0;
                }
        }


    }
}
