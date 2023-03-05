package com.gorjoe.tunplmus;

import android.app.Activity;
import android.content.ContentUris;
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
import com.bluewhaleyt.moderndialog.ModernDialog;
import com.gorjoe.tunplmus.Utils.DialogUtils;
import com.gorjoe.tunplmus.adapter.SongListAdapter;
import com.gorjoe.tunplmus.databinding.ActivitySongListBinding;
import com.gorjoe.tunplmus.models.SongModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class SongListActivity extends AppCompatActivity {

    private ActivitySongListBinding binding;
    private ModernDialog dialog = DialogUtils.dialog;

    private ArrayList<SongModel> list = new ArrayList<>();
    private SongListAdapter songlistadapter = new SongListAdapter(list);

    static ArrayList<Song> audioFiles = new ArrayList<Song>();

    public static ArrayList<Song> getAudioFilesArray() {
        return audioFiles;
    }

    private List<Song> getAudioFilesFromSAF(Activity activity, String dir) {
//        List<Song> audioFiles = new ArrayList<Song>();
        audioFiles.clear();

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM};

//        String selection = MediaStore.Audio.Media.DATA + " like ?";
//        String[] selectionArgs = {getFileContentUri(selectedDirUri).getPath() + "/%"};

        try (Cursor cursor = activity.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)) {
            if (cursor != null) {
                int idCol = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int titleCol = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artistCol = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int durationCol = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int dataCol = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int albumCol = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int rateCol = cursor.getColumnIndex(MediaStore.Audio.Media.BITRATE);
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idCol);
                    String title = cursor.getString(titleCol);
                    String artist = cursor.getString(artistCol);
                    long duration = cursor.getLong(durationCol);
                    String path = cursor.getString(dataCol);
                    String album = cursor.getString(albumCol);
                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                    if (path.startsWith(dir)) {
                        audioFiles.add(new Song(id, title, artist, duration, path, album, contentUri));
                    }
                }
            }
        }

        return audioFiles;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivitySongListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            DialogUtils.showRequirePermissionDialog(this);

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
                String treedir = sh.getString("treedir", "");
                Toast.makeText(this, "dir: " + dir, Toast.LENGTH_LONG).show();

                Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                Log.e("Test", "mdir: " + musicUri);
                Log.e("Test", "dir: " + dir);

                if (!dir.equals("unknown")) {
                    // loop file to screen
                    ArrayList<String> files = new ArrayList<>();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setStackFromEnd(true);
                    linearLayoutManager.setReverseLayout(true);

//                    OnlyDirectorySongListSDcard(dir, treedir);

                    getAudioFilesFromSAF(this, dir);
                    Collections.sort(audioFiles, new Comparator<Song>(){
                        public int compare(Song a, Song b){
                            return a.getTitle().compareTo(b.getTitle());
                        }
                    });

                    for (int i = 0; i < audioFiles.size(); i++) {
                        Song currSong = audioFiles.get(i);

//                        Log.e("wow","Songs: " + currSong.getPath().toString());
                        list.add(new SongModel());
                        list.get(i).setName(currSong.getTitle());
                        list.get(i).setAuthor(currSong.getArtist());
                        list.get(i).setDuration(currSong.getDuration());
                    }
//
                    binding.lvSongList.setLayoutManager(linearLayoutManager);
                    binding.lvSongList.setAdapter(songlistadapter);
                    binding.lvSongList.getAdapter().notifyDataSetChanged();

                } else {
                    Toast.makeText(this, "Song Directory Not Selected", Toast.LENGTH_SHORT).show();
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

        binding.bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
                Log.d("bottom_bar", "Selected index: " + newIndex + ", title: " + newTab.getTitle());
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
//                Log.d("bottom_bar", "Reselected index: " + index + ", title: " + tab.getTitle());
            }
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
}