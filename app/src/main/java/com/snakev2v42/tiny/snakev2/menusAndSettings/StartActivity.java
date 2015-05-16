package com.snakev2v42.tiny.snakev2.menusAndSettings;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snakev2v42.tiny.snakev2.GameActivity;
import com.snakev2v42.tiny.snakev2.R;
import com.snakev2v42.tiny.snakev2.Values;

import java.util.Random;

/**
 * Created by yuriy on 5/8/2015.
 */
public class StartActivity extends Activity {
    private static ImageButton level,exit,mode, records,continueb,theme,volume,info;
    private static TextView levelTxt,exitTxt,modeTxt, recordsTxt,themeTxt,continueTxt;
    private static ImageView volumeImg,infoImg;
    static Handler handler;
    public static Animation button_clicked;
    public static Typeface DotTxt;

    boolean breathAllowed = true,lockTheme=false;
    Thread breath = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if(breathAllowed)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        breathAnim(false);
                    }
                });
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
   });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        DotTxt = Typeface.createFromAsset(getAssets(),"fonts/high_speed.TTF");
        continueb =(ImageButton)findViewById(R.id.Comtinue);
        exit =(ImageButton)findViewById(R.id.Exit);
        mode =(ImageButton)findViewById(R.id.Mode);
        records =(ImageButton)findViewById(R.id.Records);
        level =(ImageButton)findViewById(R.id.Level);
        theme =(ImageButton)findViewById(R.id.Theme);
        volume =(ImageButton)findViewById(R.id.Volume);
        info =(ImageButton)findViewById(R.id.Info);

        levelTxt =(TextView)findViewById(R.id.LevelTxt);
        continueTxt =(TextView)findViewById(R.id.ContinueTxt);
        themeTxt =(TextView)findViewById(R.id.ThemeTxt);
        exitTxt =(TextView)findViewById(R.id.ExitTxt);
        modeTxt =(TextView)findViewById(R.id.ModeTxt);
        recordsTxt =(TextView)findViewById(R.id.RecoerdsTxt);
        volumeImg =(ImageView)findViewById(R.id.VolumeImg);
        infoImg =(ImageView)findViewById(R.id.InfoImg);

        levelTxt.setTypeface(DotTxt);
        recordsTxt.setTypeface(DotTxt);
        modeTxt.setTypeface(DotTxt);
        exitTxt.setTypeface(DotTxt);
        themeTxt.setTypeface(DotTxt);
        continueTxt.setTypeface(DotTxt);

        Values.init(this);
        button_clicked= AnimationUtils.loadAnimation(this,R.anim.button_anim);
        handler = new Handler();

        double D = (double) Values.dens, factor = (Values.Width - 16 * D) / (524);
        if (factor < (Values.Height - 16 * D) / (300))
            factor = (Values.Height - 16 * D) / (300);
        Values.IncreaceFactor=factor;
        setParamsByFactor(continueb,200,200);setMarginsByFactor(continueb, 16, 16,0,0);
        setParamsByFactor(level    ,150,150);setMarginsByFactor(level, 216, 16, 0, 0);
        setParamsByFactor(mode     ,120,120);setMarginsByFactor(mode, 366, 16, 0, 0);
        setParamsByFactor(records  ,130,130);setMarginsByFactor(records, 170, 170, 0, 0);
        setParamsByFactor(info     , 60, 60);setMarginsByFactor(info, 456, 120, 0, 0);
        setParamsByFactor(volume   , 60, 60);setMarginsByFactor(volume, 24, 220, 0, 0);
        setParamsByFactor(exit     ,100,100);setMarginsByFactor(exit, 424, 200, 0, 0);
        setParamsByFactor(theme    ,120,120);setMarginsByFactor(theme, 316, 136, 0, 0);
        continueTxt.setTextSize(StartActivity.convertFromDp((float) Values.IncreaceFactor * 26));
        levelTxt.setTextSize(StartActivity.convertFromDp((float) Values.IncreaceFactor * 21));
        modeTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*17));
        recordsTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*20));
        themeTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*20));
        exitTxt.setTextSize(StartActivity.convertFromDp((float)Values.IncreaceFactor*21));

        if(!breath.isAlive())
            breath.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        returnDefaultButtonProperties();
        EnableButts(true);
        breathAllowed=true;
        setLables();
    }

    @Override
    protected void onPause() {
        super.onPause();
        breathAllowed=false;
        Values.saveSettings();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EnableButts(true);
        returnDefaultButtonProperties();
    }

    @Override
    public void onBackPressed() {
    }

    public void onContinueClick(final View v){
        EnableButts(false);

        float  x1=(Values.Width-(v.getX()+v.getWidth()/2)),
                x2=v.getX()+v.getWidth()/2,
                y1=Values.Height-(v.getY()+v.getHeight()/2),
                y2=v.getY()+v.getHeight()/2,
                xMax,yMax;
        if(x1>=x2)xMax=x1;else xMax=x2;
        if(y1>=y2)yMax=y1;else yMax=y2;

        final ValueAnimator CircleAnim = ValueAnimator.ofFloat(1, (float)(Math.sqrt(xMax*xMax+yMax*yMax)/(v.getWidth()/2))+1.2f).setDuration(1000);
        CircleAnim.setInterpolator(new DecelerateInterpolator());
        CircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setScaleX((Float) (animation.getAnimatedValue()));
                v.setScaleY((Float) (animation.getAnimatedValue()));
            }
        });
        v.bringToFront();

        final TextView txt=continueTxt;

        txt.bringToFront();
        txt.setTranslationX(-180f);
        txt.setText("Loading..");
        final ValueAnimator TextAnim = ValueAnimator.ofFloat(txt.getTranslationX(), 0f).setDuration(350);
        TextAnim.setInterpolator(new DecelerateInterpolator());
        TextAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                txt.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        TextAnim.start();
        CircleAnim.start();
        final Intent GoGame = new Intent(this, GameActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(GoGame);
                overridePendingTransition(R.anim.start_activity, R.anim.wait_anim);
            }
        }, 900);
    }

    public void onModeClick(View v){
        v.startAnimation(button_clicked);
        Values.mode=Mode.incMode(Values.mode);
        switch(Values.mode){
            case CLASSIC :modeTxt.setText("CLASSIC");break;
            case BATTLE  :modeTxt.setText("BATTLE");break;
            case CAMPAIGN:modeTxt.setText("CAMPAIGN");break;
            case MULTIPLAYER:modeTxt.setText("2 Players");break;
        }

        modeTxt.setTranslationX(-50f);
        final ValueAnimator TextAnim = ValueAnimator.ofFloat(modeTxt.getTranslationX(), 0f).setDuration(350);
        TextAnim.setInterpolator(new DecelerateInterpolator());
        TextAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                modeTxt.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        TextAnim.start();
        Values.saveInt(Mode.ModeToInt(Values.mode),"mode");
    }

    public void onExitClick(final View v){
        EnableButts(false);


        final ValueAnimator CircleAnim = ValueAnimator.ofFloat(1,0f).setDuration(300);
        CircleAnim.setInterpolator(new DecelerateInterpolator());
        CircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setScaleX((Float) (animation.getAnimatedValue()));
                v.setScaleY((Float) (animation.getAnimatedValue()));
            }
        });

        final ValueAnimator TextAnim = ValueAnimator.ofFloat(0, 35f).setDuration(350);
        TextAnim.setInterpolator(new DecelerateInterpolator());
        TextAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                exitTxt.setTranslationX((Float) animation.getAnimatedValue());
                exitTxt.setAlpha((1 - (Float) animation.getAnimatedValue() / 35));
            }
        });
        TextAnim.start();
        CircleAnim.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 305);
    }

    public void onRecordsClick(final View v){
        EnableButts(false);

        float  x1=(Values.Width-(v.getX()+v.getWidth()/2)),
                x2=v.getX()+v.getWidth()/2,
                y1=Values.Height-(v.getY()+v.getHeight()/2),
                y2=v.getY()+v.getHeight()/2,
                xMax,yMax;
        if(x1>=x2)xMax=x1;else xMax=x2;
        if(y1>=y2)yMax=y1;else yMax=y2;

        final ValueAnimator CircleAnim = ValueAnimator.ofFloat(1, (float)(Math.sqrt(xMax*xMax+yMax*yMax)/(v.getWidth()/2))+1.2f).setDuration(700);
        CircleAnim.setInterpolator(new DecelerateInterpolator());
        CircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setScaleX((Float) (animation.getAnimatedValue()));
                v.setScaleY((Float) (animation.getAnimatedValue()));
            }
        });
        v.bringToFront();
        CircleAnim.start();
        final Intent GoSetts = new Intent(this,RecordsActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(GoSetts);
                overridePendingTransition(R.anim.start_activity,R.anim.wait_anim);
            }
        }, 650);
    }

    public void onVolumeClick(View v){
        volume.startAnimation(button_clicked);
        volumeImg.startAnimation(button_clicked);
        Values.Volume =!Values.Volume;
        if(Values.Volume)
            volumeImg.setImageResource(R.drawable.ic_volume_on);
        else
            volumeImg.setImageResource(R.drawable.ic_volume_off);
        Values.saveBool(Values.Volume,"Volume");
    }

    public void onLevelClick(View v){
        v.startAnimation(button_clicked);
        if(Values.lvl ==8)
            Values.lvl =1;
        else
            ++Values.lvl;
        levelTxt.setTranslationX(-70f);
        levelTxt.setText("Level"+Values.lvl);
        final ValueAnimator TextAnim = ValueAnimator.ofFloat(levelTxt.getTranslationX(), 0f).setDuration(350);
        TextAnim.setInterpolator(new DecelerateInterpolator());
        TextAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                levelTxt.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        TextAnim.start();
        Values.saveInt(Values.lvl,"lvl");
    }

    public void onThemeClick(View v){
        v.startAnimation(button_clicked);
        themeTxt.setTranslationX(-70f);
        themeTxt.setText("Theme");
        final ValueAnimator TextAnim = ValueAnimator.ofFloat(themeTxt.getTranslationX(), 0f).setDuration(350);
        TextAnim.setInterpolator(new DecelerateInterpolator());
        TextAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                themeTxt.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        TextAnim.start();
        if(!lockTheme) {
            if (Values.themeId == Values.AMOUNT_OF_THEMES - 1)
                Values.themeId = 0;
            else
                ++Values.themeId;
            lockTheme = true;
            breathAnim(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lockTheme=false;
                }
            },1200);
        }
        Values.saveInt(Values.themeId,"themeId");
    }

    public void onInfoClick(final View v){
        float  x1=(Values.Width-(v.getX()+v.getWidth()/2)),
                x2=v.getX()+v.getWidth()/2,
                y1=Values.Height-(v.getY()+v.getHeight()/2),
                y2=v.getY()+v.getHeight()/2,
                xMax,yMax;
        if(x1>=x2)xMax=x1;else xMax=x2;
        if(y1>=y2)yMax=y1;else yMax=y2;

        final ValueAnimator CircleAnim = ValueAnimator.ofFloat(1, (float)(Math.sqrt(xMax*xMax+yMax*yMax)/(v.getWidth()/2.2))+1.2f).setDuration(700);
        CircleAnim.setInterpolator(new DecelerateInterpolator());
        CircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setScaleX((Float) (animation.getAnimatedValue()));
                v.setScaleY((Float) (animation.getAnimatedValue()));
            }
        });
        v.bringToFront();

        CircleAnim.start();
        final Intent GoInfo = new Intent(this,InfoActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(GoInfo);
                overridePendingTransition(R.anim.start_activity, R.anim.wait_anim);
            }
        }, 1300);
    }

    public void breathAnim(final boolean changeColors){
        int[] sequence = {0,20,200,280,380,440,540,600};
        int delay=570;
        final ValueAnimator CircleAnimV = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimV.setInterpolator(new LinearInterpolator());
        CircleAnimV.setStartDelay(sequence[0]);
        CircleAnimV.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    volume.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    volume.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    volumeImg.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    volumeImg.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors){
                        volume.setColorFilter(Values.getTheme().volume);
                    }
                } else {
                    volume.setScaleX((Float) (animation.getAnimatedValue()));
                    volume.setScaleY((Float) (animation.getAnimatedValue()));
                    volumeImg.setScaleX((Float) (animation.getAnimatedValue()));
                    volumeImg.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });

        final ValueAnimator CircleAnimC = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimC.setInterpolator(new LinearInterpolator());
        CircleAnimC.setStartDelay(sequence[1]);
        CircleAnimC.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    continueb.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    continueb.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors){
                        continueb.setColorFilter(Values.getTheme().continueb);
                        continueTxt.setTextColor(Values.getTheme().TextColor);
                    }
                } else {
                    continueb.setScaleX((Float) (animation.getAnimatedValue()));
                    continueb.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });

        final ValueAnimator CircleAnimR = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimR.setInterpolator(new LinearInterpolator());
        CircleAnimR.setStartDelay(sequence[2]);
        CircleAnimR.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    records.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    records.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors){
                        records.setColorFilter(Values.getTheme().records);
                        recordsTxt.setTextColor(Values.getTheme().TextColor);
                    }
                } else {
                    records.setScaleX((Float) (animation.getAnimatedValue()));
                    records.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });

        final ValueAnimator CircleAnimL = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimL.setInterpolator(new LinearInterpolator());
        CircleAnimL.setStartDelay(sequence[3]);
        CircleAnimL.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    level.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    level.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors){
                        level.setColorFilter(Values.getTheme().levels);
                        levelTxt.setTextColor(Values.getTheme().TextColor);
                    }
                } else {
                    level.setScaleX((Float) (animation.getAnimatedValue()));
                    level.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });

        final ValueAnimator CircleAnimT = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimT.setInterpolator(new LinearInterpolator());
        CircleAnimT.setStartDelay(sequence[4]);
        CircleAnimT.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    theme.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    theme.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors){
                        theme.setColorFilter(Values.getTheme().theme);
                        themeTxt.setTextColor(Values.getTheme().TextColor);
                    }
                } else {
                    theme.setScaleX((Float) (animation.getAnimatedValue()));
                    theme.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });

        final ValueAnimator CircleAnimM = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimM.setInterpolator(new LinearInterpolator());
        CircleAnimM.setStartDelay(sequence[5]);
        CircleAnimM.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    mode.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    mode.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors){
                        mode.setColorFilter(Values.getTheme().mode);
                        modeTxt.setTextColor(Values.getTheme().TextColor);
                    }
                } else {
                    mode.setScaleX((Float) (animation.getAnimatedValue()));
                    mode.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });

        final ValueAnimator CircleAnimE = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimE.setInterpolator(new LinearInterpolator());
        CircleAnimE.setStartDelay(sequence[6]);
        CircleAnimE.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    exit.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    exit.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors){
                        exit.setColorFilter(Values.getTheme().exit);
                        exitTxt.setTextColor(Values.getTheme().TextColor);
                    }
                } else {
                    exit.setScaleX((Float) (animation.getAnimatedValue()));
                    exit.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });

        final ValueAnimator CircleAnimI = ValueAnimator.ofFloat(1, 1.2f).setDuration(delay);
        CircleAnimI.setInterpolator(new LinearInterpolator());
        CircleAnimI.setStartDelay(sequence[7]);
        CircleAnimI.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                    info.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    info.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    infoImg.setScaleX(2.2f - (Float) (animation.getAnimatedValue()));
                    infoImg.setScaleY(2.2f - (Float) (animation.getAnimatedValue()));
                    if(changeColors)
                        info.setColorFilter(Values.getTheme().info);
                } else {
                    info.setScaleX((Float) (animation.getAnimatedValue()));
                    info.setScaleY((Float) (animation.getAnimatedValue()));
                    infoImg.setScaleX((Float) (animation.getAnimatedValue()));
                    infoImg.setScaleY((Float) (animation.getAnimatedValue()));
                }
            }
        });
        CircleAnimV.start();
        CircleAnimR.start();
        CircleAnimM.start();
        CircleAnimE.start();
        CircleAnimI.start();
        CircleAnimT.start();
        CircleAnimL.start();
        CircleAnimC.start();
    }


    public static void setLables(){
        switch(Values.mode){
            case CLASSIC :modeTxt.setText("CLASSIC");break;
            case BATTLE  :modeTxt.setText("BATTLE");break;
            case CAMPAIGN:modeTxt.setText("CAMPAIGN");break;
            case MULTIPLAYER:modeTxt.setText("2 Players");break;
        }
        levelTxt.setText("LEVEL"+Values.lvl);
        if(Values.Volume)
            volumeImg.setImageResource(R.drawable.ic_volume_on);
        else
            volumeImg.setImageResource(R.drawable.ic_volume_off);

        continueb.setColorFilter(Values.getTheme().continueb);
        volume.setColorFilter(Values.getTheme().volume);
        records.setColorFilter(Values.getTheme().records);
        info.setColorFilter(Values.getTheme().info);
        theme.setColorFilter(Values.getTheme().theme);
        level.setColorFilter(Values.getTheme().levels);
        exit.setColorFilter(Values.getTheme().exit);
        mode.setColorFilter(Values.getTheme().mode);
        if(Values.savedGame)
            continueTxt.setText("CONTINUE");
        else
            continueTxt.setText("NEW GAME");
    }
    public static void returnDefaultButtonProperties(){
        levelTxt.setTranslationX(0);
        exitTxt.setTranslationX(0);
        recordsTxt.setTranslationX(0);
        modeTxt.setTranslationX(0);
        continueb.setTranslationX(0);
        info.setTranslationX(0);
        volume.setTranslationX(0);
        theme.setTranslationX(0);

        level.setScaleX(1);
        level.setScaleY(1);
        exit.setScaleX(1);
        exit.setScaleY(1);
        records.setScaleX(1);
        records.setScaleY(1);
        mode.setScaleX(1);
        continueb.setScaleX(1);
        continueb.setScaleY(1);
        info.setScaleX(1);
        info.setScaleY(1);
        theme.setScaleX(1);
        theme.setScaleY(1);
        volume.setScaleX(1);
        volume.setScaleY(1);
        continueTxt.bringToFront();
        recordsTxt.bringToFront();
        levelTxt.bringToFront();
        infoImg.bringToFront();
        setLables();
    }

    public void EnableButts(boolean enable){
        level.setEnabled(enable);
        exit.setEnabled(enable);
        records.setEnabled(enable);
        mode.setEnabled(enable);
        volume.setEnabled(enable);
        info.setEnabled(enable);
        continueb.setEnabled(enable);
        theme.setEnabled(enable);
        breathAllowed=enable;
    }
    public static float convertFromDp(float input) {
        return ((input - 0.5f) / Values.dens);
    }
    public static void setMarginsByFactor(View v,int l,int t,int r,int b){
        setMargins(v,(int)(Values.IncreaceFactor*l),(int)(Values.IncreaceFactor*t),(int)(Values.IncreaceFactor*r),(int)(Values.IncreaceFactor*b));
    }
    public static void setParamsByFactor(View v,int height,int width){
        setParams(v, (int) (height*Values.IncreaceFactor), (int) (width*Values.IncreaceFactor));
    }
    public static void setMargins(View v,int l,int t,int r,int b){
        RelativeLayout.LayoutParams parm = (RelativeLayout.LayoutParams)v.getLayoutParams();
        parm.setMargins(l, t, r, b);
        v.setLayoutParams(parm);
    }
    public static void setParams(View v,int height,int width){
        v.getLayoutParams().height=height;v.getLayoutParams().width=width;
    }
}
