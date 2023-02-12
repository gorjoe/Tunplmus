package com.gorjoe.tunplmus.models;

public class SongModel {

    String name, author;

    public SongModel() {

    }

    public SongModel(String name, String author) {
        this.name = name;
        this.author = author;
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

}
