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
    public Bitmap bitmap;

    public Meal(Point p,int size,int color){
        this.color=color;
        this.size=size;
        this.p=p;
        bitmap=Bitmap.createBitmap(Bitmaps.MealParts[0]);
        for (int ux = 0; ux < bitmap.getWidth(); ux++)
            for (int uy = 0; uy < bitmap.getHeight(); uy++) {
                if (bitmap.getPixel(ux, uy) == Color.parseColor("#3F51B5"))
                    bitmap.setPixel(ux, uy, color);
            }
    }
    public Meal(int size,int color){
        this(new Point(1,1),size,color);
        generate();
    }
    public boolean eat(Point p){
        if((p.x>=this.p.x&&p.x<=this.p.x+(size-1)) && (p.y>=this.p.y&&p.y<=this.p.y+(size-1)))
            return true;
        else
            return false;
    }
    public void generate(){
        Map.MakeNewMap();
        while(Map.CheckMapCell(p)!=Cell.NOTHING) {
            p.x = new Random().nextInt(Values.CellWidth - (size + 1));
            p.y = new Random().nextInt(Values.CellHeight - (size + 1));
        }
    }
    public void PaintMeal(){
        int j;
        switch (size){
            case 1 : j = 0; break;
            default: j = 0;
        }

        Bitmaps.DrawToMainB(bitmap, Values.SnakeSize * p.x, Values.SnakeSize * p.y);
    }
}
