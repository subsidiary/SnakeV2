package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {
    static RelativeLayout ll;
    static ImageView BoBar;
    static TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);loading();
    }

    public void loading(){
        //Working on Graphic stuff
        ll=(RelativeLayout)findViewById(R.id.gamelay);
        BoBar=(ImageView)findViewById(R.id.BottomBar);
        score=(TextView)findViewById(R.id.score);

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        Values val = new Values(display.widthPixels,display.heightPixels,getResources().getDisplayMetrics().density);

        BoBar.getLayoutParams().height=val.BoBaHeight;
        score.setTextSize(val.BoBaHeight / val.dens - 4);
    }
}
