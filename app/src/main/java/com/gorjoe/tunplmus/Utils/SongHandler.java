package com.gorjoe.tunplmus.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.SeekBar;

import com.gorjoe.tunplmus.MediaPlayerActivity;
import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.Song;
import com.gorjoe.tunplmus.SongListActivity;
import com.gorjoe.tunplmus.databinding.ActivityMediaPlayerBinding;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SongHandler{
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static Song nowPlaying = null;

    private static boolean wasPlaying = false;
    private static SeekBar sBar;
    public static double sBarStep = 0;
    private static ActivityMediaPlayerBinding objbinding = null;

    public SongHandler(SeekBar seebar) {
        this.sBar = seebar;
    }

    public static void setValue(int value) {
        sBar.setProgress((int) Math.round(value / sBarStep));
    }

    public static void initBinding(ActivityMediaPlayerBinding binding) {
        objbinding = binding;
    }

    public static void StopAllSong() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
    }

    public static void PlaySong(int selectedIndex) {
        try {

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                StopAllSong();
                setValue(0);
                wasPlaying = true;
            }

            if (!wasPlaying) {
                Song selectedAudioFile = SongListActivity.getAudioFilesArray().get(selectedIndex);
                String filePath = selectedAudioFile.getPath();
                File selectedFile = new File(filePath);

                nowPlaying = selectedAudioFile;
//                mediaPlayer.reset();
                mediaPlayer.setDataSource(selectedFile.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.setLooping(false);
                sBarStep = mediaPlayer.getDuration() / 100;

                mediaPlayer.start();
            }
            wasPlaying = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PauseResumeSong(Context context) {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            Drawable img = context.getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24);
            img.setBounds(0, 0, 60, 60);
            objbinding.btnPause.setCompoundDrawables(img, null, null, null);

        } else {
            mediaPlayer.start();
            Drawable img = context.getResources().getDrawable(R.drawable.ic_baseline_pause_24);
            img.setBounds(0, 0, 60, 60);
            objbinding.btnPause.setCompoundDrawables(img, null, null, null);
        }
    }

    public static void SeekSong(int time) {
        mediaPlayer.seekTo((int) Math.ceil(time * sBarStep));
    }

    public static void ToggleLoopSong() {
        if (mediaPlayer.isLooping()) {
            mediaPlayer.setLooping(false);

        } else {
            mediaPlayer.setLooping(true);
        }
    }

    public static String convertToMMSS(long millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
