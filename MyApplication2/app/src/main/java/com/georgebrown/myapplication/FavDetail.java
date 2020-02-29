package com.georgebrown.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class FavDetail extends AppCompatActivity {
    static ImageView imgV;
    String url;
    Button btnFav,btnBac;
    static TextView txtViewR,txtUN,txtRn,txtAd,txtCA;
    static Business bus1;
    static Review rev1;
    private Handler mHandler = new Handler();
    SQLiteDBM dbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_detail);
        rev1=(Review) getIntent().getSerializableExtra("review");
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
        imgV=findViewById(R.id.imageView11);
        imgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(bus1.url));
                startActivity(intent);
            }
        });
        txtViewR=findViewById(R.id.txtReview11);
        txtCA=findViewById(R.id.txtCreated11);
        txtUN=findViewById(R.id.txtUserName11);
        txtRn=findViewById(R.id.txtRestName11);
        txtAd=findViewById(R.id.txtAddress11);
        txtViewR.setText(rev1.getText());
        txtCA.setText(rev1.getPostedAt());
        txtUN.setText(rev1.getUserName());
        txtRn.setText(bus1.getName());
        txtAd.setText(bus1.getAddress());
        url=bus1.image_url;
        Utility.loadFavImg(getApplicationContext(),url);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"It is removed",Toast.LENGTH_SHORT).show();
                //Delay some time to allow user take a look at the toast screen
                mHandler.postDelayed(mUpdateTimeTask, 1000);
            }
            private Runnable mUpdateTimeTask = new Runnable() {
                public void run() {
                    btnFav.setEnabled(false);
                    dbm.delete(bus1.getId());
                    Intent i=new Intent(getBaseContext(),MyList.class);
                    startActivity(i);
                }
            };
        });

    }




}
