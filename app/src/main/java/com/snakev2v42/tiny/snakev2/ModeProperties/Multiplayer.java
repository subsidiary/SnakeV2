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
public class Multiplayer extends CurrentMode {
    public static boolean savedGame=false;
    static int score1=0,score2=0;
    static int color1,color2;

    public static void start(){
        ArrayList<Snake> snakes = GameActivity.snakes;
        color1=Values.getTheme().getSnakeColors()[0];
        color2=Values.getTheme().getSnakeColors()[1];
        snakes.add(new Snake(35, 10, Vector.WEST, 2, color1, Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        snakes.add(new Snake(5 , 10, Vector.EAST, 2, color2, Color.BLACK, Color.BLACK, Values.SnakeSize, Values.SnakeSize));
        Values.AMOUNT_OF_SNAKES = 2;

        GameActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                GameActivity.v1_1.setColorFilter(Values.getTheme().getSnakeColors()[0]);
                GameActivity.v2_1.setColorFilter(Values.getTheme().getSnakeColors()[0]);
                GameActivity.v3_1.setColorFilter(Values.getTheme().getSnakeColors()[0]);
                GameActivity.v4_1.setColorFilter(Values.getTheme().getSnakeColors()[0]);

                GameActivity.v1_2.setColorFilter(Values.getTheme().getSnakeColors()[1]);
                GameActivity.v2_2.setColorFilter(Values.getTheme().getSnakeColors()[1]);
                GameActivity.v3_2.setColorFilter(Values.getTheme().getSnakeColors()[1]);
                GameActivity.v4_2.setColorFilter(Values.getTheme().getSnakeColors()[1]);

                GameActivity.score1.setText(score1+"");
                GameActivity.score2.setText(score2+"");
            }
        });
        Logic.currentVector1=Vector.WEST;
        Logic.currentVector2=Vector.EAST;
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
                    if (Values.Volume)
                        Values.eat.start();
                    switch(i){
                        case 0:score1+=Values.lvl;break;
                        case 1:score2+=Values.lvl;break;
                    }
                    meal.generate();
                    GameActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GameActivity.score1.setText(score1+"");
                            GameActivity.score2.setText(score2+"");
                        }
                    });

                    snake.grow(1);
                }
            snake.move();
        }

        for (int i=0;i<Values.AMOUNT_OF_SNAKES;++i) {
            Snake snake=GameActivity.snakes.get(i);
            if (Logic.ifBroken(snake.head().p)) {
                snake.broken = true;
                switch (i) {
                    case 0:
                        score1 = -1;
                        break;
                    case 1:
                        score2 = -1;
                        break;
                }
                Log.i("SNAKE NUMBER ", (i + 1) + " have crashed");
            }

            if (i + 1 == Values.AMOUNT_OF_SNAKES) {
                if (GameActivity.snakes.get(0).broken) {
                    if (Values.Volume)
                        Values.crash.start();
                    Intent GoResult = new Intent(GameActivity.game, ResultActivity.class);
                    GameActivity.game.startActivity(GoResult);
                    Values.AMOUNT_OF_SNAKES = 0;
                } else
                if (GameActivity.snakes.get(1).broken) {
                    if (Values.Volume)
                        Values.crash.start();
                    Intent GoResult = new Intent(GameActivity.game, ResultActivity.class);
                    GameActivity.game.startActivity(GoResult);
                    Values.AMOUNT_OF_SNAKES = 0;
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
                if(score2<score1) {
                    ResultActivity.recordTxt.setText("First player win");
                    ResultActivity.resultTxt.setText(score1+"");
                    ResultActivity.resultTxt.setTextColor(color1);
                    ResultActivity.recordTxt.setTextColor(color1);


                    {//BackgroundColor
                        int BacgroundColor = Values.getTheme().getColor(), counter = 0;
                        while ((BacgroundColor == color1) && (++counter < 20))
                            BacgroundColor = Values.getTheme().getColor();
                        ResultActivity.re.setBackgroundColor(BacgroundColor);
                    }
                }else
                if(score1<score2) {
                    ResultActivity.recordTxt.setText("Second player win");
                    ResultActivity.resultTxt.setText(score2+"");
                    ResultActivity.resultTxt.setTextColor(color2);
                    ResultActivity.recordTxt.setTextColor(color2);

                    {//BackgroundColor
                        int BacgroundColor = Values.getTheme().getColor(), counter = 0;
                        while ((BacgroundColor == color2) && (++counter < 20))
                            BacgroundColor = Values.getTheme().getColor();
                        ResultActivity.re.setBackgroundColor(BacgroundColor);
                    }
                }else
                if(score1==score2){
                    ResultActivity.recordTxt.setText("Nobody win");
                    ResultActivity.resultTxt.setText("Draw");

                    //BackgroundColor
                    ResultActivity.re.setBackgroundColor(Values.getTheme().getColor());
                }
                int BacgroundColor=Values.getTheme().getColor(),counter=0;
                while( (BacgroundColor==color1 || BacgroundColor==color2)  &&  (++counter<20) )
                    BacgroundColor=Values.getTheme().getColor();
                ResultActivity.re.setBackgroundColor(BacgroundColor);
            }
        });
    }
    public static void load(){

    }

    public static void save(){

    }

}
