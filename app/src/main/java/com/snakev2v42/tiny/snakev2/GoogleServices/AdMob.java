package com.snakev2v42.tiny.snakev2.GoogleServices;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.snakev2v42.tiny.snakev2.GameActivity;

/**
 * Created by yuriy on 5/23/2015.
 */
public abstract class AdMob {
    private static int adCounter=0;
    private static int emptyRequest;
    private static InterstitialAd interstitial;
    private static AdRequest adRequest;
    public static boolean enable=true;

    public static void init(Context c) {
        interstitial = new InterstitialAd(c);
        interstitial.setAdUnitId("ca-app-pub-5127368111078220/2366392990");
        adRequest = new AdRequest.Builder().build();
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                //adRequest = new AdRequest.Builder().build();
                //interstitial.loadAd(adRequest);
            }
        });
        if(!interstitial.isLoaded())
            interstitial.loadAd(adRequest);
    }



    public static void displayInterstitial(boolean noDelay) {
        if(enable) {
            if (noDelay) {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                    emptyRequest = 0;
                } else
                    ++emptyRequest;

                adCounter = 0;
            } else {
                if (adCounter >= 2 + GameActivity.random.nextInt(1)) {
                    if (interstitial.isLoaded()) {
                        interstitial.show();
                        emptyRequest = 0;
                    } else
                        ++emptyRequest;

                    adCounter = 0;
                } else
                    ++adCounter;
            }

            if (emptyRequest >= 3) {
                emptyRequest = 0;
                adCounter = 10;
                interstitial.loadAd(adRequest);
            }
        }
    }
}
