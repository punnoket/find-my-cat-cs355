package com.moblieapp.pannawatnokket.findmycat.entity;

/**
 * Created by pannawatnokket on 16/11/2017 AD.
 */

public class User {
    private long id;
    private String name;
    private String idGlobal;
    private int score;

    public User() {

    }

    public User(long id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setIdGlobal(String idGlobal) {
        this.idGlobal = idGlobal;
    }

    public String getIdGlobal() {
        return idGlobal;
    }
}
