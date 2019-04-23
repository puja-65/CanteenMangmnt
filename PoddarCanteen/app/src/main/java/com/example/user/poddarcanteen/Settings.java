package com.example.user.poddarcanteen;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    ImageButton backbtn3;
    EditText nam,pho,addr,passw;
    TextView em;
    Button subm;
    UserDbHelper openHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        em=findViewById(R.id.em);
        em.setText(getIntent().getStringExtra("EMAIL"));
        openHelper=new UserDbHelper(this);
        backbtn3=findViewById(R.id.backbtn3);
        backbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Settings.this,Home.class);
                startActivity(i);
            }
        });

        nam=findViewById(R.id.nam);
        pho=findViewById(R.id.pho);
        addr=findViewById(R.id.addr);
        passw=findViewById(R.id.pas);
        subm=findViewById(R.id.submi);
        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String name=nam.getText().toString();
                String phone=pho.getText().toString();
                String email=em.getText().toString();
                String address=addr.getText().toString();
                String password=passw.getText().toString();
                if(password.length()>7 && password.length()<9) {
                    boolean isUpdate = openHelper.updatedata(name, phone, address, password, email, db);
                    if (isUpdate == true) {
                        Toast.makeText(Settings.this, "Updated", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Settings.this, Home.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Settings.this, "Some Error Occured", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"You have to enter eight elements in your password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
