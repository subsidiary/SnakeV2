package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by yuriy on 4/25/2015.
 */
public class Meal {
    int color,size,x,y,type;
    boolean enabled=true;
    public Meal(int x,int y,int size,int color,boolean enabled){
        this.color=color;
        this.x=x;
        this.y=y;
        this.size=size;
        this.enabled=enabled;
        if(x==-1&&y==-1)generate();
    }
    public void eat(int x,int y){
        if((x>=this.x&&x<=this.x+size) && (y>=this.y&&y<=this.y+size))
            enabled=false;
    }
    public void generate(){
        x=new Random().nextInt(Values.CellWidth-(size+1));
        y=new Random().nextInt(Values.CellHeight-(size+1));
        enabled=true;
    }
    public void paintMeal(){
        Bitmap b=null;
        if(size==1)b=Bitmap.createBitmap(Bitmaps.MealParts[0]);
        for(int ux=0;ux< Values.SnakeSize;ux++)
            for(int uy=0;uy< Values.SnakeSize;uy++)
                if(b.getPixel(ux,uy)== Color.parseColor("#F44336"))b.setPixel(ux,uy,color);
        Bitmaps.DrawToMainB(b, Values.SnakeSize * x, Values.SnakeSize * y);
        if(b != null){b.recycle();b = null;}
    }
}
