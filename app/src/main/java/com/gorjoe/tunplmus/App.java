package com.gorjoe.tunplmus;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        CommonUtil.setDynamicColorsIfAvailable(this);
    }

    public static Context getContext() {
        return context;
    }
}
