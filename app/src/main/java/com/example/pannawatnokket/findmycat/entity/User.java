package com.example.pannawatnokket.findmycat.entity;

/**
 * Created by pannawatnokket on 16/11/2017 AD.
 */

public class User {
    private int id;
    private String name;
    private int score;

    public User(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
