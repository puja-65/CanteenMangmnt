package com.example.user.poddarcanteen;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import java.util.HashMap;
import java.util.List;

public class AdmHome extends AppCompatActivity implements OnItemSelectedListener  {

    private static final int REQUEST_FoodList = 1;
    private ArrayList<cartValue> orderList = new ArrayList<cartValue>();;
    ArrayList<String> userList = new ArrayList<String> ();


    FloatingActionButton fab;
    private DatabaseReference mdatabaseRef;
    private ListView listView;
    orderadapter adapter;
    Spinner dropdown;

    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
//    private String[] pageTitle = {"Received", "Cooking", "Delivered"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_home);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("order");
        listView = (ListView) findViewById(R.id.list);
        adapter = new orderadapter(this);
        listView.setAdapter(adapter);
        dropdown = findViewById(R.id.spinner);
        getAllOrder();


        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), admFoodList.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    public void getAllOrder(){
        final List<Food> universityList = new ArrayList<>();
        mdatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                orderList.clear();
                userList.clear();
                userList = new ArrayList<String>();


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    cartValue cart = postSnapshot.getValue(cartValue.class);
                    adapter.add(cart);

                }





            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "YOUR SELECTION IS : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }


}





//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_adm_home);
//
//        viewPager = (ViewPager)findViewById(R.id.view_pager);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
//
//        setSupportActionBar(toolbar);
//
//        //create default navigation drawer toggle
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        //setting Tab layout (number of Tabs = number of ViewPager pages)
//        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        for (int i = 0; i < 3; i++) {
//            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
//        }
//
//        //set gravity for tab bar
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        //handling navigation view item event
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        assert navigationView != null;
//        navigationView.setNavigationItemSelectedListener(this);
//
//        //set viewpager adapter
//        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(pagerAdapter);
//
//        //change Tab selection when swipe ViewPager
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        //change ViewPager page when tab selected
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.profile) {
//            viewPager.setCurrentItem(0);
//        } else if (id == R.id.food) {
//            Intent intent = new Intent(getApplicationContext(), admFoodList.class);
//            startActivityForResult(intent, REQUEST_FoodList);
//            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//        } else if (id == R.id.about) {
//            viewPager.setCurrentItem(2);
//        } else if (id == R.id.logoff) {
//
//        }
//
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        assert drawer != null;
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//}