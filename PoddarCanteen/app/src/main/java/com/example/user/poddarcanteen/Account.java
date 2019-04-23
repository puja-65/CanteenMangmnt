package com.example.user.poddarcanteen;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Account extends AppCompatActivity {
    ImageButton backbtn1;
    UserDbHelper openHelper;
    SQLiteDatabase db;
    TextView em, nam, phon, addre;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        em = findViewById(R.id.ema);
        em.setText(getIntent().getStringExtra("EMAIL"));
        openHelper = new UserDbHelper(this);
        backbtn1 = findViewById(R.id.backbtn1);
        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Account.this, Home.class);
                startActivity(i);
            }
        });
        nam = findViewById(R.id.nam);
        phon = findViewById(R.id.phon);
        addre = findViewById(R.id.addre);
        btn1 = findViewById(R.id.press1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getReadableDatabase();
                Cursor c = openHelper.serachData(db, em.getText().toString());
                Cursor c1 = openHelper.serachData1(db, em.getText().toString());
                Cursor c2 = openHelper.serachData2(db, em.getText().toString());

                if (c.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                } else {
                    c.moveToFirst();

                    do {
                        nam.setText(c.getString(0));
                    } while (c.moveToNext());
                }if (c1.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                } else {
                    c1.moveToFirst();

                    do {
                        phon.setText(c1.getString(0));
                    } while (c1.moveToNext());
                }if (c2.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                } else {
                    c2.moveToFirst();

                    do {
                        addre.setText(c2.getString(0));
                    } while (c2.moveToNext());
                }
            }
        });

    }
}
