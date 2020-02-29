package com.georgebrown.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import static com.georgebrown.myapplication.SQLiteDBM.businesses;
import static com.georgebrown.myapplication.SQLiteDBM.reviews;

public class MyList extends AppCompatActivity {
    GridView grdV;
    SQLiteDBM dbm;
    Button btnBack;
    static ArrayAdapter gridAdapter;
    public static float x1,x2,y1,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        grdV=findViewById(R.id.myGrdV);
        btnBack=findViewById(R.id.btnBack1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity((i));
            }
        });
        dbm=new SQLiteDBM(getApplicationContext());
        dbm.open();
        dbm.fillAdaptor(dbm.fetch());
        gridAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, businesses);
        grdV.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        grdV.setBackgroundColor(Color.parseColor("#5997c9"));
        grdV.setOnItemClickListener((adapter, v, position, arg3) -> {
            Business bus1=(Business) adapter.getItemAtPosition(position);
            Review review=reviews.get(position);
            Intent intent =new Intent(getBaseContext(), FavDetail.class);
            intent.putExtra("review", review);
            intent.putExtra("business",bus1);
            startActivity(intent);
        });
    }
    //This is to avoid no update on data if user press the backward button on the device(not the back button provided in the app)
    @Override
    protected void onResume(){
        super.onResume();
        dbm.fillAdaptor(dbm.fetch());
        gridAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, businesses);
        grdV.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }
    //Swipe left and right function
    public boolean onTouchEvent(MotionEvent touchEvent){
        return Utility.onTouchEvent(touchEvent,getApplicationContext());
    }
}
