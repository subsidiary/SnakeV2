package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snakev2v42.tiny.snakev2.menusAndSettings.ResultActivity;
import com.snakev2v42.tiny.snakev2.menusAndSettings.StartActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar;
    static TextView score;
    static ArrayList<Snake> snakes = new ArrayList<>();
    static ArrayList<Meal> ml = new ArrayList<>();
    ImageButton v1, v2, v3, v4;
    Handler handler = new Handler();
    static Vector currVector = Vector.WEST;
    static GameView view;
    public static int snakeSpeed = 10;
    public static Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //Working on Graphic stuff
        ll = (RelativeLayout) findViewById(R.id.GameLayout);
        BoBar = (ImageView) findViewById(R.id.BottomBar);
        score = (TextView) findViewById(R.id.score);

        v1 = (ImageButton) findViewById(R.id.v1);
        v2 = (ImageButton) findViewById(R.id.v2);
        v3 = (ImageButton) findViewById(R.id.v3);
        v4 = (ImageButton) findViewById(R.id.v4);

        //Working on Objects
        //Values.init((this));
        //Bitmaps bit = new Bitmaps(this);

        BoBar.getLayoutParams().height = Values.BoBaHeight;
        score.setTextSize(StartActivity.convertFromDp(Values.BoBaHeight / Values.dens));

        BoBar.setBackgroundColor(Values.getTheme().BoBar);
        score.setTextColor(Values.getTheme().TextColor);

        v1.setColorFilter(Values.getTheme().controlButtons);
        v2.setColorFilter(Values.getTheme().controlButtons);
        v3.setColorFilter(Values.getTheme().controlButtons);
        v4.setColorFilter(Values.getTheme().controlButtons);

        //Use Values variables to communicate settings with game activity;
        snakeSpeed=9-Values.lvl;
        random = new Random();
        random.setSeed(System.currentTimeMillis());

        snakes.add(new Snake(30, 10, Vector.WEST, 7, Values.getTheme().getColor(), Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(10, 10, Vector.EAST, 7, Values.getTheme().getColor(), Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        Values.AMOUNT_OF_SNAKES = 2;


        //m=Amount of meal
        /*for (int m = 0; m < 2; ++m) {
            ml.add(m, new Meal(1, Color.parseColor("#3F51B5")));
            ++Values.AMOUNT_OF_MEAL;
        }*/
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent GoResult = new Intent(this,ResultActivity.class);
        GoResult.putExtra("Score",random.nextInt(2000));
        startActivity(GoResult);
        overridePendingTransition(R.anim.start_activity,R.anim.wait_anim);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //view.thread.setRunning(false);
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

    public static void render(Canvas canvas, int k) {
        canvas.drawColor(Color.parseColor("#212121"));
        for (Snake snake : GameActivity.snakes) {
            snake.draw(canvas, k);
        }
    }

    public void close(){
        if(true)
            Values.savedGame=true;
    }
}
