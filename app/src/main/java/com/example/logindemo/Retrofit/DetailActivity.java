package com.example.logindemo.Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.logindemo.R;

import org.w3c.dom.Text;

import static com.example.logindemo.SecondActivity.EXTRA_AGE;
import static com.example.logindemo.SecondActivity.EXTRA_COUNTRY;
import static com.example.logindemo.SecondActivity.EXTRA_DESCRIPTION;
import static com.example.logindemo.SecondActivity.EXTRA_GENDER;
import static com.example.logindemo.SecondActivity.EXTRA_NAME;

public class DetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        String Name = intent.getStringExtra(EXTRA_NAME);
        String Age = intent.getStringExtra(EXTRA_AGE);
        String Country = intent.getStringExtra(EXTRA_COUNTRY);
        String Gender = intent.getStringExtra(EXTRA_GENDER);
        String Description = intent.getStringExtra(EXTRA_DESCRIPTION);

        TextView name = findViewById(R.id.userName);
        TextView age = findViewById(R.id.userAge);
        TextView country = findViewById(R.id.userCountry);
        TextView gender = findViewById(R.id.userGender);
        TextView description = findViewById(R.id.userDescription);

        name.setText("Name : " + Name);
        age.setText("Age : " + Age);
        country.setText("Country : " + Country);
        gender.setText("Gender : " + Gender);
        description.setText(Description);
    }
}