package com.example.user.poddarcanteen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDbHelper1 extends SQLiteOpenHelper {
    private static final String DB_NAME="canteen1.db";
    private static final int DB_VERSION=1;

    public static final String TABLE_NAME="users1";
    public static final String COL_1="food_name";
    public static final String COL_2="food_type";
    public static final String COL_3="food_price";

    private static final String CREATE_QUERY="create table users1 (food_name text not null,food_type text not null,food_price text not null);";

    public UserDbHelper1(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        Log.i("DB Message","DataBase created");
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.i("DB Message","Table Created");
    }
    public Cursor viewData(SQLiteDatabase db){
        Cursor c=db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return c;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
