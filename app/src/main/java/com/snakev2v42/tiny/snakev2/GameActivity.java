package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snakev2v42.tiny.snakev2.menusAndSettings.Mode;
import com.snakev2v42.tiny.snakev2.menusAndSettings.ResultActivity;
import com.snakev2v42.tiny.snakev2.menusAndSettings.StartActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar;
    public static TextView score1,score2;
    public static ArrayList<Snake> snakes = new ArrayList<>();
    public static ArrayList<Meal> ml = new ArrayList<>();
    ImageButton v1_1, v2_1, v3_1, v4_1, v1_2, v2_2, v3_2, v4_2;
    public static Handler handler = new Handler();
    public static int snakeSpeed;
    public static Random random;
    static GameView view;
    public static Context game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.start_activity,R.anim.wait_anim);
        setContentView(R.layout.game_activity);

        //Working on Graphic stuff
        game=this;
        ll = (RelativeLayout) findViewById(R.id.GameLayout);
        BoBar = (ImageView) findViewById(R.id.BottomBar);

        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);

        v1_1 = (ImageButton) findViewById(R.id.v1_1);
        v2_1 = (ImageButton) findViewById(R.id.v2_1);
        v3_1 = (ImageButton) findViewById(R.id.v3_1);
        v4_1 = (ImageButton) findViewById(R.id.v4_1);
        v1_2 = (ImageButton) findViewById(R.id.v1_2);
        v2_2 = (ImageButton) findViewById(R.id.v2_2);
        v3_2 = (ImageButton) findViewById(R.id.v3_2);
        v4_2 = (ImageButton) findViewById(R.id.v4_2);
        view = (GameView)findViewById(R.id.surface);

        BoBar.getLayoutParams().height = Values.BoBaHeight;
        score1.setTextSize(StartActivity.convertFromDp(Values.BoBaHeight -32));
        score2.setTextSize(StartActivity.convertFromDp(Values.BoBaHeight -32));

        BoBar.setBackgroundColor(Values.getTheme().BoBar);
        score1.setTextColor(Values.getTheme().TextColor);
        score2.setTextColor(Values.getTheme().TextColor);
        score1.setTypeface(StartActivity.DotTxt);
        score2.setTypeface(StartActivity.DotTxt);

        v1_1.setColorFilter(Values.getTheme().controlButtons);
        v2_1.setColorFilter(Values.getTheme().controlButtons);
        v3_1.setColorFilter(Values.getTheme().controlButtons);
        v4_1.setColorFilter(Values.getTheme().controlButtons);
        v1_2.setColorFilter(Values.getTheme().controlButtons);
        v2_2.setColorFilter(Values.getTheme().controlButtons);
        v3_2.setColorFilter(Values.getTheme().controlButtons);
        v4_2.setColorFilter(Values.getTheme().controlButtons);


        //Logic part
        if(Values.mode== Mode.MULTIPLAYER){
            v1_2.setVisibility(View.VISIBLE);
            v2_2.setVisibility(View.VISIBLE);
            v3_2.setVisibility(View.VISIBLE);
            v4_2.setVisibility(View.VISIBLE);
            score2.setVisibility(View.VISIBLE);
        }
        snakeSpeed=3+9-Values.lvl;
        random = new Random();
        random.setSeed(System.currentTimeMillis());
        Logic.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Values.SaveTheGame();
        Intent GoResult = new Intent(this,ResultActivity.class);
        startActivity(GoResult);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void joystick (View v) {
        switch (v.getId()) {
            case R.id.v2_1:
                Logic.currentVector1 = Vector.EAST;
                break;
            case R.id.v3_1:
                Logic.currentVector1 = Vector.SOUTH;
                break;
            case R.id.v1_1:
                Logic.currentVector1 = Vector.NORTH;
                break;
            case R.id.v4_1:
                Logic.currentVector1 = Vector.WEST;
                break;
            case R.id.v2_2:
                Logic.currentVector2 = Vector.EAST;
                break;
            case R.id.v3_2:
                Logic.currentVector2 = Vector.SOUTH;
                break;
            case R.id.v1_2:
                Logic.currentVector2 = Vector.NORTH;
                break;
            case R.id.v4_2:
                Logic.currentVector2 = Vector.WEST;
                break;
        }
    }

    public static void render(Canvas canvas, int k) {
        canvas.drawColor(Color.parseColor("#212121"));
        for (Snake snake : GameActivity.snakes) {
            snake.draw(canvas, k);
        }

        //As for me it takes less performance than draw it by draw() method in Meal.class
        canvas.save();
        for (Meal meal : GameActivity.ml){
            Paint paint=new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(meal.color);
            canvas.drawCircle((meal.p.x + 0.5f) * Values.SnakeSize, (meal.p.y + 0.5f) * Values.SnakeSize, (meal.size / 3.5f) * Values.SnakeSize, paint);
        }
        canvas.restore();
    }

}
