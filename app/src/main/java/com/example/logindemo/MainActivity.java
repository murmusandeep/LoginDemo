package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;

    private Button mLoginButton;

    private TextView mForgotPassword;
    private TextView mInfo;
    private TextView mUserRegistration;

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
        mUserRegistration = (TextView)findViewById(R.id.registerText);
        mForgotPassword = (TextView)findViewById(R.id.forgotPassword);

        mInfo.setText("No. of Attempts remaining:" + String.valueOf(mCounter));

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
                validate(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });

        mUserRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });
    }

    private void validate(String userEmail, String userPassword) {

        progressDialog.setMessage("Ak");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailVerification();
                    // Toast.makeText(MainActivity.this, "Login Successful" , Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(MainActivity.this, SecondActivity.class));
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
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, SecondActivity.class));

        /* if(emailFlag) {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }
        else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        } */
    }
}