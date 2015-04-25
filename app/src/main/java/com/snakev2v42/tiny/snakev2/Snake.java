package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Snake {
    int length,vector,preVector,color;
    ArrayList<Part> sP=new ArrayList<Part>();

//      1   Rotated(cornered) part of snake has a combinated vector.For example "23", which means that this part goes
//    4   2 from the right(2) corner to to the bottom(3);
//      3
    public Snake(int StartX,int StartY,int vector,int length,int color) {
        for(int i=0;i<=length;i++)
            sP.add(new Part(StartX+i,StartY,vector));
        this.vector=vector;
        this.color=color;
        this.length=length;
    }

    public void moveSnake(){
        for(int i=length;i>=1;i--){
            if(sP.get(i).vec%10==1){--sP.get(i).y;if(sP.get(i).y<0) sP.get(i).y=Values.CellHeight-1;}
            if(sP.get(i).vec%10==3){++sP.get(i).y;if(sP.get(i).y>Values.CellHeight-1) sP.get(i).y=0;}
            if(sP.get(i).vec%10==2){++sP.get(i).x;if(sP.get(i).x>Values.CellWidth-1) sP.get(i).x=0;}
            if(sP.get(i).vec%10==4){--sP.get(i).x;if(sP.get(i).x<0) sP.get(i).x=Values.CellWidth-1;}
            sP.get(i).vec=sP.get(i-1).vec;
        }
        //Making combinated vector if Snake was roted;
        if (sP.get(0).vec != vector) sP.get(1).vec = sP.get(1).vec  * 10 + vector;
        sP.get(0).vec=vector;
        preVector=vector;
        if(sP.get(0).vec%10==1){--sP.get(0).y;if(sP.get(0).y<0) sP.get(0).y=Values.CellHeight-1;}
        if(sP.get(0).vec%10==3){++sP.get(0).y;if(sP.get(0).y>Values.CellHeight-1) sP.get(0).y=0;}
        if(sP.get(0).vec%10==2){++sP.get(0).x;if(sP.get(0).x>Values.CellWidth-1) sP.get(0).x=0;}
        if(sP.get(0).vec%10==4){--sP.get(0).x;if(sP.get(0).x<0) sP.get(0).x=Values.CellWidth-1;}
    }
    
    public void Grow(int AddWidth){
        int x,y,vec;
        while (AddWidth!=0){
            vec=sP.get(length-1).vec;
            y=sP.get(length-1).y;
            x=sP.get(length-1).x;
            if(vec%10==1){++y;if(y>Values.CellHeight-1) y=0;}
            if(vec%10==3){--y;if(y<0) y=Values.CellHeight-1;}
            if(vec%10==2){--x;if(x<0) x=Values.CellWidth-1;}
            if(vec%10==4){++x;if(x>Values.CellWidth-1) x=0;}
            sP.add(new Part(x,y,vec));
            ++length;
            --AddWidth;
        }
    }


    //GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS____GRAPHICS
    public void PaintSnake() {
        Bitmap b=null;
        for (int i = length; i >= 0; i--) {
            if(i == 0)
                switch (sP.get(i).vec) {
                    case 1:b=Bitmap.createBitmap(Bitmaps.SnakeParts[0]);break;
                    case 2:b=Bitmap.createBitmap(Bitmaps.SnakeParts[1]);break;
                    case 3:b=Bitmap.createBitmap(Bitmaps.SnakeParts[2]);break;
                    case 4:b=Bitmap.createBitmap(Bitmaps.SnakeParts[3]);break;
                }else
            if(i == length)
                switch (sP.get(i).vec%10){
                    case 1:b=Bitmap.createBitmap(Bitmaps.SnakeParts[6]);break;
                    case 3:b=Bitmap.createBitmap(Bitmaps.SnakeParts[4]);break;
                    case 2:b=Bitmap.createBitmap(Bitmaps.SnakeParts[7]);break;
                    case 4:b=Bitmap.createBitmap(Bitmaps.SnakeParts[5]);break;
            }else {
                if (sP.get(i).vec == 2 || sP.get(i).vec == 4)   b=Bitmap.createBitmap(Bitmaps.SnakeParts[ 8]);else
                if (sP.get(i).vec == 1 || sP.get(i).vec == 3)   b=Bitmap.createBitmap(Bitmaps.SnakeParts[ 9]);else
                if (sP.get(i).vec == 12 || sP.get(i).vec == 43) b=Bitmap.createBitmap(Bitmaps.SnakeParts[10]);else
                if (sP.get(i).vec == 14 || sP.get(i).vec == 23) b=Bitmap.createBitmap(Bitmaps.SnakeParts[13]);else
                if (sP.get(i).vec == 34 || sP.get(i).vec == 21) b=Bitmap.createBitmap(Bitmaps.SnakeParts[12]);else
                if (sP.get(i).vec == 41 || sP.get(i).vec == 32) b=Bitmap.createBitmap(Bitmaps.SnakeParts[11]);
            }
            for(int ux=0;ux<b.getWidth();ux++)
                for(int uy=0;uy<b.getHeight();uy++){
                       if(b.getPixel(ux,uy)== Color.parseColor("#f5f5f5"))b.setPixel(ux,uy,color);
                }

            Bitmaps.DrawToMainB(b, Values.SnakeSize * sP.get(i).x, Values.SnakeSize * sP.get(i).y);
            if(b != null){b.recycle();b = null;}
        }
    }
}
