package com.snakev2v42.tiny.snakev2;

/**
 * Created by Yuriy475 on 4/9/2015.
 */
abstract class Brains {

    public static Vector NewVector(Snake currentSnake){
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
            if (CheckForAnotherPrts(Point.plus(head.p,Vector.IntToVector(i))))
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


    public static boolean CheckForAnotherPrts(Point p){
        boolean Yes=false;
        for(int s=0;s<Values.AMOUNT_OF_SNAKES;++s){
            for(int prt=0;prt<GameActivity.sn.get(s).length-1;++prt){
                if(GameActivity.sn.get(s).parts.get(prt).p.x==p.x && GameActivity.sn.get(s).parts.get(prt).p.y==p.y){
                    Yes=true;
                }
            }
        }
        return Yes;
    }
}
