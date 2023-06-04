package com.gorjoe.tunplmus;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.gorjoe.tunplmus.Utils.SongHandler;
import com.gorjoe.tunplmus.databinding.ActivityMediaPlayerBinding;

public class MediaPlayerActivity extends AppCompatActivity {

    public static ActivityMediaPlayerBinding binding;
    public static SongHandler songHand;

    private static boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
        songHand = new SongHandler(binding.seekBar);
        SongHandler.initBinding(binding);
        binding.timeEnd.setText(SongHandler.convertToMMSS(SongHandler.mediaPlayer.getDuration()));

        binding.btnPause.setOnClickListener(v -> {
            SongHandler.PauseResumeSong(this);
        });
        binding.btnLoop.setOnClickListener(v -> {
            SongHandler.ToggleLoopSong();
        });
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int seekTime = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekTime = i;

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

    @Override
    protected void onStart() {
        super.onStart();
        Runnable songRunnable = new Runnable() {
            @Override
            public void run() {
                MediaPlayer mediaPlayer = SongHandler.mediaPlayer;
                final int total = mediaPlayer.getDuration();
                int currentPos = SongHandler.mediaPlayer.getCurrentPosition();

                while (!stopThread && mediaPlayer != null && mediaPlayer.isPlaying() && currentPos < total) {
                    try {
//                        Thread.sleep(500);
                        synchronized (this) {
                            wait(1000);

                            currentPos = mediaPlayer.getCurrentPosition();
                            int finalCurrentPos = currentPos;

                            runOnUiThread(() -> {
                                binding.timeNow.setText(SongHandler.convertToMMSS(finalCurrentPos));
                                binding.seekBar.setProgress((int) Math.round(finalCurrentPos / SongHandler.sBarStep));
                                Log.e("loop", "loop pos: " + (int) Math.round(finalCurrentPos / SongHandler.sBarStep) + " / " + binding.seekBar.getMax());
                            });
                        }
                    } catch (Exception e) {
                        return;
                    }
                }
                return;
            }
        };
        Thread thread = new Thread(songRunnable);
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        stopThread();
    }

    public static void stopThread() {
        stopThread = true;
    }

    private void init(Bundle savedInstanceState) {
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);
        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);

        getSupportActionBar().setTitle("Media Player");
    }
}