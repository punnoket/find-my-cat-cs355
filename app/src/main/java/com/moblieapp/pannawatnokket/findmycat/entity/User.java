package com.moblieapp.pannawatnokket.findmycat.entity;

import java.util.Date;

/**
 * Created by pannawatnokket on 16/11/2017 AD.
 */

public class User {
    private long id;
    private String name;
    private String idGlobal;
    private int score;
    private Date createDate;
    private Date modified;
    private String createDateString;
    private String modifiedString;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date create) {
        this.createDate = create;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getCreateDateString() {
        return createDateString;
    }

    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
    }

    public String getModifiedString() {
        return modifiedString;
    }

    public void setModifiedString(String modifiedString) {
        this.modifiedString = modifiedString;
    }
}
