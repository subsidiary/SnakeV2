package com.snakev2v42.tiny.snakev2.ModeProperties;

import com.snakev2v42.tiny.snakev2.Values;

/**
 * Created by yuriy on 5/16/2015.
 */
public abstract class Campaign {
    public static boolean savedGame=false;
    public static int[] records ={0,0,0,0,0};
    public static int score;

    public static void load(){
        for(int i=0;i<5;++i)
            records[i]= Values.loadInt(records[i], "campaignRec[" + i + "]");
    }
    public static void save(){
        for(int i=0;i<5;++i)
            Values.saveInt(records[i],"campaignRec["+i+"]");
    }
}
