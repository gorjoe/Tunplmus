package com.gorjoe.tunplmus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;

import com.bluewhaleyt.filemanagement.FileUtil;
import com.gorjoe.tunplmus.FileUtil2;
import com.gorjoe.tunplmus.R;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference button = findPreference("directory");
        button.setOnPreferenceClickListener(preference -> {
            //code for what you want it to do
//            Toast.makeText(getActivity(), "you clicked me", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivityForResult(intent, 9999);
            return true;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 9999:
//                Log.e("Test", "Result URI " + data.getData());

                // Storing data into SharedPreferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("directory", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                Uri uri = data.getData();
                Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
                String path = FileUtil2.getPathFromUri(getContext(), docUri);
//                String path = FileUtil.getFilePathByUri(docUri);
                myEdit.putString("directory", path);
                myEdit.commit();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
