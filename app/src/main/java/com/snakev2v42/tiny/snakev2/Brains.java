package com.snakev2v42.tiny.snakev2;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Yuriy475 on 4/9/2015.
 */
abstract class Brains {



    public static Vector StupidBot(Snake currentSnake){
        Map.MakeNewMap();
        int min=Values.CellHeight+Values.CellWidth,minId=0;
        int[] MealPriority = {0,0,0,0,0};
        int[] AlivePriority = {1,1,1,1,1};
        Part head =currentSnake.head();
        //MealPriority..
        for(int i=0;i<Values.AMOUNT_OF_MEAL;++i)
            if(Math.abs(head.p.x-GameActivity.ml.get(i).p.x)+Math.abs(head.p.y-GameActivity.ml.get(i).p.y)<=min){
                min=Math.abs(head.p.x-GameActivity.ml.get(i).p.x)+Math.abs(head.p.y-GameActivity.ml.get(i).p.y);
                minId=i;
            }
        if(head.p.x>GameActivity.ml.get(minId).p.x)MealPriority[4]=2;
        if(head.p.x<GameActivity.ml.get(minId).p.x)MealPriority[2]=2;
        if(head.p.y>GameActivity.ml.get(minId).p.y)MealPriority[1]=2;
        if(head.p.y<GameActivity.ml.get(minId).p.y)MealPriority[3]=2;
        //AlivePriority..
        for (int i = 1; i <= 4; i++) {
            if (Map.CheckMapCell(Point.plus(head.p,Vector.IntToVector(i)))==Cell.SNAKE)
                AlivePriority[i]=0;
        }
        //Summary
        int max=0,maxID=1;
        for (int i = 1; i <= 4; i++) {
            if(AlivePriority[i]!=0&&AlivePriority[i]+MealPriority[i]>max){
                max=AlivePriority[i]+MealPriority[i];
                maxID=i;
            }
        }
        return Vector.IntToVector(maxID);
    }
    public static Vector PeteBot(Snake currentSnake){
        return Vector.NORTH;
    }




}
