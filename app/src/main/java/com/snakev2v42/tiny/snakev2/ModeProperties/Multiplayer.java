package com.snakev2v42.tiny.snakev2.ModeProperties;

import android.content.Intent;
import android.graphics.Color;

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
public abstract class Multiplayer {
    public static boolean savedGame=false;
    public static int score1=0,score2=0;

    public static void start(){
        ArrayList<Snake> snakes = GameActivity.snakes;
        int snakeSpeed = GameActivity.snakeSpeed;
        snakes.add(new Snake(35, 10, Vector.WEST, 2, Values.getTheme().getSnakeColors()[0], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        snakes.add(new Snake(5 , 10, Vector.EAST, 2, Values.getTheme().getSnakeColors()[1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
        Values.AMOUNT_OF_SNAKES = 2;

        score1=0;score2=0;
    }
    public static void think(){
        for (int i=0;i<Values.AMOUNT_OF_SNAKES;++i) {
            Snake snake=GameActivity.snakes.get(i);

            switch (i){
                case 0:
                    if(Logic.currentVector1 !=Vector.inverse(snake.head().vec))
                        snake.head().vec= Logic.currentVector1;
                    break;
                case 1:
                    if(Logic.currentVector2 !=Vector.inverse(snake.head().vec))
                        snake.head().vec= Logic.currentVector2;
                    break;
            }

            for(Meal meal : GameActivity.ml)
                if(meal.eat(snake.head().p)){
                    switch(i){
                        case 0:++Multiplayer.score1;break;
                        case 1:++Multiplayer.score2;break;
                    }
                    meal.generate();
                    GameActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GameActivity.score1.setText(Multiplayer.score1+"");
                            GameActivity.score2.setText(Multiplayer.score2+"");
                        }
                    });

                    snake.grow(1);
                }
            snake.move();
            if(Logic.ifBroken(snake.head().p)){
                switch(i){
                    case 0:Multiplayer.score1=-1;break;
                    case 1:Multiplayer.score2=-1;break;
                }
                Intent GoResult = new Intent(GameActivity.game,ResultActivity.class);
                GameActivity.game.startActivity(GoResult);
            }
        }
    }
    public static void end(){
        savedGame=false;
        ResultActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                if(score2<score1) {
                    ResultActivity.recordTxt.setText("First player win");
                    ResultActivity.resultTxt.setText(score1+"");
                    ResultActivity.resultTxt.setTextColor(Values.getTheme().buttonColor);
                }else
                if(score1<score2) {
                    ResultActivity.recordTxt.setText("Second player win");
                    ResultActivity.resultTxt.setText(score2+"");
                    ResultActivity.resultTxt.setTextColor(Values.getTheme().buttonColor);
                }else
                if(score1==score2){
                    ResultActivity.recordTxt.setText("Nobody win");
                    ResultActivity.resultTxt.setText("Draw");
                }



            }
        });
    }
    public static void load(){

    }

    public static void save(){

    }

}
