package com.gorjoe.tunplmus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.gorjoe.tunplmus.Utils.SongHandler;
import com.gorjoe.tunplmus.databinding.ActivityMediaPlayerBinding;

import static com.gorjoe.tunplmus.Utils.SongHandler.updatePlayTime;

public class MediaPlayerActivity extends AppCompatActivity {

    private ActivityMediaPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
        SongHandler.updateNowPlayingSongInfo(binding);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                updatePlayTime(binding);
            }
        });

        binding.btnPause.setOnClickListener(v -> {
            SongHandler.PauseResumeSong();
        });
        binding.btnLoop.setOnClickListener(v -> {
            SongHandler.ToggleLoopSong();
        });
    }

    private void init(Bundle savedInstanceState) {
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);

        getSupportActionBar().setTitle("Media Player");
    }
}