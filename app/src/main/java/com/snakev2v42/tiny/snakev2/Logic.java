package com.snakev2v42.tiny.snakev2;

import android.content.Intent;
import android.graphics.Color;

import com.snakev2v42.tiny.snakev2.ModeProperties.Battle;
import com.snakev2v42.tiny.snakev2.ModeProperties.Classic;
import com.snakev2v42.tiny.snakev2.ModeProperties.Multiplayer;
import com.snakev2v42.tiny.snakev2.menusAndSettings.ResultActivity;

import java.util.ArrayList;

/**
 * Created by yuriy on 5/16/2015.
 */
abstract class Logic {
    static Vector currentVector1 = Vector.WEST;
    static Vector currentVector2 = Vector.EAST;

    public static void think(){
        switch (Values.mode){
            case CLASSIC    : thinkCLASSIC();break;
            case BATTLE     : thinkBATTLE();break;
            case MULTIPLAYER: thinkMULTIPLAYER();break;
        }
    }

    private static void thinkMULTIPLAYER() {
        for (int i=0;i<Values.AMOUNT_OF_SNAKES;++i) {
            Snake snake=GameActivity.snakes.get(i);

            switch (i){
                case 0:
                    if(currentVector1 !=Vector.inverse(snake.head().vec))
                    snake.head().vec= currentVector1;
                    break;
                case 1:
                    if(currentVector2 !=Vector.inverse(snake.head().vec))
                        snake.head().vec= currentVector2;
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
            /*if(ifBroken(snake.head().p)){
                Intent GoResult = new Intent(GameActivity.game,ResultActivity.class);
                GameActivity.game.finish();
                GoResult.putExtra("Score",GameActivity.random.nextInt(2000));
                GameActivity.game.startActivity(GoResult);
                GameActivity.game.overridePendingTransition(R.anim.start_activity,R.anim.wait_anim);
            }*/
        }
    }

    private static void thinkCLASSIC() {
        for (int i=0;i<Values.AMOUNT_OF_SNAKES;++i) {
            Snake snake=GameActivity.snakes.get(i);

            if(currentVector1 !=Vector.inverse(snake.head().vec))
                snake.head().vec= currentVector1;

            for(Meal meal : GameActivity.ml)
                if(meal.eat(snake.head().p)){
                    ++Classic.score;
                    meal.generate();
                    GameActivity.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GameActivity.score1.setText(Classic.score+"");
                        }
                    });

                    snake.grow(1);
                }
            snake.move();
            /*if(ifBroken(snake.head().p)){
                Intent GoResult = new Intent(GameActivity.game,ResultActivity.class);
                GoResult.putExtra("Score",Classic.score);
                GameActivity.game.startActivity(GoResult);
            }*/
        }
    }

    private static void thinkBATTLE() {
        for (int i=0;i<Values.AMOUNT_OF_SNAKES;++i) {
            Snake snake=GameActivity.snakes.get(i);

            if(i==0 && currentVector1 !=Vector.inverse(snake.head().vec))
                snake.head().vec= currentVector1;
            else
                snake.head().vec=Brains.YuraBot(snake);

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
            /*if(ifBroken(snake.head().p)&& i == 0){
                Intent GoResult = new Intent(GameActivity.game,ResultActivity.class);
                GoResult.putExtra("Score",Battle.score);
                GameActivity.game.startActivity(GoResult);
            }*/
        }
    }

    public static void start() {
        ArrayList<Snake> snakes = GameActivity.snakes;
        ArrayList<Meal> ml = GameActivity.ml;
        int snakeSpeed = GameActivity.snakeSpeed;

        if(!Values.savedGame){
            switch (Values.mode){
                case CLASSIC:
                    snakes.add(new Snake(19, 10, Vector.WEST, 2, Values.getTheme().getSnakeColors()[0], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
                    Values.AMOUNT_OF_SNAKES = 1;
                    break;
                case BATTLE:
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
                    break;
                case MULTIPLAYER:
                    snakes.add(new Snake(35, 10, Vector.WEST, 2, Values.getTheme().getSnakeColors()[0], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
                    snakes.add(new Snake(5 , 10, Vector.EAST, 2, Values.getTheme().getSnakeColors()[1], Color.BLACK, Color.BLACK, Values.SnakeSize, snakeSpeed));
                    Values.AMOUNT_OF_SNAKES = 2;
                    break;
            }
        }

        for (int m = 0; m < Values.AMOUNT_OF_SNAKES; ++m) {
            ml.add(m, new Meal(1, Color.parseColor("#3F51B5")));
            ++Values.AMOUNT_OF_MEAL;
        }
    }


    private static boolean ifBroken(Point p) {
        int count = 0;
        for (int s = 0; s < Values.AMOUNT_OF_SNAKES; ++s) {
            for (int prt = 0; prt < GameActivity.snakes.get(s).length; ++prt) {
                if (GameActivity.snakes.get(s).parts.get(prt).p.x == p.x && GameActivity.snakes.get(s).parts.get(prt).p.y == p.y) {
                    ++count;
                }
            }
        }
        if (count > 1) return true;
        else return false;
    }
}
