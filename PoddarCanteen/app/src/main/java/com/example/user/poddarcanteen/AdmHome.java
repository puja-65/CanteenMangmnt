package com.example.user.poddarcanteen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdmHome extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    EditText fname,fprice,food_type;
    Button enter,order_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_home);
        openHelper = new UserDbHelper1(this);

        fname=findViewById(R.id.fname);
        fprice=findViewById(R.id.fprice);
        food_type=findViewById(R.id.food_type);
        enter=findViewById(R.id.enter);
        order_list=findViewById(R.id.order_list);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String fd_name = fname.getText().toString();
                String fd_price = fprice.getText().toString();
                String fd_type = food_type.getText().toString();

                if(fd_name.length()>1 && fd_type.length()>1 && fd_price.length()>1){
                    insertData(fd_name, fd_type, fd_price);
                    Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getApplicationContext(),Home.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"You have to enter all the data",Toast.LENGTH_LONG).show();
                }

            }
        });
    }public void insertData(String f_name, String f_type, String f_price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDbHelper1.COL_1, f_name);
        contentValues.put(UserDbHelper1.COL_2, f_type);
        contentValues.put(UserDbHelper1.COL_3, f_price);
        long id = db.insert(UserDbHelper1.TABLE_NAME, null, contentValues);
    }
}
