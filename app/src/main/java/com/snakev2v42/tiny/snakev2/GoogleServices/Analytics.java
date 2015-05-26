package com.snakev2v42.tiny.snakev2.GoogleServices;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;

/**
 * Created by yuriy on 5/23/2015.
 */
public abstract class Analytics {
    static GoogleAnalytics tracker;

    public static void init(Context c){
        tracker = GoogleAnalytics.getInstance(c);
    }
}
