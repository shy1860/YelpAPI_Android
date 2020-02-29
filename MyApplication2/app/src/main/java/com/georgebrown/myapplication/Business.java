package com.georgebrown.myapplication;
import java.io.Serializable;
import java.util.ArrayList;
public class Business implements Serializable {
    public String  id;
    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }
    public ArrayList<Review> reviewList;
    @Override
    public String toString() {
        return "\n" + name + "\n\n" +
                "address :" + address+"\n" ;
    }
    public String name;
    public String image_url;
    public String is_closed;
    public String url;
    public String review_count;
    public String rating;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String address;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public void setIs_closed(String is_closed) {
        this.is_closed = is_closed;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
}