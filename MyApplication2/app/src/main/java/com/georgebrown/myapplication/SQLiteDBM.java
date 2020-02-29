package com.georgebrown.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import static com.georgebrown.myapplication.MyList.gridAdapter;

public class SQLiteDBM {
    public static final ArrayList<Business>businesses=new ArrayList<Business>();
    public static ArrayList<Review>reviews=new ArrayList<Review>();
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public SQLiteDBM(Context c) {
        context = c;
    }
    public SQLiteDBM open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    //Insert into database
    public void insert(Business bus, String username,String postAt,String comment) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, bus.getId());
        contentValue.put(DBHelper.IMAGE_URL, bus.getImage_url());
        contentValue.put(DBHelper.USER,username);
        contentValue.put(DBHelper.CREATED_ON, postAt);
        contentValue.put(DBHelper.COMMENT, comment);
        contentValue.put(DBHelper.ADDRESS, bus.getAddress());
        contentValue.put(DBHelper.REST_NAME, bus.getName());
        contentValue.put(DBHelper.URL, bus.getUrl());

        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    //Get all records from SQLite database
    public Cursor fetch() {
        String[] columns = new String[] { DBHelper.ID, DBHelper.IMAGE_URL, DBHelper.USER , DBHelper.CREATED_ON, DBHelper.COMMENT, DBHelper.ADDRESS, DBHelper.REST_NAME,DBHelper.URL};
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Extract data from cursor, fill into ArrayList
    public void fillAdaptor(Cursor cursor){
        businesses.clear();
        reviews.clear();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Business bus=new Business();
                Review review=new Review();
                bus.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID)));
                bus.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.URL)));
                bus.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.REST_NAME)));
                bus.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ADDRESS)));
                bus.setImage_url(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.IMAGE_URL)));
                review.setPostedAt(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CREATED_ON)));
                review.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COMMENT)));
                review.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USER)));
                businesses.add(bus);
                reviews.add(review);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
    //Delete record in SQLite
    public boolean delete(String  rID) {
         businesses.removeIf(id -> id.equals(rID));
        gridAdapter.notifyDataSetChanged();
       return database.delete(DBHelper.TABLE_NAME, DBHelper.ID + "='" + rID+"'", null)>0;

    }

}
