package com.gorjoe.tunplmus;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;

public class SongFileUtil {
//    public static String getMimeType(String url) {
//        String type = null;
//        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
//        if (extension != null) {
//            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//        }
//        return type;
//    }

    public boolean isMimeTypeAudio(Context context, String filePath) {
        ContentResolver cr = context.getContentResolver();
        String mimeType = cr.getType(Uri.fromFile(new File(filePath)));

        if (mimeType != null && mimeType.startsWith("audio/")) {
            // The file is an audio file
            return true;
        } else {
            // The file is not an audio file
            return false;
        }

    }

    public static Uri StringtoUri(String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        return uri;
    }
}
