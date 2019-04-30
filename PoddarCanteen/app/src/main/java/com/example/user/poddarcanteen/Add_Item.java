package com.example.user.poddarcanteen;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class Add_Item extends AppCompatActivity {
    ImageView imageDisplay;
    private EditText name_food, price_food;

    StorageReference mStorageRef;
    DatabaseReference mdatabaseRef;
    public Uri imgUri;
    private StorageTask uploadTask;


    String[] counter = {" Dessert", " Snacks", " Main Item - NonVeg", " Main Item - Veg"};
    AutoCompleteTextView type_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);
        imageDisplay = findViewById(R.id.image);

        mStorageRef = FirebaseStorage.getInstance().getReference("image/");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("image");
  /*      btncaptureimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask!= null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),"Upload In Progress",Toast.LENGTH_LONG).show();
                }else{
                    FileUploader();
                }
            }
        });
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("food");
*/

        name_food = findViewById(R.id.food_name);
        price_food = findViewById(R.id.food_price);


        type_food =(AutoCompleteTextView) findViewById(R.id.food_type);
        ArrayAdapter a=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,counter);
        type_food.setThreshold(1);
        type_food.setAdapter(a);


/*
        insertData = findViewById(R.id.btn_insert);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
            }
        });*/
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

 /*   private void FileUploader() {
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+","+getExtension(imguri));

        uploadTask=Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getApplicationContext(), "Image Uploaded Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
*/
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
            imgUri = data.getData();
//            imageDisplay.setImageURI(imgUri);
            try{
                Bitmap bm=MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imageDisplay.setImageBitmap(bm);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void btnUpload_Click(View v){

        if(imgUri!=null){
            final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setTitle("Uploading Image");
            dialog.show();

            //GEt the Storage reference
            StorageReference ref=mStorageRef.child("image/"+System.currentTimeMillis() + "," + getExtension(imgUri));

            //Add file to reference
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            // Dismiss dialog when success
                            dialog.dismiss();
                            //Display success toast msg
                            String _foodName = name_food.getText().toString();
                            String _foodPrice = price_food.getText().toString();
                            String _foodType = type_food.getText().toString();

                            if (!TextUtils.isEmpty(_foodName) && !TextUtils.isEmpty(_foodPrice) && !TextUtils.isEmpty(_foodType)) {
                                String uploadId=mdatabaseRef.push().getKey();
                                Food food = new Food(_foodName, _foodPrice, _foodType, taskSnapshot.getStorage().getDownloadUrl().toString());

                                mdatabaseRef.child(uploadId).setValue(food);
                                name_food.setText("");
                                price_food.setText("");
                                type_food.setText("");

                                Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Insert the Food Name,Price and Type", Toast.LENGTH_LONG).show();
                            }



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Dismiss dialog when error
                            dialog.dismiss();
                            //Display error toast msg
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress
                            double progress=(100*taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            dialog.setMessage("Uploading...");
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_LONG).show();
        }
    }

}