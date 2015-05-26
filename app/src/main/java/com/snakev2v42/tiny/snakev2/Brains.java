package com.snakev2v42.tiny.snakev2;

import android.util.Log;

import java.sql.Array;
import java.util.Arrays;

/**
 * Created by Yuriy475 on 4/9/2015.
 */
public abstract class Brains {
    private static boolean[][] was;

    public static int FreeCells(Point p){
        was[p.x][p.y] = true;
        if (Map.CheckMapCell(p) == Cell.SNAKE)
            return 0;
        int res = 0;
        for(int i = 1; i < 5; ++i) {
            Point pt = Point.plus(p, Vector.IntToVector(i));
            if (!was[pt.x][pt.y]){
                res += FreeCells(pt);
            }
        }
        return res + 1;/*
        int s = 0;
        int[][] was = new int[Values.CellWidth][Values.CellHeight];
        Point start = new Point(p.x,p.y);
        for(int i = 0;i<Values.CellWidth;++i)
            Arrays.fill(was[i],0);
        was[p.x][p.y]=-1;
        boolean Else;
        while (true) {
            Else = true;
            for (int i = 1; i < 5; ++i) {
                Point newP = Point.plus(p,Vector.IntToVector(i));
                if (Map.CheckMapCell(newP) != Cell.SNAKE && was[newP.x][newP.y]==0) {
                    p=newP;
                    was[p.x][p.y]=i;
                    ++s;
                    Else = false;
                    break;
                }
            }
            if (start.x == p.x && start.y == p.y && Else)
                break;
            if (Else) {
                p.plus(Vector.inverse(Vector.IntToVector(was[p.x][p.y])));
            }
        }
        return s;*/
    }

    public static Vector YuraBot(Snake me){
        Map.MakeNewMap();
        int min=Values.CellHeight+Values.CellWidth,minId=0;
        int[] MealPriority = {0,0,0,0,0};
        int[] AlivePriority = {1,1,1,1,1};
        Part head =me.head();
        for(int i=0;i<Values.AMOUNT_OF_MEAL;++i)
            if(Math.abs(head.p.x-GameActivity.ml.get(i).p.x)+Math.abs(head.p.y-GameActivity.ml.get(i).p.y)<=min){
                min=Math.abs(head.p.x-GameActivity.ml.get(i).p.x)+Math.abs(head.p.y-GameActivity.ml.get(i).p.y);
                minId=i;
            }        if(head.p.x>GameActivity.ml.get(minId).p.x)MealPriority[4]=2;
        if(head.p.x<GameActivity.ml.get(minId).p.x)MealPriority[2]=2;
        if(head.p.y>GameActivity.ml.get(minId).p.y)MealPriority[1]=2;
        if(head.p.y<GameActivity.ml.get(minId).p.y)MealPriority[3]=2;
        //AlivePriority..
        for (int j = 1; j <= 4; ++j)
            if (Map.CheckMapCell(Point.plus(head.p, Vector.IntToVector(j))) == Cell.SNAKE)
                AlivePriority[j]=0;else{
                was = new boolean[Values.CellWidth][Values.CellHeight];
                for(boolean[] arr: was)
                    Arrays.fill(arr, false);
                AlivePriority[j]=FreeCells(Point.plus(head.p, Vector.IntToVector(j)));}

        //Summary
        int max=0,maxID=1;
        for (int i = 1; i <= 4; i++) {
            if(AlivePriority[i]!=0&& MealPriority[i]+AlivePriority[i]>max){
                max=MealPriority[i]+AlivePriority[i];
                maxID=i;
            }
        }
        return Vector.IntToVector(maxID);
    }

    public static Vector PeteBot(Snake currentSnake){
        return Vector.NORTH;
    }

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


}
