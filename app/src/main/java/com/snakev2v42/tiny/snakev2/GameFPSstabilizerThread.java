package com.snakev2v42.tiny.snakev2;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by yuriy on 5/19/2015.
 */
public class GameFPSstabilizerThread extends Thread {
    private boolean running=false;
    static int pos=0;

    public void setRunning(boolean flag){
        running=flag;
    }
    @Override
    public void run() {
        super.run();
        while(running){
            try {
                sleep(GameActivity.snakeSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++pos;
        }


    }
}