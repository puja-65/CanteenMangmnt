package com.example.user.poddarcanteen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.internal.FirebaseAppHelper.getUid;

public class foodDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final int REQUEST_FoodList = 1;
    int count = 0;
    private TextView quantity,price,name,category,addToCart;

    private SliderLayout mDemoSlider;
    Food food;
    ProgressDialog _progressDialog;
    cartValue currentCart;

    DatabaseReference mdatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddetails);
        quantity = findViewById(R.id.sizeno);
        price = findViewById(R.id.price);
        name = findViewById(R.id.nameLable);
        category = findViewById(R.id.category);
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("cart");
        addToCart = findViewById(R.id.cartAdd);



        Intent i = getIntent();
        food = (Food) getIntent().getExtras().getSerializable("food");

        price.setText("₹ "+food.getFoodPrice());
        name.setText(food.getFoodName());
        category.setText(food.getFoodType());
        setUpSlider();
        getCartDetailsForUser();
        ImageView plusImg = (ImageView) findViewById(R.id.plus);
        plusImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ( count < 100) {
                    count = count + 1 ;
                    quantity.setText(Integer.toString(count));
                } else {
                    Toast.makeText(getApplicationContext(),"Minimum one item need to add",Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView minusImg = (ImageView) findViewById(R.id.minus);
        minusImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ( count > 0) {
                    count = count - 1 ;
                    quantity.setText(Integer.toString(count));
                } else {
                    Toast.makeText(getApplicationContext(),"Minimum one item need to add",Toast.LENGTH_LONG).show();
                }

                // your code here
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentCart == null) {
                    addValueToCart();
                } else  {
                    updateValueToCart();
                }
            }
        });
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
            if (currentCart != null) {
                 ArrayList<cartfood> foodList = currentCart.getFoodList();
                 for (int y=0;y<foodList.size();y++) {
                      cartfood cartFood = foodList.get(y);
                     if ( cartFood.getfoodID().toString().equals(food.getFoodId())) {
                      quantity.setText(""+cartFood.getquantity());

                }

    }
}


            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }


    public void setUpSlider(){
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));

        for(int l=0; l<=food.serverurlList.size() - 1; l++){

            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(food.serverurlList.get(l))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra","");

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomIn);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }


    public void addValueToCart() {

        addToCart.setEnabled(false);

        _progressDialog = new ProgressDialog(foodDetails.this,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Adding...");
        _progressDialog.show();

                new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                        final String userId = currentFirebaseUser.getUid();
                        String uploadId=mdatabaseRef.push().getKey();
                        int quantityValue = Integer.parseInt(quantity.getText().toString());
                        int priceValue = Integer.parseInt(food.getFoodPrice().toString());
                        int total = quantityValue * priceValue;
                        ArrayList<cartfood> foodList = new ArrayList<cartfood>();

                        cartfood cartFood = new cartfood(food.getFoodName(),food.getFoodId(),String.valueOf(quantityValue),String.valueOf(total));
                        foodList.add(cartFood);
                        cartValue cart = new cartValue(uploadId,userId,foodList);

                        mdatabaseRef.child(uploadId).setValue(cart);
                        _progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), cartActivity.class);
                        startActivityForResult(intent, 1);
                        finish();
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                }, 3000);



    }

    public void updateValueToCart() {

        _progressDialog = new ProgressDialog(foodDetails.this,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Updating...");
        _progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        int quantityValue = Integer.parseInt(quantity.getText().toString());
                        int priceValue = Integer.parseInt(food.getFoodPrice().toString());
                        int total = quantityValue * priceValue;

                        ArrayList<cartfood> foodList = currentCart.getFoodList();
                        for (int y=0;y<foodList.size();y++) {
                            cartfood cartFood = foodList.get(y);
                            if ( cartFood.getfoodID().toString().equals(food.getFoodId())) {
                                cartFood.setquantity(String.valueOf(quantityValue));
                                cartFood.settotal(String.valueOf(total));
                            } else {
                                cartFood = new cartfood(food.getFoodName(),food.getFoodId(),String.valueOf(quantityValue),String.valueOf(total));
                                foodList.add(cartFood);
                            }
                        }

                        mdatabaseRef.child(currentCart.getCartID()).setValue(currentCart);
                        _progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), cartActivity.class);
                        startActivityForResult(intent, 1);
                        finish();
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                    }

        }, 3000);


    }

        @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
