package com.gorjoe.tunplmus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.gorjoe.tunplmus.databinding.ActivityMediaPlayerBinding;
import com.gorjoe.tunplmus.databinding.ActivitySettingsBinding;

public class MediaPlayerActivity extends AppCompatActivity {

    private ActivityMediaPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);

        getSupportActionBar().setTitle("Media Player");
    }
}