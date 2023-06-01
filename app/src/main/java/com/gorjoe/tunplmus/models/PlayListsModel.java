package com.gorjoe.tunplmus.models;

public class PlayListsModel {

    String name;
    int total;

    public PlayListsModel() {

    }

    public PlayListsModel(String name, int total) {
        this.name = name;
        this.total = total;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTotal() {
        return String.valueOf(total);
    }

}
