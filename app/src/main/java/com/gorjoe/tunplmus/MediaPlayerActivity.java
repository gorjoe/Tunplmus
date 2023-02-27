package com.gorjoe.tunplmus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.gorjoe.tunplmus.Utils.SongHandler;
import com.gorjoe.tunplmus.databinding.ActivityMediaPlayerBinding;

import static com.gorjoe.tunplmus.Utils.SongHandler.updatePlayTime;

public class MediaPlayerActivity extends AppCompatActivity {

    public static ActivityMediaPlayerBinding binding;
    public static SongHandler songHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
        songHand = new SongHandler(binding.seekBar);
        SongHandler.initBinding(binding);
        SongHandler.updateNowPlayingSongInfo();

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                updatePlayTime();
            }
        });

        binding.btnPause.setOnClickListener(v -> {
            SongHandler.PauseResumeSong(this);
        });
        binding.btnLoop.setOnClickListener(v -> {
            SongHandler.ToggleLoopSong();
        });
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int seektime = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seektime = i;

                int x = (int) Math.ceil(i / 1000f);

                if (x <= 0 && SongHandler.mediaPlayer != null && !SongHandler.mediaPlayer.isPlaying()) {
                    SongHandler.StopAllSong();
                    songHand.setValue(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (SongHandler.mediaPlayer != null && SongHandler.mediaPlayer.isPlaying()) {
                    SongHandler.SeekSong(binding.seekBar.getProgress());
                }
            }
        });
    }

    public static SongHandler getSongHandler() {
        return songHand;
    }

    private void init(Bundle savedInstanceState) {
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);

        getSupportActionBar().setTitle("Media Player");
    }
}