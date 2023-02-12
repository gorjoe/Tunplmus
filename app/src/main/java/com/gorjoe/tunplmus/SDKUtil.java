package com.gorjoe.tunplmus;

import android.os.Build;

import androidx.annotation.ChecksSdkIntAtLeast;

public class SDKUtil {

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    public static boolean isAtLeastSDK26() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

}
