package com.georgebrown.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

import static com.georgebrown.myapplication.MainActivity.API_KEY;
import static com.georgebrown.myapplication.MainActivity.prepRetro;

public class FavDetail extends AppCompatActivity {
    ImageView imgV11;
    String url;
    Button btnFav,btnBac;
    static Boolean flag=false;
    static TextView txtViewR,txtUN,txtRn,txtAd,txtCA;
    static Business bus1;
    SQLiteDBM dbm;
    public static ArrayList<Review> reviews=new ArrayList<Review>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_detail);
        dbm=new SQLiteDBM(getApplicationContext());
        dbm.open();
        btnBac=findViewById(R.id.btnBack2);
        btnBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MyList.class);
                startActivity((i));
            }
        });
        btnFav=findViewById(R.id.favBtn11);
        bus1=(Business) getIntent().getSerializableExtra("business");
        imgV11=findViewById(R.id.imageView11);
        txtViewR=findViewById(R.id.txtReview11);
        txtCA=findViewById(R.id.txtCreated11);
        txtUN=findViewById(R.id.txtUserName11);
        txtRn=findViewById(R.id.txtRestName11);
        txtAd=findViewById(R.id.txtAddress11);
        url=bus1.image_url;
        loadImg(url);
        Log.i("BUS1","BUS1 "+bus1.toString());
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFav.setEnabled(false);
                Toast.makeText(getApplicationContext(),"Removed",Toast.LENGTH_SHORT).show();
                Log.i("Delete",String.valueOf(dbm.delete(bus1.getId())));
            }
        });
        //txtViewR.setText(reviews.get(0).getText());
        //Toast.makeText(getApplicationContext(),bus1.toString(),Toast.LENGTH_SHORT).show();
        getReview(bus1.getId());
        //Toast.makeText(getApplicationContext(),reviews.toString(),Toast.LENGTH_SHORT).show();
    }

    private void loadImg(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgV11, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                    }
                });
    }
    public  void getReview(String id){
        YelpService yelpService=prepRetro();
        Call<ResponseBody> call=yelpService.getReview(API_KEY,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s=response.body().source().readUtf8();
                    checkReviewGson(s,reviews);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    public static void checkReviewGson(String gson,final ArrayList<Review> reviews) throws JSONException {
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
            //Log.i("Review","reviewGson "+rev1.toString());
            reviews.add(rev1);
            //Log.i("reviews","reviesSize"+reviews.size());
        }
        reviews.sort(Comparator.comparing(Review::getPostedAt).reversed());
        bus1.setReviewList(reviews);
        txtViewR.setText(reviews.get(0).getText());
        txtRn.setText(bus1.getName().toUpperCase());
        txtAd.setText("Serve you at : "+bus1.getAddress());
        txtUN.setText(reviews.get(0).getUserName());
        txtCA.setText(reviews.get(0).getPostedAt());

    }

}
