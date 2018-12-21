package com.dingmouren.examplesforandroid;

import android.app.Application;
import android.content.Context;

import com.dingmouren.examplesforandroid.util.ApplicationUtils;

/**
 * @author dingmouren
 */
public class MyApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        ApplicationUtils.init(this);
    }

    public static Context getContext(){
        return sContext;
    }
}
