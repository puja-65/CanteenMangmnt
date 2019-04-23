package com.example.user.poddarcanteen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="canteen.db";
    private static final int DB_VERSION=1;

    public static final String TABLE_NAME="users";
    public static final String COL_1="name";
    public static final String COL_2="email";
    public static final String COL_3="password";
    public static final String COL_4="phone";
    public static final String COL_5="address";

    private static final String CREATE_QUERY="create table users (name text not null,email text not null,Password text not null , phone text not null , address text not null);";

    public UserDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        Log.i("DB Message","DataBase created");
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.i("DB Message","Table Created");
    }

    public Cursor serachData(SQLiteDatabase db,String email){
        Cursor c=db.rawQuery("SELECT "+ COL_1+" FROM " + TABLE_NAME  + " WHERE " + COL_2 + " LIKE" + "'" + email + "'",
                null);
        return c;
    }
    public Cursor serachData1(SQLiteDatabase db,String email){
        Cursor c=db.rawQuery("SELECT "+ COL_4+" FROM " + TABLE_NAME  + " WHERE " + COL_2 + " LIKE" + "'" + email + "'",
                null);
        return c;
    }
    public Cursor serachData2(SQLiteDatabase db,String email){
        Cursor c=db.rawQuery("SELECT "+ COL_5+" FROM " + TABLE_NAME  + " WHERE " + COL_2 + " LIKE" + "'" + email + "'",
                null);
        return c;
    }
    public boolean updatedata(String name,String phone,String address,String password,String email,SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();
        contentValues.put(UserDbHelper.COL_1,name);
        contentValues.put(UserDbHelper.COL_3,password);
        contentValues.put(UserDbHelper.COL_4,phone);
        contentValues.put(UserDbHelper.COL_5,address);

        db.update(TABLE_NAME, contentValues, "email=?", new String[]{ email });
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

