package com.snakev2v42.tiny.snakev2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.snakev2v42.tiny.snakev2.ModeProperties.Battle;
import com.snakev2v42.tiny.snakev2.ModeProperties.Classic;
import com.snakev2v42.tiny.snakev2.ModeProperties.Multiplayer;
import com.snakev2v42.tiny.snakev2.menusAndSettings.ResultActivity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yuriy on 5/16/2015.
 */
public abstract class Logic {
    public static Vector currentVector1 = Vector.WEST;
    public static Vector currentVector2 = Vector.EAST;

    public static void think(){
        switch (Values.mode){
            case CLASSIC    : Classic.think();break;
            case BATTLE     : Battle.think();break;
            case MULTIPLAYER: Multiplayer.think();break;
        }
    }

    public static void start() {

        if(GameActivity.snakes==null)
            GameActivity.snakes = new ArrayList<>();
        if(GameActivity.ml==null)
            GameActivity.ml = new ArrayList<>();
        ArrayList<Snake> snakes = GameActivity.snakes;
        ArrayList<Meal> ml = GameActivity.ml;
        //if(Values.savedGame){}else
        {
            System.gc();
            Iterator<Snake> iter = snakes.listIterator();
            while (iter.hasNext()) {
                iter.next().recycle();
                iter.remove();
            }
            ml.clear();

            if(GameActivity.snakes==null)
                GameActivity.snakes = new ArrayList<>();
            if(GameActivity.ml==null)
                GameActivity.ml = new ArrayList<>();

            switch (Values.mode){
                case CLASSIC:
                    Classic.start();
                    break;
                case BATTLE:
                    Battle.start();
                    break;
                case MULTIPLAYER:
                    Multiplayer.start();
                    break;
            }
        }

        Values.AMOUNT_OF_MEAL=-1;
        while(Values.AMOUNT_OF_MEAL!=Values.AMOUNT_OF_SNAKES-1)
            ml.add(++Values.AMOUNT_OF_MEAL, new Meal(1, Color.parseColor("#3F51B5")));
        ++Values.AMOUNT_OF_MEAL;
    }


    public static boolean ifBroken(Point p) {
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
    public static boolean[] returnCell(Point p){
        boolean Cell[]=null;
        for (int s = 0; s < Values.AMOUNT_OF_SNAKES; ++s) {
            for (int prt = 0; prt < GameActivity.snakes.get(s).length; ++prt) {
                if (GameActivity.snakes.get(s).parts.get(prt).p.x == p.x && GameActivity.snakes.get(s).parts.get(prt).p.y == p.y) {
                    if(Cell==null)
                        Cell = new boolean[Values.AMOUNT_OF_SNAKES];
                    Cell[s]=true;
                }
            }
        }
        return Cell;
    }
}
