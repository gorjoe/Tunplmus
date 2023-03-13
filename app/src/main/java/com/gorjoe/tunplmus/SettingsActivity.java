package com.gorjoe.tunplmus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.gorjoe.tunplmus.databinding.ActivitySettingsBinding;
import com.gorjoe.tunplmus.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);

        getSupportActionBar().setTitle("Settings");

        if (findViewById(R.id.frameLayout) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new SettingsFragment()).commit();
        }
    }

}