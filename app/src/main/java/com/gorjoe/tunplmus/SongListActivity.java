package com.gorjoe.tunplmus;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.moderndialog.ModernDialog;
import com.gorjoe.tunplmus.adapter.SongListAdapter;
import com.gorjoe.tunplmus.databinding.ActivitySongListBinding;
import com.gorjoe.tunplmus.models.SongModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SongListActivity extends AppCompatActivity {

    private ActivitySongListBinding binding;
    ModernDialog dialog;

    private ArrayList<SongModel> list = new ArrayList<>();
    private SongListAdapter songlistadapter = new SongListAdapter(list);

    public static ArrayList<Song> songList = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivitySongListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    public static ArrayList<Song> SongListArray() {
        return songList;
    }

    public static Uri StringtoUri(String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        return uri;
    }

    public void getSongList() {
        //clear list before adding
        songList.clear();

        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION};
        Cursor musicCursor = musicResolver.query(musicUri, projection, null, null, null);

        if (musicCursor!= null) {
            if (musicCursor.moveToFirst()) {
                do {
                    long id = musicCursor.getLong(0);
                    String title = musicCursor.getString(1);
                    String artist = musicCursor.getString(2);
                    long duration = musicCursor.getLong(3);
                    songList.add(new Song(id, title, artist, duration));
                } while (musicCursor.moveToNext());
            }
            musicCursor.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            showDialog();

        } else {
            if (dialog != null && dialog.dialogDef.isShowing()) {
                dialog.dialogDef.dismiss();
            }
            list = new ArrayList<>();
            songlistadapter = new SongListAdapter(list);
            if (songlistadapter.getItemCount() == 0) {
                // Retrieving the value using its keys the file name
                // must be same in both saving and retrieving the data
                SharedPreferences sh = getSharedPreferences("directory", Context.MODE_PRIVATE);
//                String dir = sh.getString("directory", FileUtil.getExternalStoragePath());
                String dir = sh.getString("directory", "unknown");
                Toast.makeText(this, "dir: " + dir, Toast.LENGTH_LONG).show();
                Log.e("Test", "dir: " + dir);

                if (!dir.equals("unknown")) {
                    // loop file to screen
                    ArrayList<String> files = new ArrayList<>();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setStackFromEnd(true);
                    linearLayoutManager.setReverseLayout(true);

//                    FileUtil.listOnlyFilesSubDirFiles(dir, files);
//                    Log.e("Test", "files: " + files);

//                    getSongList();
                    Uri realdir = StringtoUri(dir);
                    Log.e("Test", "realdir: " + realdir);
                    Collections.sort(songList, new Comparator<Song>(){
                        public int compare(Song a, Song b){
                            return a.getTitle().compareTo(b.getTitle());
                        }
                    });

//                    for (int i = 0; i < files.size(); i++) {
////                        String f = files.get(i);
////                        File song = new File(f);
//
//                        list.add(new SongModel());
//                        list.get(i).setName(songName);
//                        list.get(i).setAuthor(artist);
////                        list.get(i).setName("songName");
////                        list.get(i).setAuthor("artist");
//                    }

                    for (int i = 0; i < songList.size(); i++) {
                        Song currSong = songList.get(i);
                        list.add(new SongModel());
                        list.get(i).setName(currSong.getTitle());
                        list.get(i).setAuthor(currSong.getArtist());
                    }
//
                    binding.lvSongList.setLayoutManager(linearLayoutManager);
                    binding.lvSongList.setAdapter(songlistadapter);
                    binding.lvSongList.getAdapter().notifyDataSetChanged();

//                    ((BaseAdapter) binding.lvSongList.getAdapter()).notifyDataSetChanged();

                } else {
                    Toast.makeText(this, "Song Directory Not Selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static void listDir(String path, ArrayList < String > list, boolean includeHiddenFiles) {
        File dir = new File(path);
        if (!dir.exists() || dir.isFile()) return;

        File[] listFiles = dir.listFiles();
        if (listFiles == null || listFiles.length <= 0) return;

        if (list == null) return;
        list.clear();
        for (File file: listFiles) {
            if (includeHiddenFiles) {
                list.add(file.getAbsolutePath());
            } else {
                if (!file.getName().startsWith(".")) {
                    list.add(file.getAbsolutePath());
                }
            }
        }
    }

    private void init() {
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);
        getSupportActionBar().setTitle("Tunplmus");
        binding.tvSongName.setSelected(true);
        binding.tvSongName.setSingleLine();
        binding.layoutCurrentSong.setOnClickListener(v -> {
//            startActivity(new Intent(this, MediaPlayerActivity.class));
            Intent intent = new Intent(this, MediaPlayerActivity.class);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, 0 );
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_download:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.menu_history:
                startActivity(new Intent(this, DownloadHistoryActivity.class));
                break;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void download() {
        // SnackbarUtil.makeSnackbar(this, "button clicked");
        startActivity(new Intent(this, MediaPlayerActivity.class));

    }

    private void showDialog() {
        dialog = new ModernDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This app require File access permission" +
                        "\nIn order to access your music file" +
                        "\n Please allow it in the next pop up")
                .setCancelable(false, false)
                .setPositiveButton("OK", v -> {
                    PermissionUtil.requestAllFileAccess(this);
                })
                .setNegativeButton(false)
                .show();
    }

}