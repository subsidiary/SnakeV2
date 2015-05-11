package com.snakev2v42.tiny.snakev2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.DisplayMetrics;

import com.snakev2v42.tiny.snakev2.menusAndSettings.Mode;
import com.snakev2v42.tiny.snakev2.menusAndSettings.Theme;

/**
 * Created by yuriy on 4/24/2015.
 */
public abstract class Values {

    public static int CellWidth,CellHeight,Width,Height,SnakeSize,BoBaHeight;
    public static float dens;
    public static int AMOUNT_OF_SNAKES=0,AMOUNT_OF_MEAL=0,AMOUNT_OF_THEMES=3;
    public static boolean Volume =true;
    public static int lvl =8;
    public static Mode mode = Mode.CLASSIC;
    public static int[] classicRec={0,0,0,0,0},battleRec={0,0,0,0,0},multiplayerRec={0,0,0,0,0},campaignRec={0,0,0,0,0};
    public static Theme[] themes;
    public static int themeId=0;
    public static double IncreaceFactor;
    static SharedPreferences settings;

    public static void init(Context c){
        DisplayMetrics display = c.getResources().getDisplayMetrics();
        Width=display.widthPixels;
        Height=display.heightPixels;
        dens=display.density;
        CellWidth=40;CellHeight=(int)((Height-30*dens)/Width*CellWidth);
        SnakeSize=Width/CellWidth;
        BoBaHeight=Height-CellHeight*SnakeSize;
        themes= new Theme[AMOUNT_OF_THEMES];
        //                     volume      continue       records      levels       theme        mode           exit         info       TextColor
        themes[0]=new Theme(c("#485864"),c("#485864"),c("#000000"),c("#485864"),c("#485864"),c("#929fa6"),c("#bb0a01"),c("#485864"),c("#f5f5f5"));
        themes[1]=new Theme(c("#f73a18"),c("#1a2139"),c("#333e5b"),c("#9d9683"),c("#798190"),c("#333e5b"),c("#f73a18"),c("#333e5b"),c("#f5f5f5"));
        themes[2]=new Theme(c("#607d8b"),c("#33691e"),c("#8bc34a"),c("#8bc34a"),c("#9e9e9e"),c("#8bc34a"),c("#000000"),c("#4caf50"),c("#f5f5f5"));

        settings = c.getSharedPreferences("SNAKEV2_SETTINGS", Context.MODE_PRIVATE);
        themeId=loadInt(themeId,"themeId");
        Volume=loadBool(Volume,"Volume");
        mode=Mode.IntToMode(loadInt(Mode.ModeToInt(mode),"mode"));
        lvl=loadInt(lvl,"lvl");
        for(int i=0;i<5;++i){
            classicRec[i]    =loadInt(classicRec[i]    ,"classicRec["+i+"]");
            battleRec[i]     =loadInt(campaignRec[i], "campaignRec[" + i + "]");
            multiplayerRec[i]=loadInt(multiplayerRec[i],"multiplayerRec["+i+"]");
            campaignRec[i]   =loadInt(campaignRec[i]   ,"campaignRec["+i+"]");
        }
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



    public static Theme getTheme(){
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
        for(int i=0;i<5;++i){
            saveInt(classicRec[i],"classicRec["+i+"]");
            saveInt(battleRec[i],"battleRec["+i+"]");
            saveInt(multiplayerRec[i],"multiplayerRec["+i+"]");
            saveInt(campaignRec[i],"campaignRec["+i+"]");
        }
    }
}
