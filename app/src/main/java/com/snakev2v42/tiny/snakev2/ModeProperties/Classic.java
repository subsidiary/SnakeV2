package com.snakev2v42.tiny.snakev2.ModeProperties;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.snakev2v42.tiny.snakev2.GoogleServices.AdMob;
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
public abstract class Classic {
    public static boolean savedGame=false;
    public static int[] records ={0,0,0,0,0};
    public static int score;

    private static int BigMealTurn=0;
    private static boolean BigMeal=false;
    private static BigMealTimer BigMealTimer=null;

    private static class BigMealTimer extends Thread {
        long startTime;
        boolean flag =true;
        int MaxDelay,MaxPoints;
        @Override
        public void run() {
            while(flag) {
                while (BigMeal) {
                    if (System.currentTimeMillis() - startTime > MaxDelay)
                        stopTimer();
                }
            }
        }
        public void stopThread(){
            flag=false;
        }
        public void startTimer(){
            BigMeal = true;
            startTime = System.currentTimeMillis();
            GameActivity.ml.get(0).size = 2;
            GameActivity.ml.get(0).color = Color.parseColor("#F44336");
        }
        public int stopTimer(){
            BigMeal=false;
            GameActivity.ml.get(0).size=1;
            GameActivity.ml.get(0).color = Color.parseColor("#3F51B5");
            GameActivity.ml.get(0).generate();
            if(System.currentTimeMillis()-startTime<MaxDelay/10)
                return MaxDelay/10;
            else
                return (int)((MaxDelay-(System.currentTimeMillis()-startTime))*1f/MaxDelay*MaxPoints);
        }
    }

    public static void start(){
        ArrayList<Snake> snakes = GameActivity.snakes;
        snakes.add(new Snake(19, 10, Vector.WEST, 10, Values.getTheme().getSnakeColors()[0], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        Values.AMOUNT_OF_SNAKES = 1;

        GameActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                GameActivity.score1.setText(score+"");
            }
        });

        if(BigMealTimer==null) {
            BigMealTimer = new BigMealTimer();
            BigMealTimer.start();
        }
        Logic.currentVector1=Vector.WEST;
        score=0;BigMealTurn=0;BigMeal=false;
        BigMealTimer.MaxPoints=Values.lvl*100;
        BigMealTimer.MaxDelay =(int)(  (Values.CellHeight/2f+Values.CellWidth/2f  +15) * (Values.snakeSpeed*Values.SnakeSize)   );
    }

    public static void think() {
        if(Values.AMOUNT_OF_SNAKES!=0) {
            Snake snake = GameActivity.snakes.get(0);

            if (Logic.currentVector1 != Vector.inverse(snake.head().vec))
                snake.head().vec = Logic.currentVector1;

            for (Meal meal : GameActivity.ml)
                if (meal.eat(snake.head().p)) {
                    if (Values.Volume)
                        Values.eat.start();
                    if (BigMeal) {
                        score += BigMealTimer.stopTimer();
                    } else {
                        score += Values.lvl;
                        if (++BigMealTurn == 5) {
                            BigMealTurn = 0;
                            BigMealTimer.startTimer();
                        }
                    }
                    meal.generate();
                    GameActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GameActivity.score1.setText(Classic.score + "");
                        }
                    });

                    snake.grow(1);
                }

            snake.move();
            if (Logic.ifBroken(snake.head().p)) {
                if (Values.Volume)
                    Values.crash.start();
                Intent GoResult = new Intent(GameActivity.game, ResultActivity.class);
                GameActivity.game.startActivity(GoResult);
                Values.AMOUNT_OF_SNAKES = 0;
            }
        }
    }
    public static void end(){
        savedGame=false;
        AdMob.displayInterstitial(false);
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
            records[i]=Values.loadInt(records[i],"classicRec["+i+"]");
        }
    }
    public static void save(){
        for(int i=0;i<5;++i) {
            Values.saveInt(records[i], "classicRec[" + i + "]");
        }
    }
    public static String CheckRecords(int score){
        String newRecord="";
        for(int i=0;i<5;++i) {
            if(score>records[i]) {
                records[i] = score;
                if (i == 0) newRecord = "MASTER";
                else
                    newRecord = "RECORD";
                break;
            }
        }
        Values.saveSettings();
        return newRecord;
    }
}
