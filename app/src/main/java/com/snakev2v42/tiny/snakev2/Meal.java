package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by yuriy on 4/25/2015.
 */
public class Meal {
    public Point p;
    int color,size,type;
    boolean enabled=true;

    public Meal(Point p,int size,int color,boolean enabled){
        this.color=color;
        this.size=size;
        this.enabled=enabled;
        this.p=p;
    }
    public Meal(int size,int color){
        this.size=size;
        this.color=color;
        generate();
    }
    public void eat(int x,int y){
        if((x>=p.x&&x<=p.x+size) && (y>=p.y&&y<=p.y+size))
            enabled=false;
    }
    public void generate(){
        p.x=new Random().nextInt(Values.CellWidth-(size+1));
        p.y=new Random().nextInt(Values.CellHeight-(size+1));
        enabled=true;
    }
    public void paintMeal(){
        Bitmap b;
        int j;
        switch (size){
            case 1 : j = 0; break;
            default: j = 0;
        }

        b=Bitmap.createBitmap(Bitmaps.MealParts[j]);
        for(int ux=0;ux< Values.SnakeSize;ux++)
            for(int uy=0;uy< Values.SnakeSize;uy++)
                if(b.getPixel(ux,uy)== Color.parseColor("#F44336"))b.setPixel(ux,uy,color);
        Bitmaps.DrawToMainB(b, Values.SnakeSize * p.x, Values.SnakeSize * p.y);
        if(b != null)
            b.recycle();
    }
}
