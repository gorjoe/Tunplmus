package com.gorjoe.tunplmus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.moderndialog.ModernDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gorjoe.tunplmus.Utils.DialogUtils;
import com.gorjoe.tunplmus.Utils.SongHandler;
import com.gorjoe.tunplmus.Utils.SongMediaStore;
import com.gorjoe.tunplmus.databinding.ActivityMainBinding;
import com.gorjoe.tunplmus.fragments.DownloadFragment;
import com.gorjoe.tunplmus.fragments.SettingsFragment;
import com.gorjoe.tunplmus.fragments.SongListFragment;
import com.l4digital.fastscroll.FastScrollRecyclerView;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;

import java.util.ArrayList;

import static com.gorjoe.tunplmus.Utils.SongMediaStore.songlistadapter;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ModernDialog dialog = DialogUtils.dialog;

    private int lastTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongListFragment()).commit();

        LinearLayout currentSong = findViewById(R.id.layoutCurrentSong);
        currentSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = currentSong.getContext();
                Intent intent = new Intent(context, MediaPlayerActivity.class);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.slide_in_up, 0 );
            }
        });

        TabLayout tb = findViewById(R.id.bottom_bar);
        tb.addTab(tb.newTab().setIcon(R.drawable.ic_baseline_music_note_24));
        tb.addTab(tb.newTab().setIcon(R.drawable.ic_baseline_list_24));
        tb.addTab(tb.newTab().setIcon(R.drawable.ic_baseline_download_24));
        tb.addTab(tb.newTab().setIcon(R.drawable.ic_baseline_settings_24));

        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                Log.e("navbar", "id is: " + tab.getPosition() + ", lasttab: " + lastTab);
                switch (tab.getPosition()) {
                    case 0:
//                        SharedPreferences sp = getSharedPreferences("SongList", Context.MODE_PRIVATE);
//                        SongMediaStore.renderSongList(sp, MainActivity.this);

                        lastTab = 0;
                        fragment = new SongListFragment();
                        break;
                    case 2:
                        lastTab = 2;
                        fragment = new DownloadFragment();
                        break;
                    case 3:
//                        startActivity(new Intent(getBaseContext(), SettingsActivity.class));
                        lastTab = 3;
                        fragment = new SettingsFragment();
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        try {
            YoutubeDL.getInstance().init(this);

        } catch (YoutubeDLException e) {
            Log.e("ytdl", "failed to initialize youtubedl-android", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout currentsong = findViewById(R.id.layoutCurrentSong);
        TextView tvSongName = currentsong.findViewById(R.id.tvSongName);
        Log.e("main", "np=" + SongHandler.nowPlaying);
        if (SongHandler.nowPlaying != null) {
            Log.e("main", "title=" + SongHandler.nowPlaying.getTitle());
            tvSongName.setText(SongHandler.nowPlaying.getTitle());
        }

        SharedPreferences sp = getSharedPreferences("SongList", Context.MODE_PRIVATE);
        SongMediaStore.renderSongList(sp, MainActivity.this);

        TabLayout tb = findViewById(R.id.bottom_bar);
        tb.getTabAt(lastTab).select();

        if (!PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            DialogUtils.showRequirePermissionDialog(this);

        } else {
            if (dialog != null && dialog.dialogDef.isShowing()) {
                dialog.dialogDef.dismiss();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_loadSongs:
                LoadSongs();
                return true;

            case R.id.menu_downloadHistory:
                Log.e("goto", "clicked down history");
                SharedPreferences sp = getSharedPreferences("SongList", Context.MODE_PRIVATE);
                ArrayList<ArrayList<String>> playlist = SongMediaStore.fetchSongFromSharePreferences(sp);
                Log.e("sl", "loaded sl: " + playlist);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void LoadSongs() {
        Log.e("load", "loading songs");
        if (PermissionUtil.isAlreadyGrantedExternalStorageAccess()) {
            SharedPreferences sp = getSharedPreferences("directory", Context.MODE_PRIVATE);
            SharedPreferences sl = getSharedPreferences("SongList", Context.MODE_PRIVATE);
//            SongMediaStore.FilterOnlySongInSpecifyDirectory(this, sp, sl);
            SongMediaStore.getAllAudioFiles(this, sp.getString("directory", "unknown"), sl);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
            FastScrollRecyclerView songList = findViewById(R.id.lvSongList);
            songList.setLayoutManager(linearLayoutManager);
            songList.setAdapter(songlistadapter);
            songList.getAdapter().notifyDataSetChanged();
        }
    }
}