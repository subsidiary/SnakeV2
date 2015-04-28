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

    public Meal(Point p,int size,int color){
        this.color=color;
        this.size=size;
        this.p=p;
    }
    public Meal(int size,int color){
        this.size=size;
        this.color=color;
        this.p=new Point(0,0);
        generate();
    }
    public boolean eat(Point p){
        if((p.x>=this.p.x&&p.x<=this.p.x+(size-1)) && (p.y>=this.p.y&&p.y<=this.p.y+(size-1)))
            return true;
        else
            return false;
    }
    public void generate(){
        p.x=new Random().nextInt(Values.CellWidth-(size+1));
        p.y=new Random().nextInt(Values.CellHeight-(size+1));
    }
    public void PaintMeal(){
        Bitmap b;
        int j;
        switch (size){
            case 1 : j = 0; break;
            default: j = 0;
        }

        b=Bitmap.createBitmap(Bitmaps.MealParts[j]);
        for(int ux=0;ux< b.getWidth();ux++)
            for(int uy=0;uy< b.getHeight();uy++)
                if(b.getPixel(ux,uy)== Color.parseColor("#3F51B5"))b.setPixel(ux,uy,color);
        Bitmaps.DrawToMainB(b, Values.SnakeSize * p.x, Values.SnakeSize * p.y);
        if(b != null)
            b.recycle();
    }
}
