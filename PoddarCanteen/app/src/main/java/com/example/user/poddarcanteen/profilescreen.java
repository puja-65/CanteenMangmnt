package com.example.user.poddarcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class profilescreen extends AppCompatActivity   {

    private TextView search;
    private EditText searchValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        search = findViewById(R.id.search);
        searchValue = findViewById(R.id.searchfield);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchAndDisplayList();
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    Toast.makeText(profilescreen.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.search:
                    Intent intent = new Intent(getApplicationContext(), UserHome.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0,0);
                    return true;
                case R.id.cart:
                    Intent intentProfile = new Intent(getApplicationContext(), cartActivity.class);
                    startActivity(intentProfile);
                    finish();
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };

    void searchAndDisplayList(){
        Intent intent = new Intent(getApplicationContext(), admFoodList.class);
        intent.putExtra("searchstring",searchValue.getText().toString());
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

}
