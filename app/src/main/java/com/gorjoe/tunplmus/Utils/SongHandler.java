package com.gorjoe.tunplmus.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.SeekBar;
import com.gorjoe.tunplmus.R;
import com.gorjoe.tunplmus.Song;
import com.gorjoe.tunplmus.databinding.ActivityMediaPlayerBinding;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SongHandler{
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static Song nowPlaying = null;

    private static boolean wasPlaying = false;
    private static SeekBar sBar;
    public static double sBarStep = 0;
    private static ActivityMediaPlayerBinding objBinding = null;

    public SongHandler(SeekBar seekbar) {
        this.sBar = seekbar;
    }

    public static void setValue(int value) {
        sBar.setProgress((int) Math.round(value / sBarStep));
    }

    public static void initBinding(ActivityMediaPlayerBinding binding) {
        objBinding = binding;
    }

    public static void StopAllSong() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
    }

    public static void PlaySong(int selectedIndex) {
        try {
            Log.e("Song1", "mp is=" + (mediaPlayer != null) + ", wasPlaying=" + wasPlaying + ", mp playing=" + mediaPlayer.isPlaying());

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                StopAllSong();
                setValue(0);
                wasPlaying = false;
                Log.e("Song", "has reset!");
            }

            if (!wasPlaying) {
                Song selectedAudioFile = SongMediaStore.getAudioFilesArray().get(selectedIndex);
                String filePath = selectedAudioFile.getPath();
                File selectedFile = new File(filePath);

                nowPlaying = selectedAudioFile;
                mediaPlayer.setDataSource(selectedFile.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.setLooping(false);
                sBarStep = mediaPlayer.getDuration() / 100;

                mediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PauseResumeSong(Context context) {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            Drawable img = context.getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24);
            img.setBounds(0, 0, 60, 60);
            objBinding.btnPause.setCompoundDrawables(img, null, null, null);

        } else {
            mediaPlayer.start();
            Drawable img = context.getResources().getDrawable(R.drawable.ic_baseline_pause_24);
            img.setBounds(0, 0, 60, 60);
            objBinding.btnPause.setCompoundDrawables(img, null, null, null);
        }
    }

    public static void SeekSong(int time) {
        mediaPlayer.seekTo((int) Math.ceil(time * sBarStep));
    }

    public static void ToggleLoopSong() {
        mediaPlayer.setLooping(!mediaPlayer.isLooping());
    }

    public static String convertToMMSS(long millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
