package com.moblieapp.pannawatnokket.findmycat;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by pannawatnokket on 17/11/2017 AD.
 */

public class ApplicationFonts extends Application {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/SukhumvitSet.ttc")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }

    public FirebaseAnalytics getmFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

}
