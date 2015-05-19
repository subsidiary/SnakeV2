package com.snakev2v42.tiny.snakev2.ModeProperties;

import com.snakev2v42.tiny.snakev2.Values;
import com.snakev2v42.tiny.snakev2.menusAndSettings.ResultActivity;

/**
 * Created by yuriy on 5/16/2015.
 */
public abstract class Campaign {
    public static boolean savedGame=false;
    public static int[] records ={0,0,0,0,0};
    public static int score;

    public static void start(){

    }
    public static void think(){


    }
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
        for(int i=0;i<5;++i)
            records[i]= Values.loadInt(records[i], "campaignRec[" + i + "]");
    }
    public static void save(){
        for(int i=0;i<5;++i)
            Values.saveInt(records[i],"campaignRec["+i+"]");
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
