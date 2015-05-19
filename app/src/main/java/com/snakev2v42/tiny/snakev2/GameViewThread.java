package com.snakev2v42.tiny.snakev2;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by yuriy on 5/11/2015.
 */
public class GameViewThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private boolean running=false;
    private Canvas canvas;

    public GameViewThread(SurfaceHolder surfaceHolder) {
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
                        GameActivity.render(canvas, GameFPSstabilizerThread.pos);
                }
            }
            finally {
                if (canvas != null) {
                    if (running)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            if(GameFPSstabilizerThread.pos >= Values.SnakeSize){
                long startPoint=System.currentTimeMillis();
                Logic.think();
                Log.i("Logic.think()      ", System.currentTimeMillis() - startPoint + "");
                GameFPSstabilizerThread.pos = 0;
            }

        }


    }
}
