package com.georgebrown.myapplication;

import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import static com.georgebrown.myapplication.MainActivity.businesses;
import static com.georgebrown.myapplication.MainActivity.grdV;
import static com.georgebrown.myapplication.MainActivity.gridAdapter;
import static com.georgebrown.myapplication.SQLiteDBM.reviews;

public class RetrofitRepo extends Application {

    //Define base url
    public static final String BASE_URL = "https://api.yelp.com/v3/";
    //API Key
    public static String API_KEY="Bearer EIXMis1tr7wIkS9QcssPoFA11Hlt6sxfluj94fC1yhlHuFa_q3SxCaKnDq80IA-sXft7266yD_5di7HTF5buYdN9FnrFwyaDkuG-gRPnGByn7TFT3VfGRP9yDWVYXnYx";
    //Prepare Retrofit with same base URL
    public static YelpService prepRetro(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YelpService yelpService=retrofit.create(YelpService.class);
        return yelpService;
    }
    //If no serach text is provided, will search for all businesses, else search by the words given
    public  void search(String searchT,Context  applicationContext){
        if(searchT.trim()==null ||searchT.trim()==""){
            getAllBz(applicationContext);
        }else{
            YelpService yelpService=prepRetro();
            Call<ResponseBody> call=yelpService.getSearchRest(API_KEY,searchT,"Toronto",10);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String s=response.body().source().readUtf8();
                        checkGson(s, applicationContext);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }
    }
    //Sort the ArrayList
    public static void sort(Boolean flag,Context applicationContext){
        System.out.println("flag:"+flag);
        if(flag){
            businesses.sort(Comparator.comparing(Business::getName));
        }
        else{
            businesses.sort(Comparator.comparing(Business::getName).reversed());
        }
        //Refresh the view
        gridAdapter.notifyDataSetChanged();
    }
    public static void checkGson(String gson, Context applicationContext) throws JSONException {
        businesses.clear();
        JSONObject json = new JSONObject(gson);
        JSONArray busArray = new JSONArray(json.getString("businesses"));
        for (int i = 0; i < busArray.length(); i++) {
            JSONObject obj = busArray.getJSONObject(i);
            Business bus1 = new Business();
            bus1.setId( obj.getString("id"));
            bus1.setName(obj.getString("name"));
            bus1.setImage_url(obj.getString("image_url"));
            bus1.setIs_closed(obj.getString("is_closed"));
            bus1.setUrl(obj.getString("url"));
            bus1.setReview_count(obj.getString("review_count"));
            bus1.setRating(obj.getString("rating"));
            JSONObject addObj = new JSONObject(obj.getString("location"));
            bus1.setAddress(addObj.getString("display_address").replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"",""));
            businesses.add(bus1);
        }
        gridAdapter= new ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, businesses);
        grdV.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }
    //For getting all the businesses
    public  void getAllBz(Context applicationContext){
        YelpService yelpService=prepRetro();
        Call<ResponseBody> call=yelpService.getRest(API_KEY,"Toronto",50);
        call.enqueue(new Callback<ResponseBody> () {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody>  response) {
                try {
                    String s=response.body().source().readUtf8();
                    checkGson(s,applicationContext);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    //Get reviews according to given business ID
    public  void getReview(Business bus1){
        YelpService yelpService=prepRetro();
        Call<ResponseBody> call=yelpService.getReview(API_KEY,bus1.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s=response.body().source().readUtf8();
                    checkReviewGson(bus1,s,reviews);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    //add reviews to reviews arraylist and assign the data to the text field.
    public static void checkReviewGson(Business bus1,String gson,final ArrayList<Review> reviews) throws JSONException {
        reviews.clear();
        JSONObject json = new JSONObject(gson);
        JSONArray busArray = new JSONArray(json.getString("reviews"));
        for (int i = 0; i < busArray.length(); i++) {
            JSONObject obj = busArray.getJSONObject(i);
            Review rev1 = new Review();
            rev1.setText( obj.getString("text"));
            rev1.setPostedAt(obj.getString("time_created"));
            JSONObject commentUser = new JSONObject(obj.getString("user"));
            rev1.setUserName(commentUser.getString("name"));
            reviews.add(rev1);
        }
        reviews.sort(Comparator.comparing(Review::getPostedAt).reversed());
        bus1.setReviewList(reviews);
        BzDetail.txtViewR.setText(reviews.get(0).getText());
        BzDetail.txtRn.setText(bus1.getName().toUpperCase());
        BzDetail.txtAd.setText("Serve you at : "+bus1.getAddress());
        BzDetail.txtUN.setText(reviews.get(0).getUserName());


    }

}
