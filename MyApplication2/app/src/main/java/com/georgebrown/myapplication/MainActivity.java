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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final ArrayList<Business> businesses = new ArrayList<Business>();
    public static ArrayAdapter gridAdapter;
    static GridView grdV;
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
        RetrofitRepo repo=new RetrofitRepo();
        btnS.setOnClickListener(v -> repo.search(txtS.getText().toString(),getApplicationContext()));
        //Update view
        repo.getAllBz(getApplicationContext());
        btnSort.setOnClickListener(v -> {
            repo.sort(sortFlag,getApplicationContext());
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
       return Utility.onTouchEvent(touchEvent,getApplicationContext());
    }
}
