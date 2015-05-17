package com.snakev2v42.tiny.snakev2.ModeProperties;

import android.content.Intent;
import android.graphics.Color;

import com.snakev2v42.tiny.snakev2.Brains;
import com.snakev2v42.tiny.snakev2.GameActivity;
import com.snakev2v42.tiny.snakev2.Logic;
import com.snakev2v42.tiny.snakev2.Meal;
import com.snakev2v42.tiny.snakev2.Snake;
import com.snakev2v42.tiny.snakev2.Values;
import com.snakev2v42.tiny.snakev2.Vector;
import com.snakev2v42.tiny.snakev2.menusAndSettings.ResultActivity;

import java.util.ArrayList;

/**
 * Created by yuriy on 5/16/2015.
 */
public abstract class Battle {
    public static boolean savedGame=false;
    public static int[] records ={0,0,0,0,0};
    public static int score;

    public static void start(){
        ArrayList<Snake> snakes = GameActivity.snakes;
        int snakeSpeed = GameActivity.snakeSpeed;
        snakes.add(new Snake(35, 1 , Vector.WEST, 2, Values.getTheme().getSnakeColors()[0], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(35, 6 , Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(35, 11, Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(35, 16, Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(35, 21, Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(5 , 1 , Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(5 , 6 , Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(5 , 11, Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(5 , 16, Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(5 , 21, Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        Values.AMOUNT_OF_SNAKES = 10;

        score=0;
    }
    public static void think(){
        for (int i=0;i<Values.AMOUNT_OF_SNAKES;++i) {
            Snake snake=GameActivity.snakes.get(i);

            if(i==0 && Logic.currentVector1 !=Vector.inverse(snake.head().vec))
                snake.head().vec= Logic.currentVector1;
            else
                snake.head().vec= Brains.YuraBot(snake);

            for(Meal meal : GameActivity.ml)
                if(meal.eat(snake.head().p)){
                    if(i == 0)
                        ++Battle.score;
                    meal.generate();
                    GameActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GameActivity.score1.setText(Battle.score+"");
                        }
                    });

                    snake.grow(1);
                }
            snake.move();
            if(Logic.ifBroken(snake.head().p)){
                Intent GoResult = new Intent(GameActivity.game,ResultActivity.class);
                GameActivity.game.startActivity(GoResult);
            }
        }    }
    public static void end(){
        savedGame=false;
        ResultActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                ResultActivity.recordTxt.setText(CheckRecords(score));
                ResultActivity.resultTxt.setText(""+score);
            }
        });
    }
    public static void load(){
        for(int i=0;i<5;++i){
            records[i] = Values.loadInt(records[i],"battleRec["+i+"]");
        }
    }
    public static void save(){
        for(int i=0;i<5;++i)
            Values.saveInt(records[i], "battleRec[" + i + "]");
    }



    public static String CheckRecords(int score){
        String newRecord="";
        for(int i=0;i<5;++i) {
            if(score>records[i]){
                if(records[i]<score){
                    records[i]=score;
                    if(i==0)newRecord="MASTER";
                    else
                        newRecord="RECORD";
                    break;
                }
            }
        }
        Values.saveSettings();
        return newRecord;
    }
}
