package com.snakev2v42.tiny.snakev2.menusAndSettings;

import com.snakev2v42.tiny.snakev2.ModeProperties.Battle;
import com.snakev2v42.tiny.snakev2.ModeProperties.Campaign;
import com.snakev2v42.tiny.snakev2.Values;

/**
 * Created by yuriy on 5/9/2015.
 */
public enum Mode {
    CLASSIC,BATTLE,CAMPAIGN,MULTIPLAYER;
    public static Mode incMode(Mode mode){
        switch (mode){
            case BATTLE:  return CLASSIC;
            case CAMPAIGN:return CLASSIC;
            case CLASSIC: return MULTIPLAYER;
            case MULTIPLAYER: return BATTLE;
            default:      return CLASSIC;
        }
    }

    public static int ModeToInt(Mode mode){
        switch (mode){
            case BATTLE:  return 3;
            case CAMPAIGN:return 4;
            case CLASSIC: return 1;
            case MULTIPLAYER: return 2;
            default:      return 1;
        }
    }
    public static Mode IntToMode(int integer){
        switch (integer){
            case 3:  return BATTLE;
            case 4:return CAMPAIGN;
            case 1: return CLASSIC;
            case 2: return MULTIPLAYER;
            default:      return CLASSIC;
        }
    }

    public void think() {
    }
}
