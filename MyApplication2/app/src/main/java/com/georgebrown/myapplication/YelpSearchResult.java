package com.georgebrown.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 public class YelpSearchResult {
    @SerializedName("businesses")
    ArrayList<Business> restaurants=new ArrayList<>();
    }
class Region{
    public ArrayList<String> transactions = new ArrayList<String>();
}




