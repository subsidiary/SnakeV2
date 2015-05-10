package com.snakev2v42.tiny.snakev2.menusAndSettings;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.snakev2v42.tiny.snakev2.R;

/**
 * Created by yuriy on 5/10/2015.
 */
public class PauseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.pause_activity);
    }
}
