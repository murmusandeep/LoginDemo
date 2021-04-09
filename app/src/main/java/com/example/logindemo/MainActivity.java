package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;

    private Button mLoginButton;

    private TextView mInfo;

    private int mCounter = 5;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText)findViewById(R.id.input_email);
        mPassword = (EditText)findViewById(R.id.input_password);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mInfo = (TextView)findViewById(R.id.tvInfo);
        TextView mUserRegistration = (TextView) findViewById(R.id.registerText);

        String noOfAttempt = "No. of Attempts remaining:" + String.valueOf(mCounter);
        mInfo.setText(noOfAttempt);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(validateCredentials(email, password)) {
                    logIn(email, password);
                }

            }
        });


        mUserRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void logIn(String userEmail, String userPassword) {

        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailVerification();
                }
                else {

                    Toast.makeText(MainActivity.this, "Login Failed" , Toast.LENGTH_SHORT).show();
                    mCounter--;
                    progressDialog.dismiss();
                    mInfo.setText("No. of Attempts Remaining: "+ mCounter);
                    if(mCounter == 0) {
                        mLoginButton.setEnabled(false);
                    }
                }
            }
        });
    }


    private void checkEmailVerification() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, SecondActivity.class));
        finish();
    }

    private boolean isValidEmail(String email) {
        //String pattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if(email.isEmpty()) {
            return false;
        }
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcher = Pattern.compile(pattern).matcher(email);
        return matcher.find();
    }

    private boolean isValidPassword(String password) {
        if (password.isEmpty()) {
            return false;
        }
        if(password.length() < 2 && password.length() > 32) {
            return false;
        }
        return true;
    }

    private boolean validateCredentials(String email, String password) {
        if(!isValidEmail(email)) {
            Toast.makeText(MainActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isValidPassword(password)) {
            Toast.makeText(MainActivity.this, "Password cannot be empty and length should be between 2 and 32", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}