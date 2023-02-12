package com.gorjoe.tunplmus.models;

public class HistoryModel {

    String name, time;

    public HistoryModel() {

    }

    public HistoryModel(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
