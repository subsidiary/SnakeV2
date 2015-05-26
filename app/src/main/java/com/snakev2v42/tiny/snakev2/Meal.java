package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by yuriy on 4/25/2015.
 */
public class Meal {
    public Point p;
    public int color;
    public int size;

    public Meal(Point p,int size,int color){
        this.color=color;
        this.size=size;
        this.p=p;
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
        while(checkPlace(p)) {
            p.x = GameActivity.random.nextInt(Values.CellWidth - (size + 1));
            p.y = GameActivity.random.nextInt(Values.CellHeight - (size + 1));
        }
    }
    public boolean checkPlace(Point p){
        boolean flag=false;
        for(int x=0;x<size;++x)
            for(int y=0;y<size;++y)
                if(Map.CheckMapCell(Point.plus(p,x,y))!=Cell.NOTHING)
                    flag=true;
        return flag;
    }
    /*public void draw(Canvas canvas){
        canvas.save();
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawCircle((p.x+0.5f)*Values.SnakeSize,(p.y+0.5f)*Values.SnakeSize,(size/3.5f)*Values.SnakeSize,paint);
        canvas.restore();
    }*/
}
