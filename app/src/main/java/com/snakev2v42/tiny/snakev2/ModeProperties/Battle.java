package com.snakev2v42.tiny.snakev2.ModeProperties;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.snakev2v42.tiny.snakev2.GoogleServices.AdMob;
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
        snakes.add(new Snake(35, 0 , Vector.WEST, 2, Values.getTheme().getSnakeColors()[0], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(35, 5 , Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(35, 10, Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(35, 15, Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(35, 20, Vector.WEST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(5 , 0 , Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(5 , 5 , Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(5 , 10, Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(5 , 15, Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(5 , 20, Vector.EAST, 2, Values.getTheme().getSnakeColors()[GameActivity.random.nextInt(Values.getTheme().AMOUNT_OF_SNAKECOLORS-1)+1], Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        Values.AMOUNT_OF_SNAKES = 10;

        GameActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                GameActivity.score1.setText(score+"");
            }
        });
        Logic.currentVector1=Vector.WEST;
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
                    if (Values.Volume)
                        Values.eat.start();
                    if(i == 0)
                        score+=Values.lvl;
                    meal.generate();
                    GameActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GameActivity.score1.setText(score+"");
                        }
                    });

                    snake.grow(1);
                }
            snake.move();
        }

        for(int i=0;i<Values.AMOUNT_OF_SNAKES;++i){
            Snake snake=GameActivity.snakes.get(i);

            if(Logic.ifBroken(snake.head().p)){
                snake.broken=true;
            }

            if(i+1==Values.AMOUNT_OF_SNAKES) {
                if (GameActivity.snakes.get(0).broken) {
                    if (Values.Volume)
                        Values.crash.start();
                    Intent GoResult = new Intent(GameActivity.game, ResultActivity.class);
                    GameActivity.game.startActivity(GoResult);
                    Values.AMOUNT_OF_SNAKES=0;
                }else
                    for(int j=1;j<Values.AMOUNT_OF_SNAKES;++j)
                        if(GameActivity.snakes.get(j).broken){
                            if (Values.Volume)
                                Values.crash.start();

                            boolean[] CellMembers=Logic.returnCell(snake.head().p);
                            if(CellMembers!=null && CellMembers[0]) {
                                score += snake.length * Values.lvl *10;
                                Log.i("BATTLE ", " PLUSSED " + snake.length * Values.lvl + " length " + snake.length);
                            }
                            GameActivity.snakes.remove(j);
                            --j;
                            --Values.AMOUNT_OF_SNAKES;
                        }
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
