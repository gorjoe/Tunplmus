package com.gorjoe.tunplmus;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;
import com.gorjoe.tunplmus.databinding.ActivityMainBinding;
import com.gorjoe.tunplmus.fragments.DownloadFragment;
import com.gorjoe.tunplmus.fragments.MediaPlayerFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}