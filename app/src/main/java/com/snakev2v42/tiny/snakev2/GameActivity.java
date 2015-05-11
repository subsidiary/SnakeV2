package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar;
    private static SurfaceView surface;
    static TextView score;
    static ArrayList<Snake> snakes = new ArrayList<>();
    static ArrayList<Meal> ml = new ArrayList<>();
    ImageButton v1, v2, v3, v4, stop;
    boolean stoped = false;
    //    Thread LoadFrame = new LoadFrame();
    Handler HelpToDraw = new Handler();
    static Vector currVector = Vector.WEST;
    private int color = 0;
    static GameView view;
    private static int snakeSpeed = 10;
    public static Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        setContentView(view);
//        setContentView(R.layout.game_activity);

        //Working on Graphic stuff
        //ll = (RelativeLayout) findViewById(R.id.gamelay);
        BoBar = (ImageView) findViewById(R.id.BottomBar);
        score = (TextView) findViewById(R.id.score);
        //surface = (GameView) findViewById(R.id.MainBitmapSurface);
        v1 = (ImageButton) findViewById(R.id.v1);
        v2 = (ImageButton) findViewById(R.id.v2);
        v3 = (ImageButton) findViewById(R.id.v3);
        v4 = (ImageButton) findViewById(R.id.v4);
        stop = (ImageButton) findViewById(R.id.stop);

        //BoBar.getLayoutParams().height = Values.BoBaHeight;
        //score.setTextSize(Values.BoBaHeight / Values.dens - 4);

        //Working on Objects
        Bitmaps bit = new Bitmaps(this);

        //Use Values variables to communicate settings with game activity;
        snakeSpeed=9-Values.lvl;

        random = new Random();
        random.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 20; ++i)
            snakes.add(new Snake(30, i, Vector.WEST, 1, Color.parseColor("#F5F5F5"), Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        //snakes.add(new Snake(10, 10, Vector.EAST, 1, Color.parseColor("#CDDC39"), Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));

        Values.AMOUNT_OF_SNAKES = 2;

        //m=Amount of meal
        for (int m = 0; m < 2; ++m) {
            ml.add(m, new Meal(1, Color.parseColor("#3F51B5")));
            ++Values.AMOUNT_OF_MEAL;
        }

        /*snakes.add(2, new Snake(35, 6 , Vector.WEST , 3, Color.parseColor("#4CAF50")));
        snakes.add(3, new Snake(4, 9 , Vector.SOUTH , 3, Color.parseColor("#03A9F4")));
        snakes.add(4, new Snake(35, 12, Vector.WEST , 3, Color.parseColor("#FF9800")));
        snakes.add(5, new Snake(4, 15, Vector.SOUTH , 3, Color.parseColor("#607D8B")));
        snakes.add(6, new Snake(35, 18, Vector.WEST , 3, Color.parseColor("#E91E63")));*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Here I am writing a pause, just now...
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.surfaceDestroyed(null);
    }

    public void clicked(View v) {
        switch (v.getId()) {
            case R.id.v2:
                currVector = Vector.EAST;
                break;
            case R.id.v3:
                currVector = Vector.SOUTH;
                break;
            case R.id.v1:
                currVector = Vector.NORTH;
                break;
            case R.id.v4:
                currVector = Vector.WEST;
                break;
        }
    }

    public void stopIt(View v) {
        stoped = !stoped;
    }

    private void render(Canvas canvas, int k) {
        canvas.drawColor(Color.BLACK);
        for (Snake snake : snakes) {
            snake.draw(canvas, k);
        }
    }

    public class GameThread extends Thread {
        private final SurfaceHolder surfaceHolder;
        private boolean run = false;

        public GameThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean run) {
            this.run = run;
        }

        public SurfaceHolder getSurfaceHolder() {
            return surfaceHolder;
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
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }

                if(++i >= snakeSpeed)
                    for (Snake snake : snakes) {
                        //snake.head().vec=Brains.YuraBot(snake);
                        snake.move();
                        snake.turn((random.nextInt(9) - 4) / 4);
                        i = 0;
                    };
            }
        }
    }

    public class GameView extends SurfaceView implements SurfaceHolder.Callback {
        GameThread thread;

        public GameView(Context context) {
            super(context);
            getHolder().addCallback(this);
        }

        public GameView(Context context, AttributeSet attrs) {
            super(context, attrs);
            getHolder().addCallback(this);
        }

        public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            thread = new GameThread(getHolder());
            thread.setRunning(true);
            thread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            thread.setRunning(false);
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {

                }
            }
        }

    }
}
