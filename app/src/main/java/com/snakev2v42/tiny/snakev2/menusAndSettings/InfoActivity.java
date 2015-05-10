package com.snakev2v42.tiny.snakev2.menusAndSettings;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.snakev2v42.tiny.snakev2.R;
import com.snakev2v42.tiny.snakev2.Values;

/**
 * Created by yuriy on 5/9/2015.
 */
public class InfoActivity extends Activity {
    RelativeLayout infLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        infLay=(RelativeLayout)findViewById(R.id.InfLay);
        infLay.setBackgroundColor(Values.getTheme().info);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
