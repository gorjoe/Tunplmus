package com.gorjoe.tunplmus;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;

public class SongHandler {
    static MediaPlayer mediaPlayer;

    public void initSong(String path) {
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(path);
            mp.prepare();
            mp.start();

        } catch (IOException e) {

        }
    }

    public void PlaySong(String uri) {
        initSong(uri);
        mediaPlayer.start();
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
