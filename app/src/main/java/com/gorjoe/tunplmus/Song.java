package com.gorjoe.tunplmus;

import android.net.Uri;

public class Song {

    private long sid;
    private String stitle;
    private String sartist;
    private long sduration;
    private String spath;
    private String salbum;
    private Uri scontentUri;

    public Song(long id, String title, String artist, long duration, String path, String album, Uri contentUri) {
        sid = id;
        stitle = title;
        sartist = artist;
        sduration = duration;
        spath = path;
        salbum = album;
        scontentUri = contentUri;
    }

    public long getId() {
        return sid;
    }

    public String getTitle(){
        return stitle;
    }

    public String getArtist(){
        return sartist;
    }

    public long getDuration(){
        return sduration;
    }

    public String getPath() {
        return spath;
    }

    public String getAlbum() {
        return salbum;
    }

    public Uri getContentUri() {
        return scontentUri;
    }

}
