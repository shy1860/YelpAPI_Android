package com.georgebrown.myapplication;

public class Review {
    private String postedAt;
    private String text;

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

    private String userName;
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
