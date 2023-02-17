package com.gorjoe.tunplmus.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.gorjoe.tunplmus.FileUtil2;
import com.gorjoe.tunplmus.R;
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
            intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse("content://com.android.externalstorage.music/tree/primary%3A"));
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
                //=======================
//                Uri treeUri = data.getData();
//                getContext().getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                SharedPreferences preferences = getActivity().getSharedPreferences("directory", MODE_PRIVATE);
//
//                // Get a DocumentFile object representing the selected file
//                // Get a DocumentFile object representing the selected file
//                DocumentFile documentFile = DocumentFile.fromSingleUri(getContext(), treeUri);
//
//                // Get the absolute file path of the selected file
//                String filePath = documentFile.getUri().toString();
//
//
//                preferences.edit().putString("directory", filePath).apply();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
