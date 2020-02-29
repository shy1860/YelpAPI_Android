package com.georgebrown.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class BzDetail extends AppCompatActivity {
    static ImageView imgV;
    String url;
    Button btnFav,btnB;
    static Boolean flag=false;
    static TextView txtViewR,txtUN,txtRn,txtAd,txtCA;
    SQLiteDBM dbm;
    Business bus1;
    public static ArrayList<Review> reviews=new ArrayList<Review>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bz_detail);
        txtViewR=findViewById(R.id.txtReview);
        txtCA=findViewById(R.id.txtCreated);
        txtUN=findViewById(R.id.txtUserName);
        txtRn=findViewById(R.id.txtRestName);
        txtAd=findViewById(R.id.txtAddress);
        RetrofitRepo repo=new RetrofitRepo();
        bus1=(Business) getIntent().getSerializableExtra("business");
        repo.getReview(bus1);
        dbm=new SQLiteDBM(getApplicationContext());
        dbm.open();
        btnB=findViewById(R.id.btnBack4);
        btnB.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity((i));
        });
        imgV=findViewById(R.id.imageView);
        imgV.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(bus1.url));
            startActivity(intent);
        });
        url=bus1.image_url;
        Utility.loadBZImg(getApplicationContext(),url);
        btnFav=findViewById(R.id.favBtn);
        btnFav.setOnClickListener(v -> {
            btnFav.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Added to your fav list",Toast.LENGTH_SHORT).show();
            dbm.insert(bus1,txtUN.getText().toString(),txtCA.getText().toString(),txtViewR.getText().toString());
        });
    }
}
