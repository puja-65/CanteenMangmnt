package com.example.user.poddarcanteen;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

public class Add_Item extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    ProgressDialog dialog;

    private SliderLayout mDemoSlider;
    HashMap<String,Integer> file_maps = new HashMap<String, Integer>();


    FloatingActionButton fab;
    private EditText name_food, price_food;

    StorageReference mStorageRef;
    DatabaseReference mdatabaseRef;
    private StorageTask mUploadTask;

    public ArrayList<Uri> urlList = new ArrayList<Uri>();
    public ArrayList<String> serverurlList = new ArrayList<String>();

    private StorageTask uploadTask;


    String[] counter = {"Drink","Dessert", "Snacks", "Main Item - NonVeg", "Main Item - Veg","Chineese"};
    AutoCompleteTextView type_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);
        dialog=new ProgressDialog(this);
        setUpSlider();

        mStorageRef = FirebaseStorage.getInstance().getReference("/");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("foodItem");


        name_food = findViewById(R.id.food_name);
        price_food = findViewById(R.id.food_price);


        type_food =(AutoCompleteTextView) findViewById(R.id.food_type);
        ArrayAdapter a=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,counter);
        type_food.setThreshold(1);
        type_food.setAdapter(a);




    }

    public void setUpSlider(){
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomIn);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }


    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    public void btnBrowser_Click(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imgUri = data.getData();
//            imageDisplay.setImageURI(imgUri);
            try{
                Bitmap bm=MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description("")
                        .image(String.valueOf(imgUri))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra",bm.toString());

                mDemoSlider.addSlider(textSliderView);
                urlList.add(imgUri);



//                imageDisplay.setImageBitmap(bm);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void btnUpload_Click(View v){
        dialog.setTitle("Uploading Image");
        dialog.show();
        uploaFiles();
    }

    public void uploaFiles() {
        if (urlList.size() <= 0 ) {
            saveDataInDb();
        } else {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_LONG).show();
            } else {
                Uri imgUrl = urlList.get(0);

                if (imgUrl != null) {
                    final StorageReference ref = mStorageRef.child("foods/" + System.currentTimeMillis() + "." + getExtension(imgUrl));
                    mUploadTask = ref.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //uploadProgress.setProgress(0);
                                }
                            }, 500);
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    serverurlList.add(uri.toString());
                                    urlList.remove(0);
                                    if (urlList.size() > 0) {
                                        uploaFiles();
                                    } else {
                                        saveDataInDb();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No Image Selected, Please Select Image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void saveDataInDb(){
        //Display success toast msg
        String _foodName = name_food.getText().toString();
        String _foodPrice = price_food.getText().toString();
        String _foodType = type_food.getText().toString();

        if (!TextUtils.isEmpty(_foodName) && !TextUtils.isEmpty(_foodPrice) && !TextUtils.isEmpty(_foodType)) {
            String uploadId=mdatabaseRef.push().getKey();
            Food food = new Food(uploadId,_foodName, _foodPrice, _foodType, serverurlList);

            mdatabaseRef.child(uploadId).setValue(food);
            name_food.setText("");
            price_food.setText("");
            type_food.setText("");
            serverurlList.clear();
            urlList.clear();
            mDemoSlider.removeAllSliders();
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"Save Successfully",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Please Insert the Food Name,Price and Type", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
//        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}