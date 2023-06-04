package com.gorjoe.tunplmus.fragments.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import com.bluewhaleyt.component.preferences.CustomPreferenceFragment;
import com.gorjoe.tunplmus.R;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import static android.content.Context.MODE_PRIVATE;

public class AppFragment extends CustomPreferenceFragment {

    private static final int REQUEST_CODE_STORAGE_ACCESS = 9996;
    private File sdCardUri = Environment.getExternalStorageDirectory();

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sh = getActivity().getSharedPreferences("directory", Context.MODE_PRIVATE);
        String path = sh.getString("directory", "unknown");

        Preference button = findPreference("directory");
        button.setSummary(path);
        button.setOnPreferenceClickListener(preference -> {
            launchStorageAccessFramework();
            return true;
        });
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        SharedPreferences sh = getActivity().getSharedPreferences("directory", Context.MODE_PRIVATE);
        String path = sh.getString("directory", "unknown");

        Preference button = findPreference("directory");
        button.setSummary(path);
    }

    private void launchStorageAccessFramework() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, sdCardUri);
        startActivityForResult(intent, REQUEST_CODE_STORAGE_ACCESS);
    }

    private String getSDcardFromTreeUri(Uri treeUri) throws UnsupportedEncodingException, MalformedURLException {
        Log.e("input", "Uri: " + treeUri);
        File[] f = ContextCompat.getExternalFilesDirs(getContext().getApplicationContext(), null);
        for (int i = 0; i < f.length; i++) {
            String path = f[i].getParent().replace("/Android/data/", "").replace(getContext().getPackageName(), "");

            Log.e("paths", treeUri.toString() + " && " + path);

            String loc = path.substring(9);

            if (treeUri.toString().contains("primary") && path.contains("emulated")) {
                String docId = DocumentsContract.getTreeDocumentId(treeUri);
                String[] parts = docId.split(":");

                Uri unrealpath = Uri.parse(path).buildUpon().appendPath(parts[1]).build();
                URL realpath = new URL(URLDecoder.decode(unrealpath.toString(), "UTF-8"));

                return realpath.toString();

            } else if (treeUri.toString().contains(loc)) {
                Log.d("DIRS", path); //sdcard and internal and usb

                String docId = DocumentsContract.getTreeDocumentId(treeUri);
                String[] parts = docId.split(":");

                Uri unrealpath = Uri.parse(path).buildUpon().appendPath(parts[1]).build();
                URL realpath = new URL(URLDecoder.decode(unrealpath.toString(), "UTF-8"));

                return realpath.toString();
            }
        }
        return treeUri.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE_STORAGE_ACCESS:
                // Storing data into SharedPreferences
                try {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("directory", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    Uri uri = data.getData();
                    Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
                    String path = null;

                    path = getSDcardFromTreeUri(docUri);

                    Log.e("path", "path is: " + path);
                    myEdit.putString("directory", path);
                    myEdit.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
