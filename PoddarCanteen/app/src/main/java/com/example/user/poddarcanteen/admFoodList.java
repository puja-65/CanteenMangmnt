package com.example.user.poddarcanteen;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class admFoodList  extends AppCompatActivity {
    private static final int REQUEST_AddFood = 0;

    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        fab = (FloatingActionButton)findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add_Item.class);
                startActivityForResult(intent, REQUEST_AddFood);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


}
