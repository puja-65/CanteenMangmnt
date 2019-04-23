package com.example.user.poddarcanteen;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {
    TextView sign;
    Button login, press;
    EditText email, password, admin;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        openHelper = new UserDbHelper(this);
        db = openHelper.getReadableDatabase();
        sign = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailb = email.getText().toString();
                String passw = password.getText().toString();
                cursor = db.rawQuery("select * from " + UserDbHelper.TABLE_NAME + " Where " + UserDbHelper.COL_2 + "=? AND " + UserDbHelper.COL_3 + "=?", new String[]{emailb, passw});
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToNext();
                        String emailvalue = emailb;
                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LogIn.this, Home.class);
                        i.putExtra("EMAIL", emailvalue);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Existed", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this, SignUp.class);
                startActivity(i);
            }
        });
        admin = findViewById(R.id.edit_admin);
        press = findViewById(R.id.press);
        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin.getText().toString().equals("abc12")) {
                    Intent i = new Intent(getApplicationContext(), AdmHome.class);
                    startActivity(i);
                }
            }
        });
    }
}
