package com.snakev2v42.tiny.snakev2.menusAndSettings;

import java.util.Random;

/**
 * Created by yuriy on 5/10/2015.
 */
public class Theme {
    public static int[] snakeColors;
    public int volume,info,mode,exit,continueb,levels,records,theme;
    public int TextColor;

    public Theme(int volume,int continueb,int records,int levels,int theme,int mode,int exit,int info,int TextColor){
        this.volume=volume;
        this.levels=levels;
        this.continueb=continueb;
        this.info=info;
        this.theme=theme;
        this.records=records;
        this.mode=mode;
        this.exit=exit;
        this.TextColor=TextColor;
        snakeColors=new int[8];
        snakeColors[0]=volume;
        snakeColors[1]=continueb;
        snakeColors[2]=records;
        snakeColors[3]=levels;
        snakeColors[4]=theme;
        snakeColors[5]=mode;
        snakeColors[6]=exit;
        snakeColors[7]=info;
    }

    public static int getColor(){
        return snakeColors[new Random().nextInt(8)];
    }


}
