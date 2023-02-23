package com.gorjoe.tunplmus.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;

import com.bluewhaleyt.filemanagement.FileUtil;
import com.gorjoe.tunplmus.FileUtil2;
import com.gorjoe.tunplmus.R;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragment {

    private static final int REQUEST_CODE_STORAGE_ACCESS = 9996;
    private String selectedSDCardPath;
    private File sdCardUri = Environment.getExternalStorageDirectory();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference button = findPreference("directory");
        button.setOnPreferenceClickListener(preference -> {
            launchStorageAccessFramework();
//            //code for what you want it to do
////            Toast.makeText(getActivity(), "you clicked me", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//            intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
////            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
////            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse("content://com.android.externalstorage.music/tree/primary%3A"));
//            startActivityForResult(intent, 9999);
            return true;
        });
    }

    private void launchStorageAccessFramework() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, sdCardUri);
        String[] mimeTypes = {"audio/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, REQUEST_CODE_STORAGE_ACCESS);
    }

    private Uri getContentUriFromTreeUri(Uri treeUri) {
        String documentId = DocumentsContract.getTreeDocumentId(treeUri);
        String[] parts = documentId.split(":");
        String type = parts[0];
        String path = parts[1];

        Uri contentUri = null;
        if ("primary".equalsIgnoreCase(type)) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(path).build();
        } else {
            contentUri = MediaStore.Audio.Media.getContentUri(type).buildUpon()
                    .appendPath(path).build();
        }
        return contentUri;
    }

    private Uri getContentUriFromTreeUri2(Uri treeUri) {
        // Get the document ID from the tree URI
        String docId = DocumentsContract.getTreeDocumentId(treeUri);

        String[] parts = docId.split(":");
        String type = parts[0];
        String path = parts[1];

        Uri contentUri = null;
        if ("primary".equalsIgnoreCase(type)) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(path).build();

            // Build a content URI using the volume ID and document ID
//            contentUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL).buildUpon().appendPath(docId).build();

        } else {
//            contentUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL).buildUpon().appendPath(path).build();
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(path).build();
//            contentUri = MediaStore.Audio.Media.getContentUri(type).buildUpon().appendPath(path).build();
        }

        return contentUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_STORAGE_ACCESS && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    selectedSDCardPath = getContentUriFromTreeUri2(uri).toString();
//                    selectedSDCardPath = uri.toString();
                    // Save the selected SD card path to shared preferences or use it as needed
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("directory", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("directory", selectedSDCardPath);
                    myEdit.apply();

                    Log.e("Test", "Result URI " + selectedSDCardPath);
                }
            }
        }

//        switch(requestCode) {
//            case 9999:
////                Log.e("Test", "Result URI " + data.getData());
//
//                // Storing data into SharedPreferences
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("directory", MODE_PRIVATE);
//                SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                Uri uri = data.getData();
//                Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
//                String path = FileUtil2.getPathFromUri(getContext(), docUri);
////                String path = FileUtil.getFilePathByUri(docUri);
//                myEdit.putString("directory", path);
//                myEdit.commit();
//                //=======================
////                Uri treeUri = data.getData();
////                getContext().getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
////                SharedPreferences preferences = getActivity().getSharedPreferences("directory", MODE_PRIVATE);
////
////                // Get a DocumentFile object representing the selected file
////                // Get a DocumentFile object representing the selected file
////                DocumentFile documentFile = DocumentFile.fromSingleUri(getContext(), treeUri);
////
////                // Get the absolute file path of the selected file
////                String filePath = documentFile.getUri().toString();
////
////
////                preferences.edit().putString("directory", filePath).apply();
//                break;
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
