package com.snakev2v42.tiny.snakev2;

import android.widget.Switch;

import java.sql.Array;
import java.util.Arrays;

/**
 * Created by yuriy on 4/29/2015.
 */
public class Map {
    static Cell[][] map = new Cell[Values.CellWidth][Values.CellHeight];;

    public static void MakeNewMap() {
        for(int i = 0;i<Values.CellWidth;++i)
            Arrays.fill(map[i],Cell.NOTHING);
        for (int s = 0; s < Values.AMOUNT_OF_SNAKES; ++s) {
            for (int prt = 0; prt < GameActivity.sn.get(s).length; ++prt) {
                map[GameActivity.sn.get(s).parts.get(prt).p.x][GameActivity.sn.get(s).parts.get(prt).p.y] = Cell.SNAKE;
            }
        }
        for (int m = 0; m < Values.AMOUNT_OF_MEAL; ++m)
            map[GameActivity.ml.get(m).p.x][GameActivity.ml.get(m).p.y] = Cell.MEAL;
    }

    public static Cell CheckMapCell(Point p) {
            return map[p.x][p.y];
    }

    public static boolean IfBroken(Point p) {
        int count = 0;
        for (int s = 0; s < Values.AMOUNT_OF_SNAKES; ++s) {
            for (int prt = 0; prt < GameActivity.sn.get(s).length; ++prt) {
                if (GameActivity.sn.get(s).parts.get(prt).p.x == p.x && GameActivity.sn.get(s).parts.get(prt).p.y == p.y) {
                    ++count;
                }
            }
        }
        if (count > 1) return true;
        else return false;
    }
}
