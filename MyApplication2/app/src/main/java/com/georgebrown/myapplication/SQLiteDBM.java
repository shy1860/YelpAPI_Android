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

    public void insert(Business bus, String username,String postAt,String comment) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, bus.getId());
        contentValue.put(DBHelper.IMAGE_URL, bus.getImage_url());
        contentValue.put(DBHelper.USER,username);
        contentValue.put(DBHelper.CREATED_ON, postAt);
        contentValue.put(DBHelper.COMMENT, comment);
        contentValue.put(DBHelper.ADDRESS, bus.getAddress());
        contentValue.put(DBHelper.REST_NAME, bus.getName());

        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DBHelper.ID, DBHelper.IMAGE_URL, DBHelper.USER , DBHelper.CREATED_ON, DBHelper.COMMENT, DBHelper.ADDRESS, DBHelper.REST_NAME};
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public void fillAdaptor(Cursor cursor){
        businesses.clear();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Business bus=new Business();
                bus.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID)));
                bus.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.REST_NAME)));
                bus.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ADDRESS)));
                bus.setImage_url(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.IMAGE_URL)));
                businesses.add(bus);
                cursor.moveToNext();
            }
        }
        cursor.close();

    }



    public boolean delete(String  rID) {
         businesses.removeIf(id -> id.equals(rID));
        gridAdapter.notifyDataSetChanged();
       return database.delete(DBHelper.TABLE_NAME, DBHelper.ID + "='" + rID+"'", null)>0;

    }

}
