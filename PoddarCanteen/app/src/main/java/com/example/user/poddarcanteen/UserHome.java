package com.example.user.poddarcanteen;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;
import android.support.design.widget.BottomNavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity {

    private static final int REQUEST_FODLIST = 0;

    GridLayout mainGrid;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_home);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //setToggleEvent(mainGrid);

//        toolbar = getSupportActionBar();

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_view);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
////        toolbar.setTitle("Shop");
//
//
//
//
//        BottomNavigationView nav_view = (BottomNavigationView) findViewById(R.id.navigationView);



    }

    private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(UserHome.this, "State : True", Toast.LENGTH_SHORT).show();
                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(UserHome.this, "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), admFoodList.class);
                    if (finalI == 0) {
                        intent.putExtra("foodType","Main Item - NonVeg");
                    } else if (finalI == 1) {
                        intent.putExtra("foodType","Main Item - Veg");
                    } else if (finalI == 2) {
                        intent.putExtra("foodType","Chineese");
                    } else if (finalI == 3) {
                        intent.putExtra("foodType","Dessert");
                    } else if (finalI == 4) {
                        intent.putExtra("foodType","Snacks");
                    } else if (finalI == 5) {
                        intent.putExtra("foodType","Drink");
                    }
                    startActivityForResult(intent, REQUEST_FODLIST);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent = new Intent(getApplicationContext(), profilescreen.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0,0);
                    return true;
                case R.id.search:
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

}


//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_home);
//        dl = (DrawerLayout) findViewById(R.id.dl);
//        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
//        abdt.setDrawerIndicatorEnabled(true);
//
//        dl.addDrawerListener(abdt);
//        abdt.syncState();
////
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////
////        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
////        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
////            @Override
////            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
////                int id = menuItem.getItemId();
////                if (id == R.id.settings) {
////                    Intent i = new Intent(getApplicationContext(), Settings.class);
////                    startActivity(i);
////                } else {
////                    Intent i = new Intent(getApplicationContext(), Account.class);
////                    startActivity(i);
////                }
////                return true;
////            }
////        });
////        mRecyclerView = findViewById(R.id.recycler_view);
////        mRecyclerView.setHasFixedSize(true);
////        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
////
////       progressBar = findViewById(R.id.progress_circle);
//
//        mfoods = new ArrayList<>();
//        udatabaseRef = FirebaseDatabase.getInstance().getReference("foodDetails");
//        udatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
//                    Food ufood=postSnapshot.getValue(Food.class);
//                    mfoods.add(ufood);
//                }
//                mAdapter=new FoodImageAdapter(getApplicationContext(),mfoods);
//                mRecyclerView.setAdapter(mAdapter);
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//    }
//}
