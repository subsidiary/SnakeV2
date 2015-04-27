package com.snakev2v42.tiny.snakev2;

/**
 * Created by Yuriy475 on 4/9/2015.
 */
abstract class Brains {

 /*   public static void NewVector(Snake currentSnake){
        int min=Values.CellHeight+Values.CellWidth,minId=0;
        int[] MealPriority = {0,0,0,0,0};
        int[] AlivePriority = {0,0,0,0,0};
        Part head =currentSnake.parts.get(0);
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
        for (int ui = 1; ui <= 4; ui++) {
            sII.vector = ui;
            int x=sII.GetXwithVectorNext(1,ui),y=sII.GetYwithVectorNext(1,ui);
            if (CheckForAnotherPrts())
                UnwtPr[ui] = false;
        }

    }


    public boolean CheckForAnotherPrts(Point p){
        return true;
    }*/
}
