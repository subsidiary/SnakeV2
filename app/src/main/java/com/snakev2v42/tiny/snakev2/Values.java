package com.snakev2v42.tiny.snakev2;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Values {
    static int CellWidth,CellHeight,Width,Height,SnakeSize,BoBaHeight;
    static float dens;
    static boolean FirstLoad=true;
    static int delayTime=230;
    static int AMOUNT_OF_SNAKES=1;
    static int AMOUNT_OF_MEAL=1;
    static boolean game=true;

    public static void init(int width,int height,float density){
        Width=width;Height=height;dens=density;
        CellWidth=40;CellHeight=(int)((Height-30*dens)/Width*CellWidth);
        SnakeSize=Width/CellWidth;
        BoBaHeight=Height-CellHeight*SnakeSize;
    }
}
