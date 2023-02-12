package com.gorjoe.tunplmus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.gorjoe.tunplmus.databinding.ActivityDownloadBinding;

public class DownloadActivity extends AppCompatActivity {

    private ActivityDownloadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityDownloadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);

        getSupportActionBar().setTitle("Download");

        binding.btnDownload.setOnClickListener(v -> download());

    }

    private void download() {
        // SnackbarUtil.makeSnackbar(this, "button clicked");
        startActivity(new Intent(this, MediaPlayerActivity.class));

    }

    private void showDownloadHistory() {

    }

}