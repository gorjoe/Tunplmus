package com.gorjoe.tunplmus.models;

public class SongModel {

    String name, author;
    long duration;

    public SongModel() {

    }

    public SongModel(String name, String author, long duration) {
        this.name = name;
        this.author = author;
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

}
