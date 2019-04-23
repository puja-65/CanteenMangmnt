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


public class LogIn extends AppCompatActivity {
    private static final String TAG = "LogIn";
    private static final int REQUEST_SIGNUP = 0;
    EditText _emailText, _passwordText;
    Button _loginButton;
    TextView _signupLink;
    ProgressDialog _progressDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        _emailText = findViewById(R.id.email_Input);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
//                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LogIn.this,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

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


//public class LogIn extends AppCompatActivity {
//    TextView sign;
//    Button login, press;
//    EditText email, password, admin;
//    SQLiteOpenHelper openHelper;
//    SQLiteDatabase db;
//    Cursor cursor;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//    }
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_log_in);
//        openHelper = new UserDbHelper(this);
//        db = openHelper.getReadableDatabase();
//        sign = findViewById(R.id.signup);
//        login = findViewById(R.id.login);
//        email = findViewById(R.id.email);
//        password = findViewById(R.id.password);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String emailb = email.getText().toString();
//                String passw = password.getText().toString();
//                cursor = db.rawQuery("select * from " + UserDbHelper.TABLE_NAME + " Where " + UserDbHelper.COL_2 + "=? AND " + UserDbHelper.COL_3 + "=?", new String[]{emailb, passw});
//                if (cursor != null) {
//                    if (cursor.getCount() > 0) {
//                        cursor.moveToNext();
//                        String emailvalue = emailb;
//                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
//                        Intent i = new Intent(LogIn.this, Home.class);
//                        i.putExtra("EMAIL", emailvalue);
//                        startActivity(i);
//                        finish();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Not Existed", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//        });
//        sign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(LogIn.this, SignUp.class);
//                startActivity(i);
//            }
//        });
//        admin = findViewById(R.id.edit_admin);
//        press = findViewById(R.id.press);
//        press.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (admin.getText().toString().equals("abc12")) {
//                    Intent i = new Intent(getApplicationContext(), AdmHome.class);
//                    startActivity(i);
//                }
//            }
//        });
//    }
}
