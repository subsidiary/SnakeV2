package com.snakev2v42.tiny.snakev2;

import android.graphics.Color;
import java.util.ArrayList;

/**
 * Created by yuriy on 4/24/2015.
 */
public class Snake {
    int length,vector,preVector;
    Color color;
    ArrayList<Part> sP=new ArrayList<Part>();

//      1   Rotated(cornered) part of snake has a combinated vector.For example "23", which means that this part goes
//    4   2 from the right(2) corner to to the bottom(3);
//      3
    public Snake(int StartX,int StartY,int vector,int length,Color color) {
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
        //Making combinated vector if Snake roted;
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
            --length;
            --AddWidth;
        }
    }


}
