package com.georgebrown.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
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

public class MainActivity extends AppCompatActivity {
    public static final ArrayList<Business> businesses = new ArrayList<Business>();
    public static float x1,x2,y1,y2;
    public static final String BASE_URL = "https://api.yelp.com/v3/";
    public static String API_KEY="Bearer EIXMis1tr7wIkS9QcssPoFA11Hlt6sxfluj94fC1yhlHuFa_q3SxCaKnDq80IA-sXft7266yD_5di7HTF5buYdN9FnrFwyaDkuG-gRPnGByn7TFT3VfGRP9yDWVYXnYx";
    public static ArrayAdapter gridAdapter;
    GridView grdV;
    EditText txtS;
    Button btnS,btnSort;
    public static Boolean sortFlag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String BASE_URL = "https://api.yelp.com/v3/";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtS=findViewById(R.id.txtKeywords);
        btnS=findViewById(R.id.btnSearch);
        btnSort=findViewById(R.id.btnSort);
        grdV=findViewById(R.id.gridView);

        btnS.setOnClickListener(v -> search(txtS.getText().toString()));
        runMe();
        btnSort.setOnClickListener(v -> {
            sort(sortFlag);
            sortFlag=!sortFlag;
        });
        grdV.setBackgroundColor(Color.parseColor("#786fa8"));
        grdV.setOnItemClickListener((adapter, v, position, arg3) -> {
            Business bus1=(Business) adapter.getItemAtPosition(position);

            Intent intent =new Intent(getBaseContext(), BzDetail.class);
            intent.putExtra("business",bus1);
            startActivity(intent);
        });

        }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                 x1 = touchEvent.getX();
                 y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                 x2 = touchEvent.getX();
                 y2 = touchEvent.getY();
                if(x1 < x2){
                    Toast.makeText(getApplicationContext(),"You swiped left",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, MyList.class);
                startActivity(i);
            }else if(x1 >  x2){
                    Toast.makeText(getApplicationContext(),"You swiped right",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, MyList.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }
    public void runMe( ){
        YelpService yelpService=prepRetro();
        Call<ResponseBody> call=yelpService.getRest(API_KEY,"Toronto",50);
        call.enqueue(new Callback<ResponseBody> () {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody>  response) {
                try {
                    String s=response.body().source().readUtf8();
                    checkGson(s);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    public static YelpService prepRetro(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/v3/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YelpService yelpService=retrofit.create(YelpService.class);
        return yelpService;
    }
    public void search(String searchT){

        YelpService yelpService=prepRetro();
        Call<ResponseBody> call=yelpService.getSearchRest(API_KEY,searchT,"Toronto",10);
        call.enqueue(new Callback<ResponseBody> () {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody>  response) {
                try {
                    String s=response.body().source().readUtf8();
                    checkGson(s);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    public void sort(Boolean flag){
        System.out.println("flag:"+flag);
        if(flag){
            businesses.sort(Comparator.comparing(Business::getName));
        }
        else{
            businesses.sort(Comparator.comparing(Business::getName).reversed());
        }

        gridAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, businesses);
        grdV.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }



    public void checkGson(String gson) throws JSONException {
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
        gridAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, businesses);
        grdV.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

}
