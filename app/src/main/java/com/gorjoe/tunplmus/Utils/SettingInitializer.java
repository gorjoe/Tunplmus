package com.gorjoe.tunplmus.Utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bluewhaleyt.filemanagement.FileUtil;

public class SettingInitializer {

    static SharedPreferences sp;

    static boolean appLightModeEnable;
    public static String directory;

    public static void init(AppCompatActivity activity) {
        sp = PreferenceManager.getDefaultSharedPreferences(activity);

        appLightModeEnable = sp.getBoolean("prefAppLightModeEnable", false);
        directory = sp.getString("directory", FileUtil.getExternalStoragePath());

        applySetting(activity);

    }

    public static void applySetting(AppCompatActivity activity) {

        View view = activity.getWindow().getDecorView();

        if (appLightModeEnable) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

}