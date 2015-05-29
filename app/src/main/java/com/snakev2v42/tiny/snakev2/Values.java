package com.snakev2v42.tiny.snakev2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;

import com.snakev2v42.tiny.snakev2.GoogleServices.AdMob;
import com.snakev2v42.tiny.snakev2.ModeProperties.Battle;
import com.snakev2v42.tiny.snakev2.ModeProperties.Campaign;
import com.snakev2v42.tiny.snakev2.ModeProperties.Classic;
import com.snakev2v42.tiny.snakev2.ModeProperties.CurrentMode;
import com.snakev2v42.tiny.snakev2.ModeProperties.Multiplayer;
import com.snakev2v42.tiny.snakev2.menusAndSettings.Mode;
import com.snakev2v42.tiny.snakev2.menusAndSettings.Theme;
/**
 * Created by yuriy on 4/24/2015.
 */
public abstract class Values {

    public static int CellWidth,CellHeight,Width,Height,SnakeSize,BoBaHeight;
    public static float dens;
    public static int AMOUNT_OF_SNAKES=0,AMOUNT_OF_MEAL=0,AMOUNT_OF_THEMES=5;
    public static boolean Volume =true;
    public static int lvl =8;
    public static Mode mode = Mode.CLASSIC;
    public static Theme[] themes=null;
    public static int themeId=0;
    public static int snakeSpeed;
    public static double IncreaceFactor=1;
    static SharedPreferences settings;
    public static boolean savedGame=false;

    public static MediaPlayer eat=null,crash;
    public static CurrentMode currentMode;

    public static void init(Context c){
        DisplayMetrics display = c.getResources().getDisplayMetrics();
        Width=display.widthPixels;
        Height=display.heightPixels;
        dens=display.density;
        CellWidth=40;CellHeight=(int)((Height-30*dens)/Width*CellWidth);
        SnakeSize=Width/CellWidth;
        BoBaHeight=Height-CellHeight*SnakeSize;
        if(themes==null) {
            themes = new Theme[AMOUNT_OF_THEMES];
            //                       volume       continue       records        levels        theme         mode          exit          info        TextColor    buttonColor              snakeColors
            themes[1] = new Theme(c("#485864"), c("#485864"), c("#000000"), c("#485864"), c("#485864"), c("#929fa6"), c("#bb0a01"), c("#485864"), c("#f5f5f5"), c("#bb0a01"), new int[]{c("#bb0a01"), c("#485864"), c("#929fa6")});
            themes[0] = new Theme(c("#f73a18"), c("#1a2139"), c("#333e5b"), c("#9d9683"), c("#798190"), c("#333e5b"), c("#f73a18"), c("#333e5b"), c("#f5f5f5"), c("#f73a18"), new int[]{c("#f73a18"), c("#9d9683"), c("#798190"), c("#333e5b")});
            themes[2] = new Theme(c("#607d8b"), c("#33691e"), c("#8bc34a"), c("#8bc34a"), c("#9e9e9e"), c("#8bc34a"), c("#000000"), c("#4caf50"), c("#f5f5f5"), c("#000000"), new int[]{c("#9e9e9e"), c("#4caf50"), c("#8bc34a"), c("#607d8b"), c("#33691e")});
            themes[3] = new Theme(c("#bab3d9"), c("#818692"), c("#434945"), c("#a6b7ae"), c("#818692"), c("#bab3d9"), c("#4a683a"), c("#434945"), c("#f5f5f5"), c("#bab3d9"), new int[]{c("#bab3d9"), c("#4a683a"), c("#818692"), c("#434945")});
            themes[4] = new Theme(c("#bdac9c"), c("#d1a701"), c("#1c1e26"), c("#9d815b"), c("#fdeb37"), c("#d1a701"), c("#1c1e26"), c("#9d815b"), c("#f5f5f5"), c("#a6b7ae"), new int[]{c("#d1a701"), c("#bdac9c"), c("#9d815b"), c("#fdeb37")});
        }
        settings = c.getSharedPreferences("SNAKEV2_SETTINGS", Context.MODE_PRIVATE);
        //loading settings
        themeId=loadInt(themeId, "themeId");
        Volume=loadBool(Volume, "Volume");
        mode=Mode.IntToMode(loadInt(Mode.ModeToInt(mode), "mode"));
        lvl=loadInt(lvl, "lvl");
        Classic.load();
        Battle.load();
        Campaign.load();
        Multiplayer.load();

        if(eat ==null){
            eat = MediaPlayer.create(c.getApplicationContext(), R.raw.eat);
            crash = MediaPlayer.create(c.getApplicationContext(), R.raw.crash);

            /*music.setAudioStreamType(AudioManager.STREAM_MUSIC);
            music.setLooping(true);*/
        }
        AdMob.init(c);
    }

    public static void saveInt(int integer,String key){
        settings.edit().putInt(key, integer).apply();
    }
    public static void saveBool(boolean bool,String key){
        settings.edit().putBoolean(key, bool).apply();
    }
    public static int loadInt(int integer,String key){
        if(settings.contains(key))
            integer=settings.getInt(key,0);
        return integer;
    }
    public static boolean loadBool(boolean bool,String key){
        if(settings.contains(key))
            bool=settings.getBoolean(key, false);
        return bool;
    }

    public static CurrentMode setupMode() {
        switch (Values.mode){
            case CLASSIC:
                currentMode = new Classic();
                break;
            case BATTLE:
                currentMode = new Battle();
                break;
            case MULTIPLAYER:
                currentMode = new Multiplayer();
                break;
            case CAMPAIGN:
                currentMode = new Campaign();
        }
        return currentMode;
    }

    public static Theme getTheme(){
        Log.d("Values ",""+themeId);
        return themes[Values.themeId];
    }
    static int c(String s){
        return Color.parseColor(s);
    }

    public static void saveSettings() {
        saveInt(themeId,"themeId");
        saveBool(Volume,"Volume");
        saveInt(Mode.ModeToInt(mode),"mode");
        saveInt(lvl,"lvl");
        Battle.save();
        Classic.save();
        Multiplayer.save();
        Campaign.save();
    }
    /*public static void SaveTheGame(){
        savedGame=true;
        switch(Values.mode){
            case CLASSIC :
                Classic.savedGame=true;
                Classic.save();
                break;
            case BATTLE :
                Battle.savedGame=true;
                Battle.save();
                break;
            case MULTIPLAYER :
                Multiplayer.savedGame=true;
                Multiplayer.save();
                break;
            case CAMPAIGN :
                Campaign.savedGame=true;
                Campaign.save();
                break;
        }
    }*/
}
