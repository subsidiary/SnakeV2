package com.snakev2v42.tiny.snakev2.menusAndSettings;

import android.util.Log;

import java.util.Random;

/**
 * Created by yuriy on 5/10/2015.
 */
public class Theme {
    public int[] themeColors,snakeColors;
    public int AMOUNT_OF_THEMECOLORS,AMOUNT_OF_SNAKECOLORS;
    public int volume,info,mode,exit,continueb,levels,records,theme,BoBar,controlButtons,buttonColor;
    public int TextColor;


    public Theme(int volume,int continueb,int records,int levels,int theme,int mode,int exit,int info,int TextColor,int buttonColor,int[] snakeColors){
        this.volume=volume;
        this.levels=levels;
        this.continueb=continueb;
        this.info=info;
        this.theme=theme;
        this.records=records;
        this.mode=mode;
        this.exit=exit;
        this.TextColor=TextColor;
        this.buttonColor=buttonColor;
        BoBar=buttonColor;
        controlButtons=buttonColor;
        themeColors =new int[8];
        this.snakeColors = snakeColors;
        AMOUNT_OF_SNAKECOLORS=snakeColors.length;
        themeColors[0]=volume;
        themeColors[1]=continueb;
        themeColors[2]=records;
        themeColors[3]=levels;
        themeColors[4]=theme;
        themeColors[5]=mode;
        themeColors[6]=info;
        themeColors[7]=exit;

        boolean[] temp = new boolean[8];

        for(int i=0;i<7;++i){
            for(int j=i+1;j<8;++j)
            if ( (themeColors[i] == themeColors[j]) && themeColors[j] != -1) {
                temp[j] = true;
            }
        }
        AMOUNT_OF_THEMECOLORS=-1;
        for(int i=0;i<8;++i){
            if(!temp[i]){
                if(themeColors[i]!=buttonColor)
                themeColors[++AMOUNT_OF_THEMECOLORS]=themeColors[i];
            }
        }
        Log.d("AMOUNT_OF_COLORS"," "+AMOUNT_OF_THEMECOLORS);
    }
    public int getColor(){
        return themeColors[new Random().nextInt(AMOUNT_OF_THEMECOLORS)];
    }
    public int[]getSnakeColors(){
        return snakeColors;
    }
}
