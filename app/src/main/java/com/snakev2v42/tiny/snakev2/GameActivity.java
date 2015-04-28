package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar,MainBsurf;
    static TextView score;
    static ArrayList<Snake> sn = new ArrayList<>();
    static ArrayList<Meal> ml = new ArrayList<>();
    ImageButton v1,v2,v3,v4;
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
        ll=(RelativeLayout)findViewById(R.id.gamelay);
        BoBar=(ImageView)findViewById(R.id.BottomBar);
        score=(TextView)findViewById(R.id.score);
        MainBsurf=(ImageView)findViewById(R.id.MainBitmapSurface);
        v1=(ImageButton)findViewById(R.id.v1);
        v2=(ImageButton)findViewById(R.id.v2);
        v3=(ImageButton)findViewById(R.id.v3);
        v4=(ImageButton)findViewById(R.id.v4);

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        Values val = new Values(display.widthPixels,display.heightPixels,getResources().getDisplayMetrics().density);

        BoBar.getLayoutParams().height= Values.BoBaHeight;
        score.setTextSize(Values.BoBaHeight / Values.dens - 4);

        //Working on Objects
        Bitmaps bit = new Bitmaps(this);
        Values.AMOUNT_OF_SNAKES=7;
        Values.AMOUNT_OF_MEAL=7;
        for(int m=0;m<Values.AMOUNT_OF_MEAL;++m)
            ml.add(m, new Meal(1,Color.parseColor("#3F51B5")));

        sn.add(0,new Snake(10,10, Vector.WEST,7,Color.parseColor("#F5F5F5")));
        sn.add(1,new Snake(10,11, Vector.WEST,7,Color.parseColor("#CDDC39")));
        sn.add(0,new Snake(10,12, Vector.WEST,7,Color.parseColor("#4CAF50")));
        sn.add(1,new Snake(10,13, Vector.WEST,7,Color.parseColor("#03A9F4")));
        sn.add(0,new Snake(10,14, Vector.WEST,7,Color.parseColor("#FF9800")));
        sn.add(1,new Snake(10,15, Vector.WEST,7,Color.parseColor("#607D8B")));
        sn.add(1,new Snake(10,15, Vector.WEST,7,Color.parseColor("#E91E63")));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Values.FirstLoad){
            GraphicThread.start();
            Values.FirstLoad=false;
        }
    }

    public void clicked(View v) {
        switch(v.getId()){
            case R.id.v2:
                currVector = Vector.EAST;break;
            case R.id.v3:
                currVector = Vector.SOUTH;break;
            case R.id.v1:
                currVector = Vector.NORTH;break;
            case R.id.v4:
                currVector = Vector.WEST;break;
        }
    }

    class LoadFrame extends Thread{
        @Override
        public void run() {
            super.run();
            if (currVector != Vector.inverse(sn.get(0).head().vec))
                sn.get(0).head().vec = currVector;
            //Counts Vector ,moves and checks the snakes that ate meal;
            for(int s=0;s<Values.AMOUNT_OF_SNAKES;++s){
                Snake snake=sn.get(s);
                if(s!=0)
                    snake.head().vec=Brains.NewVector(snake);
                if(!snake.broken)
                    snake.move();
                for(int m=0;m<Values.AMOUNT_OF_MEAL;++m)
                    if(ml.get(m).eat(snake.head().p)){
                        ml.get(m).generate();
                        snake.grow(1);
                    }
                if(Brains.AmountOfPartsInTheCurrentPoint(snake.head().p)>1)
                    snake.broken=true;
                    snake.PaintSnake();

            }
            for(int m=0;m<Values.AMOUNT_OF_MEAL;++m)
                ml.get(m).PaintMeal();

        }
    }
    class GraphicThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(Values.game) {
                try {
                    sleep(Values.delayTime - 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bitmaps.MainB.eraseColor(Color.parseColor("#212121"));
                LoadFrame.run();
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HelpToDraw.post(new Runnable() {
                    @Override
                    public void run() {
                        MainBsurf.setImageBitmap(Bitmaps.MainB);
                    }
                });
            }
        }
    }
}
