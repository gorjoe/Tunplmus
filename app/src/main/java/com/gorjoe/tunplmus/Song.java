package com.gorjoe.tunplmus;

public class Song {

    private String path;
    private String title;
    private String artist;
    private String album;
    private long duration;

    public Song(String songFilePath, String songTitle, String songArtist, String songAlbum, long songDuration) {
        path = songFilePath;
        title=songTitle;
        artist=songArtist;
        album=songAlbum;
        duration=songDuration;
    }

    public String getPath(){
        return path;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public long getDuration(){
        return duration;
    }

}
