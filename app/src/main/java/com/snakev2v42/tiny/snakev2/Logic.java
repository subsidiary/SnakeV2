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
        ArrayList<Snake> snakes = GameActivity.snakes;
        ArrayList<Meal> ml = GameActivity.ml;

        //if(Values.savedGame){}else
        {
            int sizeS=snakes.size()-1,sizeM=ml.size()-1;
            for(int i=sizeS;i>=0;--i)
                snakes.remove(i);
            for(int i=sizeM;i>=0;--i)
                ml.remove(i);

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
        Log.i("dsfef"," "+Values.AMOUNT_OF_MEAL);
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
}
