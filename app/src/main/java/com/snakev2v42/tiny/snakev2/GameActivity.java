package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        loading();
        if(Values.FirstLoad){
            //startThreeds;
            Values.FirstLoad=false;
        }
    }

    public void clicked(View v) {
        switch(v.getId()){
            case R.id.v2: if(sn.get(1).preVector!=4)sn.get(1).vector=2;break;
            case R.id.v3: if(sn.get(1).preVector!=1)sn.get(1).vector=3;break;
            case R.id.v1: if(sn.get(1).preVector!=3)sn.get(1).vector=1;break;
            case R.id.v4: if(sn.get(1).preVector!=2)sn.get(1).vector=4;break;
        }
    }

    public void loading(){
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
        sn.add(1,new Snake(10,10,2,7,Color.BLACK));
        sn.add(2,new Snake(17,11,4,7,Color.BLUE));
        //sn.PaintSnake();Is not Working due to the "Attempt to invoke virtual method 'boolean android.graphics.Bitmap.isRecycled()' on a null object reference"
    }
}
