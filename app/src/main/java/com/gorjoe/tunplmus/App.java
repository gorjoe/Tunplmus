package com.gorjoe.tunplmus;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bluewhaleyt.common.DynamicColorsUtil;
import com.yausername.aria2c.Aria2c;
import com.yausername.ffmpeg.FFmpeg;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DynamicColorsUtil.setDynamicColorsIfAvailable(this);
        Completable.fromAction(this::initLibraries).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                // it worked
            }

            @Override
            public void onError(Throwable e) {
                if(BuildConfig.DEBUG) Log.e("ytdl", "failed to initialize youtubedl-android", e);
                Toast.makeText(getApplicationContext(), "initialization failed: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Context getContext() {
        return context;
    }

    private void initLibraries() throws YoutubeDLException {
        YoutubeDL.getInstance().init(getContext());
        FFmpeg.getInstance().init(getContext());
        Aria2c.getInstance().init(getContext());
    }
}
