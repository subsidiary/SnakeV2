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
import com.snakev2v42.tiny.snakev2.ModeProperties.Battle;
import com.snakev2v42.tiny.snakev2.ModeProperties.Campaign;
import com.snakev2v42.tiny.snakev2.ModeProperties.Classic;
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
        recordTxt.setTextSize(StartActivity.convertFromDp((float) Values.IncreaceFactor * 25));
        resultTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*80));
        GoGameTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*14));
        GoMenuTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*16));
        GoMenu.setColorFilter(Values.getTheme().buttonColor);
        GoGame.setColorFilter(Values.getTheme().buttonColor);

        StartActivity.setParamsByFactor(GoGame, 80, 80);
        StartActivity.setParamsByFactor(GoMenu, 80, 80);

        int score=getIntent().getExtras().getInt("Score",0);
        resultTxt.setText(score+"");
        recordTxt.setText(CheckRecords(score));
    }

    public void onGoMenuClick(View v){
//        GoMenu.startAnimation(StartActivity.button_clicked);
        Intent GoTOMenu = new Intent(this, StartActivity.class);
        startActivity(GoTOMenu);
        finish();
        overridePendingTransition(R.anim.wait_anim,R.anim.exit_activity);
    }
    public void onGoGameClick(View v){
//        GoGame.startAnimation(StartActivity.button_clicked);
        finish();
        overridePendingTransition(R.anim.wait_anim,R.anim.exit_activity);
    }




    public String CheckRecords(int score){
        int[] temp;
        String newRecord="";
        switch(Values.mode){
            case CLASSIC :temp= Classic.records;break;
            case BATTLE  :temp= Battle.records;break;
            case CAMPAIGN:temp= Campaign.records;break;
            default: temp=Classic.records;
        }
        for(int i=0;i<5;++i) {
            if(score>temp[i]){
                if(temp[i]<score){
                    switch(Values.mode){
                        case CLASSIC :Classic.records[i]=score;break;
                        case BATTLE  :Battle.records[i]=score;break;
                        case CAMPAIGN:Campaign.records[i]=score;break;
                    }
                    if(i==0)newRecord="MASTER";
                    else
                    newRecord="RECORD";
                    break;
                }
            }
        }
        Values.saveSettings();
        return newRecord;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.wait_anim,R.anim.exit_activity);
        finish();
    }
}
