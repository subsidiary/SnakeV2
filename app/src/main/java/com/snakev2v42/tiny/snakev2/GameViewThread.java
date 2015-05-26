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
    private GameFPSstabilizerThread GFPSST=null;
    public static int pos=0;

    public GameViewThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean flag){
        running=flag;
        if(GFPSST==null)
            GFPSST= new GameFPSstabilizerThread();
        GFPSST.setRunning(flag);
        if(!GFPSST.isAlive() && flag)
            GFPSST.start();
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
                        GameActivity.render(canvas, pos);
                }
            }
            finally {
                if (canvas != null) {
                    if (running)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            if(pos >= Values.SnakeSize-1){
                long startPoint=System.currentTimeMillis();
                Logic.think();
                Log.i("Logic.think()      ", System.currentTimeMillis() - startPoint + "");
                pos = 0;
            }
        }
    }


    public static class GameFPSstabilizerThread extends Thread {
        private boolean running=false;

        public void setRunning(boolean flag){
            running=flag;
        }
        @Override
        public void run() {
            super.run();
            while(running){
                try {
                    sleep(Values.snakeSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(GameViewThread.pos<Values.SnakeSize-1)
                    ++GameViewThread.pos;
            }
        }
    }
}
