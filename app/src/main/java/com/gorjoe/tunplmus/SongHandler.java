package com.gorjoe.tunplmus;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class SongHandler {
    // Create a new MediaPlayer object
    static MediaPlayer mediaPlayer = new MediaPlayer();

    public static void PlaySong(int selectedIndex) {
        // Get the File object for the selected audio file
        Song selectedAudioFile = SongListActivity.getAudioFilesArray().get(selectedIndex);
        String filePath = selectedAudioFile.getPath();
        File selectedFile = new File(filePath);

        try {
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

    public void PauseResumeSong() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();

        } else {
            mediaPlayer.start();
        }
    }

    public void ResumeSong() {
        var length = mediaPlayer.getCurrentPosition();
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
    }

    public void StopSong() {
        mediaPlayer.stop();
    }
}
