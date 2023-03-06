package com.gorjoe.tunplmus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.bluewhaleyt.common.PermissionUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.moderndialog.ModernDialog;
import com.google.android.material.tabs.TabLayout;
import com.gorjoe.tunplmus.Utils.DialogUtils;
import com.gorjoe.tunplmus.Utils.SongMediaStore;
import com.gorjoe.tunplmus.databinding.ActivityMainBinding;
import com.gorjoe.tunplmus.fragments.DownloadFragment;
import com.gorjoe.tunplmus.fragments.MediaPlayerFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ModernDialog dialog = DialogUtils.dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MediaPlayerFragment()).commit();

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
                        fragment = new MediaPlayerFragment();
                        break;
                    case 2:
                        fragment = new DownloadFragment();
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
            SharedPreferences sp = getSharedPreferences("directory", Context.MODE_PRIVATE);
            SongMediaStore.FilterOnlySongInSpecifyDirectory(this, sp);

            // the below code not working
            View sl = findViewById(R.id.lvSongsList);
            binding.lvSongList.setLayoutManager(linearLayoutManager);
            binding.lvSongList.setAdapter(songlistadapter);
            binding.lvSongList.getAdapter().notifyDataSetChanged();
        }
    }
}