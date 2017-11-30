package com.moblieapp.pannawatnokket.findmycat;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by pannawatnokket on 17/11/2017 AD.
 */

public class ApplicationFonts extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/SukhumvitSet.ttc")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }
}
