package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.logindemo.Retrofit.DetailActivity;
import com.example.logindemo.Retrofit.UserData;
import com.example.logindemo.Retrofit.UserDataAdapter;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements UserDataAdapter.OnItemClickListener {

    public static final String EXTRA_NAME = "Name";
    public static final String EXTRA_AGE = "Age";
    public static final String EXTRA_COUNTRY = "Country";
    public static final String EXTRA_GENDER = "Gender";
    public static final String EXTRA_DESCRIPTION = "Description";

    private FirebaseAuth firebaseAuth;

    private Button mLogout;

    private RecyclerView mRecyclerView;
    private UserDataAdapter mUserDataAdapter;
    private ArrayList<UserData> mUserData;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle("Top 20 Richest Personal");

        firebaseAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUserData = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    private void parseJSON() {

        String url = "https://7790f3fd-ba5f-4f50-bca7-f2991e5514e5.mock.pstmn.io//v1/home";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject datas = jsonArray.getJSONObject(i);

                        String UserName = datas.getString("name");
                        String UserGender = datas.getString("gender");
                        String UserCountry = datas.getString("country");
                        String UserAge = datas.getString("age");
                        String UserDescription = datas.getString("description");

                        mUserData.add(new UserData(UserName, UserGender, UserCountry, UserAge, UserDescription));
                    }

                    mUserDataAdapter = new UserDataAdapter(SecondActivity.this,mUserData);
                    mRecyclerView.setAdapter(mUserDataAdapter);
                    mUserDataAdapter.setOnItemClickListener(SecondActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(jsonObjectRequest);

    }

    private void logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final int logoutMenu = R.id.logoutMenu;
        final int profileMenu = R.id.profileMenu;

        switch (item.getItemId()) {

            case logoutMenu: {
                logout();
                break;
            }
            case profileMenu: {
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent DetailIntent = new Intent(this, DetailActivity.class);
        UserData userData = mUserData.get(position);

        DetailIntent.putExtra(EXTRA_NAME,userData.getName());
        DetailIntent.putExtra(EXTRA_AGE,userData.getAge());
        DetailIntent.putExtra(EXTRA_COUNTRY,userData.getCountry());
        DetailIntent.putExtra(EXTRA_GENDER,userData.getGender());
        DetailIntent.putExtra(EXTRA_DESCRIPTION,userData.getDescription());

        startActivity(DetailIntent);
    }
}