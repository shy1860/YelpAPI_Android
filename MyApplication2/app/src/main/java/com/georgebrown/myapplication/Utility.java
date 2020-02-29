package com.georgebrown.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class Utility extends AppCompatActivity {
    public static float x1,x2,y1,y2;

    //To allow swipe left or right gesure
    public static boolean onTouchEvent(MotionEvent touchEvent, Context context){
        switch(touchEvent.getAction()){
            //Start point
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            //End point
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                    Intent i = new Intent(context, MyList.class);
                    //Regular class call activity need use .setFlags method
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }else if(x1 >  x2){
                    Intent i = new Intent(context, MyList.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                break;
        }
        return false;
    }
    // Load Image to business image view using picasso library
    public static void loadBZImg(Context context,String url) {
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(BzDetail.imgV, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                    }
                });
    }
    //Load Image to Favorite detail image view using picasso library
    public static void loadFavImg(Context context,String url) {
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(FavDetail.imgV, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                    }
                });
    }

}
