package com.snakev2v42.tiny.snakev2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

public class Bitmaps {
    static Context context;
    static Bitmap[] RedParts= new Bitmap[15];Bitmap[] GreenParts= new Bitmap[15];Bitmap[] YellowParts= new Bitmap[15];Bitmap[] BlueParts= new Bitmap[15];
    static Bitmap[] MealParts = new Bitmap[3];
    static Bitmap MainB,emptyBit;
    static Canvas canvas;

    public Bitmaps(Context c) {
        context=c;
        MainB=Bitmap.createBitmap(Values.Width, Values.Height, Bitmap.Config.RGB_565);
        canvas= new Canvas(MainB);
    }

    public static void DrawToMainB(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x,y, null);
    }

    public void loadBitmaps() {
        RedParts[1]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[2]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[3]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[4]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[5]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[6]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[7]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[8]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[9]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x1_3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[10]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x2_4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[11]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x12_43))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[12]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x14_23))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[13]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x34_21))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        RedParts[14]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x41_32))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);



        GreenParts[1]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[2]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[3]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[4]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[5]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[6]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[7]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[8]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[9]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x1_3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[10]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x2_4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[11]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x12_43))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[12]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x14_23))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[13]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x34_21))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        GreenParts[14]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x41_32))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);


        YellowParts[1]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[2]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[3]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[4]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[5]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[6]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[7]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[8]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[9]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x1_3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[10]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x2_4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[11]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x12_43))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[12]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x14_23))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[13]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x34_21))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        YellowParts[14]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x41_32))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);



        BlueParts[1]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[2]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[3]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[4]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.head4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[5]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[6]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail2))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[7]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[8]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.tail4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[9]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x1_3))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[10]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x2_4))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[11]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x12_43))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[12]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x14_23))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[13]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x34_21))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);
        BlueParts[14]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.x41_32))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);

        MealParts[1]=Bitmap.createScaledBitmap(((BitmapDrawable)(context.getResources().getDrawable(R.drawable.meal1))).getBitmap(), Values.SnakeSize, Values.SnakeSize, true);


        emptyBit=Bitmap.createBitmap(Values.SnakeSize, Values.SnakeSize, Bitmap.Config.RGB_565);
        for(int ux=0;ux< Values.SnakeSize;ux++)
            for(int uy=0;uy< Values.SnakeSize;uy++)
                emptyBit.setPixel(ux,uy, Color.BLACK);
        colorBits();

    }

    public void colorBits(){
        for(int i=1;i<=14;i++){
            for(int ux=0;ux< Values.SnakeSize;ux++)
                for(int uy=0;uy< Values.SnakeSize;uy++)
                    if(GreenParts[i].getPixel(ux,uy)==Color.parseColor("#F44336"))GreenParts[i].setPixel(ux,uy,Color.parseColor("#4CAF50"));
        }
        for(int i=1;i<=14;i++){
            for(int ux=0;ux< Values.SnakeSize;ux++)
                for(int uy=0;uy< Values.SnakeSize;uy++)
                    if(BlueParts[i].getPixel(ux,uy)==Color.parseColor("#F44336"))BlueParts[i].setPixel(ux,uy,Color.parseColor("#3F51B5"));
        }
        for(int i=1;i<=14;i++){
            for(int ux=0;ux< Values.SnakeSize;ux++)
                for(int uy=0;uy< Values.SnakeSize;uy++)
                    if(YellowParts[i].getPixel(ux,uy)==Color.parseColor("#F44336"))YellowParts[i].setPixel(ux,uy,Color.parseColor("#FFEB3B"));
        }
    }
}