package com.snakev2v42.tiny.snakev2;

/**
 * Created by Yuriy475 on 4/9/2015.
 */
public class Brains {

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //The class was imported from the SnakeV1 and was not optimized , so it has few errors
/*
    public void VectorPlease(Snake2 sII) {
        //Havka
        int min = Values.CellHeight + Values.CellWidth, minId = 1;
        int prioVecX = 0, prioVecY = 0;
        int[] MealPr = {1, 1, 1, 1, 1};
        boolean[] UnwtPr = {true, true, true, true, true};

        for (int ui = 1; ui <= Meals.AMOUNT_OF_MEAL; ui++)
            if (min > Math.abs(sII.sn[1] - Meals.mealCO[ui * 2 - 1]) + Math.abs(sII.sn[2] - Meals.mealCO[ui * 2])) {
                min = Math.abs(sII.sn[1] - Meals.mealCO[ui * 2 - 1]) + Math.abs(sII.sn[2] - Meals.mealCO[ui * 2]);
                minId = ui;
            }
        if (sII.sn[1] > Meals.mealCO[minId * 2 - 1]) prioVecX = 4;
        if (sII.sn[1] < Meals.mealCO[minId * 2 - 1]) prioVecX = 2;
        if (sII.sn[2] > Meals.mealCO[minId * 2]) prioVecY = 1;
        if (sII.sn[2] < Meals.mealCO[minId * 2]) prioVecY = 3;
        MealPr[prioVecY] = 2;
        MealPr[prioVecX] = 2;
        //UnWasted

        for (int ui = 1; ui <= 4; ui++) {
            sII.vector = ui;
            int x=sII.GetXwithVectorNext(1,ui),y=sII.GetYwithVectorNext(1,ui);
            if (CheckSnakeCoods(x,y))
                UnwtPr[ui] = false;
        }
        // Summary
        int mealVec = 1, max = 0;
        for (int ui = 1; ui <= 4; ui++)
            if (UnwtPr[ui] && MealPr[ui] > max) {
                max = MealPr[ui];
                mealVec = ui;
            }
        sII.vector = mealVec;
    }

    static public boolean CheckSnakeCoods(int x,int y){
        boolean Yes=false;
        for(int ui=1;ui<=1600;ui++) {
            if ((x == Snake1.sn[ui * 2 - 1] && y == Snake1.sn[ui * 2]) || (x == Snake2.sn[ui * 2 - 1] && y == Snake2.sn[ui * 2]) ||
                    (x == Snake3.sn[ui * 2 - 1] && y == Snake3.sn[ui * 2]) || (x == Snake4.sn[ui * 2 - 1] && y == Snake4.sn[ui * 2])) {
                Yes = true;
            }
        }
        return Yes;
    }

*/

}
