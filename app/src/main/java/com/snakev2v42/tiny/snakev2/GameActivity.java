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
import android.view.View;
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
    Thread GraphicThread = new GameActivity.GraphicThread();
    Thread LoadFrame = new LoadFrame();
    Handler HelpToDraw = new Handler();
    private Vector currVector = Vector.NORTH;

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

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        Values val = new Values(display.widthPixels,display.heightPixels,getResources().getDisplayMetrics().density);

        BoBar.getLayoutParams().height= Values.BoBaHeight;
        score.setTextSize(Values.BoBaHeight / Values.dens - 4);

        Bitmaps bit = new Bitmaps(this);
        sn.add(0,new Snake(10,10, Vector.WEST,7,Color.BLUE));
        //sn.add(1, new Snake(17, 11, 4, 7, Color.BLUE));
        ml.add(0, new Meal(1,Color.BLUE));
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
            case R.id.v2: currVector = Vector.EAST;break;
            case R.id.v3: currVector = Vector.SOUTH;break;
            case R.id.v1: currVector = Vector.NORTH; break;
            case R.id.v4: currVector = Vector.WEST;break;
        }
    }





    class LoadFrame extends Thread{
        @Override
        public void run() {
            super.run();
            if (Vector.inverse(currVector) != sn.get(0).parts.get(0).vec)
                sn.get(0).parts.get(0).vec = currVector;
            //currVector = Vector.NORTH;
            sn.get(0).move();
            sn.get(0).PaintSnake();
        }
    }
    class GraphicThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(Values.game) {
                try {
                    sleep(Values.delayTime - 15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LoadFrame.run();
                try {
                    sleep(15);
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
