package com.example.user.poddarcanteen;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    SQLiteDatabase db;
TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.Settings) {
                    String emailva = getIntent().getStringExtra("EMAIL");
                    Intent i = new Intent(getApplicationContext(), Settings.class);
                    i.putExtra("EMAIL", emailva);
                    startActivity(i);
                } else {
                    String emailval = getIntent().getStringExtra("EMAIL");
                    Intent i = new Intent(getApplicationContext(), Account.class);
                    i.putExtra("EMAIL", emailval);
                    startActivity(i);
                }
                return true;
            }
        });
        result = findViewById(R.id.result);
        ViewData();
    }

    public void ViewData() {
        UserDbHelper1 user = new UserDbHelper1(this);
        db = user.getReadableDatabase();
        Cursor c = user.viewData(db);

        if (c.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Items are available", Toast.LENGTH_LONG).show();
        } else {
            c.moveToFirst();

            StringBuffer sb=new StringBuffer();
            do{
                sb.append("Food Name: " + c.getString(0) + "\n");
                sb.append("Food Type: " + c.getString(1) + "\n");
                sb.append("Food Price: " + c.getString(2) + "\n" + "\n");
            }while (c.moveToNext());
            result.setText(sb.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
