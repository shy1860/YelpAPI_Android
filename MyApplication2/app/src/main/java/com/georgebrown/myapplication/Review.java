package com.georgebrown.myapplication;

import java.io.Serializable;
//Used to define review class to contain informations
public class Review implements Serializable {
    private String postedAt;
    private String text;
    private String userName;
    @Override
    public String toString() {
        return "Review{" +
                "postedAt='" + postedAt + '\'' +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
