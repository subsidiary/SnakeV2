package com.snakev2v42.tiny.snakev2;

import android.graphics.Bitmap;
import android.graphics.Color;
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
    public Bitmap chooseTheRightPart(int id) {
        Bitmap b= Bitmaps.RedParts[1];
        if(id == 0)
            switch (sP.get(id).vec) {
                case 1:b=Bitmaps.RedParts[1];break;
                case 2:b=Bitmaps.RedParts[2];break;
                case 3:b=Bitmaps.RedParts[3];break;
                case 4:b=Bitmaps.RedParts[4];break;
            }else
        if(id == length){
            if(sP.get(id).vec%10==1)b=Bitmaps.RedParts[5];
            if(sP.get(id).vec%10==3)b=Bitmaps.RedParts[7];
            if(sP.get(id).vec%10==2)b=Bitmaps.RedParts[6];
            if(sP.get(id).vec%10==4)b=Bitmaps.RedParts[8];
        }else{
            if(sP.get(id).vec==2|| sP.get(id).vec==4)b=Bitmaps.RedParts[10];
            if(sP.get(id).vec==1|| sP.get(id).vec==3)b=Bitmaps.RedParts[9];
            if(sP.get(id).vec==12|| sP.get(id).vec==43)b=Bitmaps.RedParts[11];
            if(sP.get(id).vec==14|| sP.get(id).vec==23)b=Bitmaps.RedParts[12];
            if(sP.get(id).vec==34|| sP.get(id).vec==21)b=Bitmaps.RedParts[13];
            if(sP.get(id).vec==41|| sP.get(id).vec==32)b=Bitmaps.RedParts[14];
        }
        //if(ui == length+1) b=Bitmaps.emptyBit;
        return b;
    }

    public void PaintSnake() {
        for (int i = length; i >= 1; i--) {
            Bitmaps.DrawToMainB(chooseTheRightPart(i), Values.SnakeSize * sP.get(i).x, Values.SnakeSize * sP.get(i).y);
        }
    }
}
