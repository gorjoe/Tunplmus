package com.gorjoe.tunplmus.Utils;

import androidx.appcompat.app.AppCompatActivity;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.moderndialog.ModernDialog;

public class DialogUtils {
    public static ModernDialog dialog;

    public static void showRequirePermissionDialog(AppCompatActivity activity) {
        dialog = new ModernDialog.Builder(activity)
                .setTitle("Permission Needed")
                .setMessage("This app require File access permission" +
                        "\nIn order to access your music file" +
                        "\n Please allow it in the next pop up")
                .setCancelable(false, false)
                .setPositiveButton("OK", v -> PermissionUtil.requestAllFileAccess(activity))
                .setNegativeButton(false)
                .show();
    }
}
