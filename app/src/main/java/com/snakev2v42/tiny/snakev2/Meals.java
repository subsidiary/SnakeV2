package com.snakev2v42.tiny.snakev2;

import java.util.Random;

/**
 * Created by Yuriy475 on 4/3/2015.
 */
public class Meals{

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //The class was imported from the SnakeV1 and was not optimized , so it has few errors
    /*
    int AMOUNT_OF_MEAL=4;
    static int[] mealCO = new int[22];
    static GameActivity game = new GameActivity();
    static Settings set = new Settings();

    public void generateMeal(){
        Random r = new Random();
        for(int ui=1;ui<=AMOUNT_OF_MEAL;ui++) {
            if (mealCO[ui * 2] == 0) {
                int x = r.nextInt(set.CellWidth);
                int y = r.nextInt(set.CellHeight);
                while(Brains.CheckSnakeCoods(x,y)){
                    x = r.nextInt(set.CellWidth);y = r.nextInt(set.CellHeight);
                }
                mealCO[ui * 2 - 1] = x;
                mealCO[ui * 2] = y;
            }
        }
    }

    public void PaintMeals(){
        for(int ui=1;ui<=AMOUNT_OF_MEAL;ui++) {
            game.bit.DrawToMainB(game.bit.MealParts[1],set.SnakeSize *  mealCO[ui * 2 - 1], set.SnakeSize * mealCO[ui * 2]);
        }
    }*/
}