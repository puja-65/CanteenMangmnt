package com.example.user.poddarcanteen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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


public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText _nameText, _emailText, _passwordText;
    Button _signupButton;
    TextView _loginLink;
//
//    @InjectView(R.id.input_name) EditText _nameText;
//    @InjectView(R.id.input_email) EditText _emailText;
//    @InjectView(R.id.input_password) EditText _passwordText;
//    @InjectView(R.id.btn_signup) Button _signupButton;
//    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");


        setContentView(R.layout.activity_sign_up);
        _nameText = findViewById(R.id.input_name);
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            createUserInTable(user.getUid());
//                            updateUI(user);
                            submitUserData();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            onSignupFailed();
                        }
                        progressDialog.dismiss();

                    }
                });

    }


    private void sendEmailVerification(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
//                        sendUserData();
                        Toast.makeText(SignUp.this, "Successfully Registered, Verification mail sent!",
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        onSignupSuccess();
//                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(SignUp.this, "Verification mail has'nt been sent!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void submitUserData() {
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        user user = dataSnapshot.getValue(user.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(SignUp.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            createNewUserInDB(user);
                        }

                        // Finish this Activity, back to the stream
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void createNewUserInDB(user user) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Map<String, Object> postValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + user.userId + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

//    public void createUserInTable(String userID) {
//        String name = _nameText.getText().toString();
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
//
//        user userProfile = new user(userID, name, email,"0");
//        mDatabase.child("users").child(userID).setValue(userProfile);
//
//
//
//
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        DatabaseReference myRef = database.getReference("message");
////
////        myRef.setValue("Hello, World!");
////
////        myRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                // This method is called once with the initial value and again
////                // whenever data at this location is updated.
////                String value = dataSnapshot.getValue(String.class);
////                Log.d(TAG, "Value is: " + value);
////            }
////
////            @Override
////            public void onCancelled(DatabaseError error) {
////                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException());
////            }
////        });
//        Log.d(TAG, "createUserWithEmail:success");
//
//    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}

//
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class SignUp extends AppCompatActivity {
//    Button sign;
//    EditText nameb, emailb, phoneb, passwordb, rePasswordb, address;
//    SQLiteOpenHelper openHelper;
//    SQLiteDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
//
//        openHelper = new UserDbHelper(this);
//
//        sign = (Button) findViewById(R.id.sign);
//        nameb = findViewById(R.id.name);
//        emailb = findViewById(R.id.email);
//        phoneb = findViewById(R.id.phone);
//        passwordb = findViewById(R.id.password);
//        rePasswordb = findViewById(R.id.re_pass);
//        address = findViewById(R.id.address);
//        sign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db = openHelper.getWritableDatabase();
//                String name = nameb.getText().toString();
//                String email = emailb.getText().toString();
//                String pass = passwordb.getText().toString();
//                String phone = phoneb.getText().toString();
//                String adres = address.getText().toString();
//
//                if (pass.length() < 9 && pass.length() > 7 && phone.length()>8 && adres.length()>2) {
//                    if (pass.equals(rePasswordb.getText().toString())) {
//                        insertdata(name, email, pass, phone, adres);
//                        String namev = name;
//                        Toast.makeText(getApplicationContext(), "registration successfully", Toast.LENGTH_LONG).show();
//                        Intent i = new Intent(SignUp.this, LogIn.class);
//                        i.putExtra("NAME", namev);
//                        startActivity(i);
//                    } else {
//                        Toast.makeText(SignUp.this, "Passwords are not match", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "You have to enter eight elements in your password and fill the full details", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//
//    public void insertdata(String name, String email, String password, String phone, String address) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(UserDbHelper.COL_1, name);
//        contentValues.put(UserDbHelper.COL_2, email);
//        contentValues.put(UserDbHelper.COL_3, password);
//        contentValues.put(UserDbHelper.COL_4, phone);
//        contentValues.put(UserDbHelper.COL_5, address);
//        long id = db.insert(UserDbHelper.TABLE_NAME, null, contentValues);
//    }
//}

