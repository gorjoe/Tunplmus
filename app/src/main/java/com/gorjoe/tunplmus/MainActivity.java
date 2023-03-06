package com.gorjoe.tunplmus;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.gorjoe.tunplmus.databinding.ActivityMainBinding;
import com.gorjoe.tunplmus.fragments.DownloadFragment;
import com.gorjoe.tunplmus.fragments.SongListFragment;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {
                Fragment fragment = null;
                switch (i1) {
                    case 0:
                        fragment = new SongListFragment();
                        break;
                    case 1:
                        fragment = new DownloadFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongListFragment()).commit();
    }
}
