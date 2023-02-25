package com.gorjoe.tunplmus.Utils;

import android.media.MediaPlayer;
import android.os.Handler;

import com.gorjoe.tunplmus.Song;
import com.gorjoe.tunplmus.SongListActivity;
import com.gorjoe.tunplmus.databinding.ActivityMediaPlayerBinding;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SongHandler {
    
    static MediaPlayer mediaPlayer = new MediaPlayer();
    static Song nowPlaying = null;

    public static void updateNowPlayingSongInfo(ActivityMediaPlayerBinding binding) {
        updatePlayTime(binding);
    }

    public static void updatePlayTime(ActivityMediaPlayerBinding binding) {
        binding.timeNow.setText(convertToMMSS(mediaPlayer.getCurrentPosition()));
        binding.timeEnd.setText(convertToMMSS(mediaPlayer.getDuration()));
    }

    public static void PlaySong(int selectedIndex) {
        // Get the File object for the selected audio file
        Song selectedAudioFile = SongListActivity.getAudioFilesArray().get(selectedIndex);
        String filePath = selectedAudioFile.getPath();
        File selectedFile = new File(filePath);

        nowPlaying = selectedAudioFile;

        try {
            // Reset the MediaPlayer before setting to data source
            mediaPlayer.reset();

            // Set the selected audio file as the data source for the MediaPlayer
            mediaPlayer.setDataSource(selectedFile.getAbsolutePath());

            // Prepare the MediaPlayer for playback
            mediaPlayer.prepare();

            // Start playback
            mediaPlayer.start();
        } catch (IOException e) {
            // Handle any errors that occur
            e.printStackTrace();
        }
    }

    public static void PauseResumeSong() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();

        } else {
            mediaPlayer.start();
        }
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
