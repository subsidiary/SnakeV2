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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar,MainBsurf;
    static TextView score;
    ArrayList<Snake> sn = new ArrayList<Snake>();
    ArrayList<Meal> ml = new ArrayList<Meal>();
    Thread GraphicThread = new GraphicThreed();
    Thread LoadFrame = new LoadFrame();
    Handler HelpToDraw = new Handler();

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

        BoBar.getLayoutParams().height=Values.BoBaHeight;
        score.setTextSize(Values.BoBaHeight / Values.dens - 4);

        Bitmaps bit = new Bitmaps(this);
        sn.add(0,new Snake(10,10,4,7,Color.parseColor("#F5F5F5")));
        //sn.add(1, new Snake(17, 11, 4, 7, Color.BLUE));
        ml.add(0, new Meal(-1, -1, 1, Color.BLUE, true));
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
            case R.id.v2: if(sn.get(0).preVector!=4)sn.get(0).vector=2;break;
            case R.id.v3: if(sn.get(0).preVector!=1)sn.get(0).vector=3;break;
            case R.id.v1: if(sn.get(0).preVector!=3)sn.get(0).vector=1;break;
            case R.id.v4: if(sn.get(0).preVector!=2)sn.get(0).vector=4;break;
        }
    }





    class LoadFrame extends Thread{
        @Override
        public void run() {
            super.run();
            sn.get(0).moveSnake();
            sn.get(0).PaintSnake();
        }
    }
    class GraphicThreed extends Thread{
        @Override
        public void run() {
            super.run();
            while(Values.game) {
                try {
                    GraphicThreed.sleep(Values.delayTime - 15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LoadFrame.run();
                try {
                    GraphicThreed.sleep(15);
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
