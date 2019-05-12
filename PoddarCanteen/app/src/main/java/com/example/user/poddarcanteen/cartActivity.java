package com.example.user.poddarcanteen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class cartActivity extends AppCompatActivity {

    private ListView listView;
    CartItemAdapter adapter;
    cartValue currentCart;

    private ArrayList<cartfood> foodList = new ArrayList<cartfood>();;

    DatabaseReference mdatabaseRef;

    Button bClear;
    Button bShop;
    TextView tvTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("cart");
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        bShop = (Button) findViewById(R.id.bShop);
        listView = (ListView) findViewById(R.id.lvCartItems);
        adapter = new CartItemAdapter(this);
        listView.setAdapter(adapter);

        bShop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                performCheckout();
            }
        });

        getCartDetailsForUser();
    }


    public void getCartDetailsForUser(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userId = currentFirebaseUser.getUid();
        mdatabaseRef.orderByChild("userID").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    currentCart = postSnapshot.getValue(cartValue.class);
                }
            if (currentCart != null){
                foodList = currentCart.getFoodList();
                int totalPrice = 0;
                for (int y=0;y<foodList.size();y++) {
                    cartfood cartFood = foodList.get(y);
                    int total = Integer.parseInt(cartFood.gettotal());
                    totalPrice = total + totalPrice;
                    adapter.add(cartFood);
                }
                tvTotalPrice.setText("Total Price"+Integer.toString(totalPrice));

            } else  {
                bShop.setEnabled(false);
            }

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });
    }

public void performCheckout(){
    Intent intent = new Intent(getApplicationContext(), payment.class);
    startActivityForResult(intent, 1);
    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
}



}