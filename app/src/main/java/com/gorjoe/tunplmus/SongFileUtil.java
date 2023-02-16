package com.gorjoe.tunplmus;

import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.util.ArrayList;

public class SongFileUtil {
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static Uri StringtoUri(String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        return uri;
    }
}
