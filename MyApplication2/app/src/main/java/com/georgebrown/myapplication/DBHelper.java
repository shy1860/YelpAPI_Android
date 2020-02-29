package com.georgebrown.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "Business";

    // Table columns
    public static final String ID = "rest_id";
    public static final String IMAGE_URL = "image_url";
    public static final String USER = "user_name";
    public static final String CREATED_ON = "created_on";
    public static final String REST_NAME = "rest_name";
    public static final String ADDRESS = "address";
    public static final String COMMENT = "comment";

    // Database Information
    static final String DB_NAME = "MyFav.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
             ID+ " TEXT PRIMARY KEY , " +
            IMAGE_URL + " TEXT , " +
            USER + " TEXT , " +
            CREATED_ON + " TEXT , " +
            REST_NAME + " TEXT , " +
            ADDRESS + " TEXT , " +
            COMMENT + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
