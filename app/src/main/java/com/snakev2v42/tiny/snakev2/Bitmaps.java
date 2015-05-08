package com.snakev2v42.tiny.snakev2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

public class Bitmaps {
    static Context context;
    static Bitmap[] SnakeParts = new Bitmap[15];
    static Bitmap[] MealParts = new Bitmap[3];
    static Bitmap MainB;
    static Canvas canvas;
    static Bitmap Pete,Yura;

    public Bitmaps(Context c) {
        context=c;
        MainB=Bitmap.createBitmap(Values.Width, Values.Height, Bitmap.Config.RGB_565);
        MainB.eraseColor(Color.parseColor("#212121"));
        canvas= new Canvas(MainB);

        Bitmap source;
        source = ((BitmapDrawable) (context.getResources().getDrawable(R.drawable.snake_parts))).getBitmap();
        for (int i = 0; i <= 14; i++) {
            SnakeParts[i] = Bitmap.createBitmap(source, source.getWidth() / 5 * (i % 5), source.getHeight() / 3 * (i / 5), source.getWidth() / 5, source.getHeight() / 3);
            SnakeParts[i] = Bitmap.createScaledBitmap(SnakeParts[i], Values.SnakeSize, Values.SnakeSize, false);
        }
        MealParts[0] = Bitmap.createScaledBitmap(((BitmapDrawable) (context.getResources().getDrawable(R.drawable.meal1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        Pete = ((BitmapDrawable) (context.getResources().getDrawable(R.drawable.inon_p))).getBitmap();
        Yura = ((BitmapDrawable) (context.getResources().getDrawable(R.drawable.inon_y))).getBitmap();
        Pete = Bitmap.createScaledBitmap(Pete, Values.SnakeSize*5, Values.SnakeSize*5, false);
        Yura = Bitmap.createScaledBitmap(Yura, Values.SnakeSize*5, Values.SnakeSize*5, false);
    }

    public static void DrawToMainB(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x,y, null);
    }
}