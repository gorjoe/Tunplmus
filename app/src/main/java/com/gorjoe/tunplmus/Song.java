package com.gorjoe.tunplmus;

public class Song {

    private long id;
    private String title;
    private String artist;
    private long duration;

    public Song(long songID, String songTitle, String songArtist, long songDuration) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        duration=songDuration;
    }

    public long getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public long getDuration(){
        return duration;
    }

}
