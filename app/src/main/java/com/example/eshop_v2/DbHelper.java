package com.example.eshop_v2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.icu.lang.UProperty.NAME;
import static android.provider.BaseColumns._ID;

/**
 * Created by delaroy on 9/8/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "image.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;

    public final static String COLUMN_IMAGE = "image";
    public static final String COLUMN_NAME = "imagename";
    public final static String TABLE_NAME = "imagetable";
    public static final String COLUMN_ID = "_id";




    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_IMAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY ," +
                COLUMN_IMAGE + " BLOB NOT NULL," +
                COLUMN_NAME + " TEXT" + ");";

        db.execSQL(SQL_CREATE_IMAGE_TABLE);

        Log.d(TAG, "Database Created Successfully" );

    }

    public void addToDb(int productId, byte[] image , String productName){
        ContentValues cv = new  ContentValues();
        cv.put(_ID, productId);
        cv.put(COLUMN_NAME, productName);
        cv.put(COLUMN_IMAGE,   image);
        db.insert( TABLE_NAME, null, cv );
        Log.d(TAG, "SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + " = " + productId + " AND " + COLUMN_NAME + " = " + productName + " AND " +COLUMN_IMAGE + "=" + image );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllData(int id , String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + " = " + id + " AND " + COLUMN_NAME + " = '" + name + "'" , null);
        Log.d(TAG,_ID + " = " + id+" AND " + COLUMN_NAME + " = '" + name);
        return cursor;
    }

    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return cursor;
    }
    public void deleteColumn(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ? " , new String[]{String.valueOf(id)});
    }


}

