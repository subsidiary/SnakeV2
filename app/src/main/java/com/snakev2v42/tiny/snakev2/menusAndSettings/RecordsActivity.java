package com.snakev2v42.tiny.snakev2.menusAndSettings;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snakev2v42.tiny.snakev2.R;
import com.snakev2v42.tiny.snakev2.Values;


/**
 * Created by yuriy on 5/9/2015.
 */
public class RecordsActivity extends Activity {
    private ImageButton modeB;
    private TextView modeTxt, recordsTxt, recordList;
    private RelativeLayout re;
    Mode mode=Mode.CLASSIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_activity);
        modeTxt =(TextView)findViewById(R.id.modeTxt);
        recordsTxt =(TextView)findViewById(R.id.records);
        recordList = (TextView)findViewById(R.id.recs);
        modeB=(ImageButton)findViewById(R.id.mode);
        re=(RelativeLayout)findViewById(R.id.RecId);

        re.setBackgroundColor(Values.getTheme().records);

        recordList.setText(UpdateRecords(Values.mode));

        recordsTxt.setTypeface(StartActivity.DotTxt);
        modeTxt.setTypeface(StartActivity.DotTxt);
        recordList.setTypeface(StartActivity.DotTxt);

        recordList.setTextColor(Values.getTheme().TextColor);
        recordsTxt.setTextColor(Values.getTheme().TextColor);
        modeTxt.setTextColor(Values.getTheme().TextColor);

        modeB.setColorFilter(Values.getTheme().exit);

        recordsTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*25));
        recordList.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*21));
        modeTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*11));
        StartActivity.setParamsByFactor(modeB,80,80);
    }

    public void onModeButtonClick(View v){
        v.startAnimation(StartActivity.button_clicked);

        mode=Mode.incMode(mode);
        switch(mode){
            case CLASSIC    :modeTxt.setText("CLASSIC");break;
            case BATTLE     :modeTxt.setText("BATTLE");break;
            case CAMPAIGN   :modeTxt.setText("CAMPAIGN");break;
            case MULTIPLAYER:modeTxt.setText("2 Players");break;
        }
        recordList.setText(UpdateRecords(mode));

        final ValueAnimator TextAnim = ValueAnimator.ofFloat(-50f, 0f).setDuration(350);
        TextAnim.setInterpolator(new DecelerateInterpolator());
        TextAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                   modeTxt.setTranslationX((Float) animation.getAnimatedValue());
                   recordList.setTranslationX((Float) animation.getAnimatedValue()*7*(float)Values.IncreaceFactor);
            }
        });
        TextAnim.start();

    }


    public String UpdateRecords(Mode mode){
        int[] temp;
        String s="";
        switch(mode){
            case CLASSIC :temp=Values.classicRec;break;
            case BATTLE  :temp=Values.battleRec;break;
            case CAMPAIGN:temp=Values.campaignRec;break;
            case MULTIPLAYER:temp=Values.multiplayerRec;break;
            default: temp=Values.classicRec;
        }
        for(int i=0;i<5;++i) {
            s += (i + 1) + "  "+temp[i] + System.getProperty ("line.separator");
        }
        return s;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.wait_anim,R.anim.exit_activity);
        finish();
    }
}
