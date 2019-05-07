package com.example.user.poddarcanteen;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class admFoodList  extends AppCompatActivity {
    private static final int REQUEST_AddFood = 0;

    FloatingActionButton fab;
    private DatabaseReference mdatabaseRef;
    private List<Food> foodList;
    private ListView listView;
    FoodCardsAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_list);

        ListView lvCards = (ListView) findViewById(R.id.list);
        adapter = new FoodCardsAdapter(this);
        lvCards.setAdapter(adapter);

        mdatabaseRef = FirebaseDatabase.getInstance().getReference("foodItem");

        fab = (FloatingActionButton)findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add_Item.class);
                startActivityForResult(intent, REQUEST_AddFood);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        getAllOrderFromFirebase();


    }


    private void getAllOrderFromFirebase() {
        foodList = new ArrayList<>();
         final List<Food> universityList = new ArrayList<>();
        mdatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                universityList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Food food = postSnapshot.getValue(Food.class);
                    universityList.add(food);
                    adapter.add(food);
                }
                // here you can access to name property like university.name
                System.out.println("universityList: " + universityList);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}


