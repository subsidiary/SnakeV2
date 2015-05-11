package com.snakev2v42.tiny.snakev2.menusAndSettings;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snakev2v42.tiny.snakev2.GameActivity;
import com.snakev2v42.tiny.snakev2.R;
import com.snakev2v42.tiny.snakev2.Values;

/**
 * Created by yuriy on 5/11/2015.
 */
public class ResultActivity extends Activity{
    private RelativeLayout re;
    private TextView resultTxt,recordTxt,GoGameTxt,GoMenuTxt;
    private ImageButton GoGame,GoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        re=(RelativeLayout)findViewById(R.id.re);
        resultTxt=(TextView)findViewById(R.id.resultTxt);
        recordTxt=(TextView)findViewById(R.id.record);
        re.setBackgroundColor(Values.getTheme().getColor());
        GoGame=(ImageButton)findViewById(R.id.GoGame);
        GoMenu=(ImageButton)findViewById(R.id.GoMenu);
        GoGameTxt=(TextView)findViewById(R.id.GoGameTxt);
        GoMenuTxt=(TextView)findViewById(R.id.GoMenuTxt);


        GoGameTxt.setTextColor(Values.getTheme().TextColor);
        GoMenuTxt.setTextColor(Values.getTheme().TextColor);
        recordTxt.setTextColor(Values.getTheme().TextColor);
        resultTxt.setTextColor(Values.getTheme().TextColor);
        recordTxt.setTypeface(StartActivity.DotTxt);
        resultTxt.setTypeface(StartActivity.DotTxt);
        GoGameTxt.setTypeface(StartActivity.DotTxt);
        GoMenuTxt.setTypeface(StartActivity.DotTxt);
        recordTxt.setTextSize((float) Values.IncreaceFactor * 25);
        resultTxt.setTextSize((float) Values.IncreaceFactor * 80);
        GoGameTxt.setTextSize((float)Values.IncreaceFactor*14);
        GoMenuTxt.setTextSize((float)Values.IncreaceFactor*16);
        GoMenu.setColorFilter(Values.getTheme().exit);
        GoGame.setColorFilter(Values.getTheme().exit);

        StartActivity.setParams(GoGame,(int)(Values.IncreaceFactor*Values.dens*80),(int)(Values.IncreaceFactor*Values.dens*80));
        StartActivity.setParams(GoMenu,(int)(Values.IncreaceFactor*Values.dens*80),(int)(Values.IncreaceFactor*Values.dens*80));

        int score=getIntent().getExtras().getInt("Score",0);
        resultTxt.setText(score+"");
        recordTxt.setText(CheckRecords(score));
        CheckRecords(score);
    }

    public void onGoMenuClick(View v){
        GoMenu.startAnimation(StartActivity.button_clicked);
        Intent GoTOMenu = new Intent(this, StartActivity.class);
        startActivity(GoTOMenu);
        finish();
        overridePendingTransition(R.anim.wait_anim,R.anim.exit_activity);
    }
    public void onGoGameClick(View v){
        GoGame.startAnimation(StartActivity.button_clicked);
        finish();
        overridePendingTransition(R.anim.wait_anim,R.anim.exit_activity);
    }




    public String CheckRecords(int score){
        int[] temp;
        String newRecord="";
        switch(Values.mode){
            case CLASSIC :temp=Values.classicRec;break;
            case BATTLE  :temp=Values.battleRec;break;
            case CAMPAIGN:temp=Values.campaignRec;break;
            case MULTIPLAYER:temp=Values.multiplayerRec;break;
            default: temp=Values.classicRec;
        }
        for(int i=0;i<5;++i) {
            if(score>temp[i]){
                if(temp[i]<score){
                    switch(Values.mode){
                        case CLASSIC :Values.classicRec[i]=score;break;
                        case BATTLE  :Values.battleRec[i]=score;break;
                        case CAMPAIGN:Values.campaignRec[i]=score;break;
                        case MULTIPLAYER:Values.multiplayerRec[i]=score;break;
                    }
                    if(i==0)newRecord="MASTER";
                    else
                    newRecord="RECORD";
                    break;
                }
            }
        }
        return newRecord;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.wait_anim,R.anim.exit_activity);
        finish();
    }
}
