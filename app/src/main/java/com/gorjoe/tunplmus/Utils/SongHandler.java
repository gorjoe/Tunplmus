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
import java.util.concurrent.TimeUnit;

public class SongHandler{
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static Song nowPlaying = null;
    private static boolean wasPlaying = false;
    private static SeekBar sBar;

    private static ActivityMediaPlayerBinding objbinding = null;

    public SongHandler(SeekBar seebar) {
        this.sBar = seebar;
    }

    public static void setValue(int value) {
        sBar.setProgress(value);
    }

    public static void setMax(int max) {
        sBar.setMax(max);
    }

    public static void initBinding(ActivityMediaPlayerBinding binding) {
        objbinding = binding;
    }

    public static void updateNowPlayingSongInfo() {
        updatePlayTime();
    }

    public static void updatePlayTime() {
        objbinding.timeNow.setText(convertToMMSS(mediaPlayer.getCurrentPosition()));
        objbinding.timeEnd.setText(convertToMMSS(mediaPlayer.getDuration()));

//        MediaPlayer.OnBufferingUpdateListener bufferingListener = new MediaPlayer.OnBufferingUpdateListener() {
//            public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                //code to increase your secondary seekbar
//                objbinding.seekBar.setProgress(percent);
//            }
//        };
    }

    public static void StopAllSong() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
    }

//    public void run() {
//        int currentPos = mediaPlayer.getCurrentPosition();
//        int total = mediaPlayer.getDuration();
//
//        while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPos < total) {
//            try {
//                Thread.sleep(1000);
//                currentPos = mediaPlayer.getCurrentPosition();
//            } catch (Exception e) {
//                return;
//            }
//
//            sBar.setProgress(currentPos);
//        }
//    }

    public static void updateSeekBar() {
        final int total = mediaPlayer.getDuration();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int currentPos = mediaPlayer.getCurrentPosition();

                while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPos < total) {
                    try {
                        Thread.sleep(1000);
                        currentPos = mediaPlayer.getCurrentPosition();
                    } catch (Exception e) {
                        return;
                    }
                    setValue(currentPos);
                }
            }
        }).start();
    }


    public static void PlaySong(int selectedIndex) {
        try {

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                StopAllSong();
//                objbinding.seekBar.setProgress(0);
                setValue(0);
                wasPlaying = true;
            }

            if (!wasPlaying) {
                // Get the File object for the selected audio file
                Song selectedAudioFile = SongListActivity.getAudioFilesArray().get(selectedIndex);
                String filePath = selectedAudioFile.getPath();
                File selectedFile = new File(filePath);

                nowPlaying = selectedAudioFile;
                mediaPlayer.reset();
                mediaPlayer.setDataSource(selectedFile.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.setLooping(false);
//                objbinding.seekBar.setMax(mediaPlayer.getDuration());
                Log.e("long", "duration: " + mediaPlayer.getDuration());
//                MediaPlayerActivity.songHand.setMax(mediaPlayer.getDuration());
//                setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                updateSeekBar();
            }
            wasPlaying = false;
        } catch (IOException e) {
            // Handle any errors that occur
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
        mediaPlayer.seekTo(time);
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
