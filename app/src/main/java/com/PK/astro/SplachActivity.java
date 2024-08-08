package com.PK.astro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        ImageView logo = findViewById(R.id.logo);
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        logo.startAnimation(splashAnimation);

        int splashScreenTimeout = 3000; // 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplachActivity.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        }, splashScreenTimeout);
    }
}
