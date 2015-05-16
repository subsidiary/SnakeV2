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

import com.snakev2v42.tiny.snakev2.menusAndSettings.Mode;
import com.snakev2v42.tiny.snakev2.menusAndSettings.ResultActivity;
import com.snakev2v42.tiny.snakev2.menusAndSettings.StartActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar;
    static TextView score1,score2;
    static ArrayList<Snake> snakes = new ArrayList<>();
    static ArrayList<Meal> ml = new ArrayList<>();
    ImageButton v1_1, v2_1, v3_1, v4_1, v1_2, v2_2, v3_2, v4_2;
    static Handler handler = new Handler();
    static GameView view;
    public static int snakeSpeed;
    public static Random random;
    static GameActivity game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //Working on Graphic stuff
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
        //super.onBackPressed();
        Intent GoResult = new Intent(this,ResultActivity.class);
        GoResult.putExtra("Score",random.nextInt(2000));
        startActivity(GoResult);
        overridePendingTransition(R.anim.start_activity,R.anim.wait_anim);
    }

    @Override
    protected void onPause() {
        super.onPause();
        close();
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
        for (Meal meal : GameActivity.ml){
            meal.draw(canvas);
        }
    }

    public void close(){
        //view.thread.setRunning(false);
    }
}
