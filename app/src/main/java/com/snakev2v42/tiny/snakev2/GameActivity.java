package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar, Surface;
    static TextView score;
    static ArrayList<Snake> sn = new ArrayList<>();
    static ArrayList<Meal> ml = new ArrayList<>();
    ImageButton v1, v2, v3, v4 ,stop;
    boolean stoped=false;
    Thread GraphicThread = new GameActivity.GraphicThread();
    Thread LoadFrame = new LoadFrame();
    Handler HelpToDraw = new Handler();
    static Vector currVector = Vector.WEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //Working on Graphic stuff
        ll = (RelativeLayout) findViewById(R.id.gamelay);
        BoBar = (ImageView) findViewById(R.id.BottomBar);
        score = (TextView) findViewById(R.id.score);
        Surface = (ImageView) findViewById(R.id.MainBitmapSurface);
        v1 = (ImageButton) findViewById(R.id.v1);
        v2 = (ImageButton) findViewById(R.id.v2);
        v3 = (ImageButton) findViewById(R.id.v3);
        v4 = (ImageButton) findViewById(R.id.v4);
        stop = (ImageButton) findViewById(R.id.stop);

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        Values.init(display.widthPixels, display.heightPixels, getResources().getDisplayMetrics().density);

        BoBar.getLayoutParams().height = Values.BoBaHeight;
        score.setTextSize(Values.BoBaHeight / Values.dens - 4);

        //Working on Objects
        Bitmaps bit = new Bitmaps(this);
        sn.add(0, new Snake(30, 10 , Vector.WEST , 3, Color.parseColor("#F5F5F5")));
        sn.add(1, new Snake(10 , 10 , Vector.EAST , 3, Color.parseColor("#CDDC39")));

        Values.AMOUNT_OF_SNAKES = 2;

        //m=Amount of meal
        for (int m = 0; m < 2; ++m){
            ml.add(m, new Meal(1, Color.parseColor("#3F51B5")));
            ++Values.AMOUNT_OF_MEAL;
        }

        /*sn.add(2, new Snake(35, 6 , Vector.WEST , 3, Color.parseColor("#4CAF50")));
        sn.add(3, new Snake(4, 9 , Vector.SOUTH , 3, Color.parseColor("#03A9F4")));
        sn.add(4, new Snake(35, 12, Vector.WEST , 3, Color.parseColor("#FF9800")));
        sn.add(5, new Snake(4, 15, Vector.SOUTH , 3, Color.parseColor("#607D8B")));
        sn.add(6, new Snake(35, 18, Vector.WEST , 3, Color.parseColor("#E91E63")));*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Values.FirstLoad) {
            GraphicThread.start();
            Values.FirstLoad = false;
        }
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

    public void stopIt(View v){
        stoped=!stoped;
    }
    class LoadFrame extends Thread {
        @Override
        public void run() {
            super.run();
            Bitmaps.MainB.eraseColor(Color.parseColor("#212121"));
            if (currVector != Vector.inverse(sn.get(0).head().vec))
                sn.get(0).head().vec = currVector;
            //Counts Vector ,moves and checks the snakes that ate meal;
            for (int s = 0; s < Values.AMOUNT_OF_SNAKES; ++s) {
                Snake snake = sn.get(s);
                if (s == 0)
                    snake.head().vec = Brains.YuraBot(snake);
                else
                    snake.head().vec = Brains.StupidBot(snake);
                if (!snake.broken)
                    snake.move();
                for (int m = 0; m < Values.AMOUNT_OF_MEAL; ++m)
                    if (ml.get(m).eat(snake.head().p)) {
                        ml.get(m).generate();
                        snake.grow(1);
                    }
                if (Map.IfBroken(snake.head().p))
                    snake.broken = true;
                snake.PaintSnake();
            }
            for (int m = 0; m < Values.AMOUNT_OF_MEAL; ++m)
                ml.get(m).PaintMeal();
            Bitmaps.DrawToMainB(Bitmaps.Pete,GameActivity.sn.get(1).parts.get(0).p.x*Values.SnakeSize,GameActivity.sn.get(1).parts.get(0).p.y*Values.SnakeSize);
            Bitmaps.DrawToMainB(Bitmaps.Yura,GameActivity.sn.get(0).parts.get(0).p.x*Values.SnakeSize,GameActivity.sn.get(0).parts.get(0).p.y*Values.SnakeSize);
        }
    }

    class GraphicThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (Values.game) {
                try {
                    sleep(Values.delayTime - 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!stoped)
                LoadFrame.run();
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HelpToDraw.post(new Runnable() {
                    @Override
                    public void run() {
                        Surface.setImageBitmap(Bitmaps.MainB);
                    }
                });
            }
        }
    }
}
