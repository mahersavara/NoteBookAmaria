package com.khuatlinh.notebookamaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                //?TODO:why FireBase user -> null -> Login Activity ?
                if(currentUser== null){
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                } else{
                    startActivity(new Intent(SplashActivity.this, CreateAccountActivity.class));
                }
                // Intent bắn dữ liệu -> class đằng sau (activity )
                finish();
            }

        }, 1000);

    }
}