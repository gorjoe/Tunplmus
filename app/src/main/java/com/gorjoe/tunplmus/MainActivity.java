package com.gorjoe.tunplmus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.moderndialog.ModernDialog;
import com.google.android.material.tabs.TabLayout;
import com.gorjoe.tunplmus.Utils.DialogUtils;
import com.gorjoe.tunplmus.Utils.SongHandler;
import com.gorjoe.tunplmus.databinding.ActivityMainBinding;
import com.gorjoe.tunplmus.fragments.DownloadFragment;
import com.gorjoe.tunplmus.fragments.SettingsFragment;
import com.gorjoe.tunplmus.fragments.SongListFragment;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;

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

        LinearLayout currentsong = findViewById(R.id.layoutCurrentSong);
        currentsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = currentsong.getContext();
                Intent intent = new Intent(context, MediaPlayerActivity.class);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition( R.anim.slide_in_up, 0 );
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
                Log.e("navbar", "id is: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
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
}