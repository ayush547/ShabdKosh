package com.example.shabdkosh;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    TextView welcome;
    MediaPlayer welcomeSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        welcome = findViewById(R.id.welcomeText);
        welcomeSound = MediaPlayer.create(this,R.raw.welcome);
        ObjectAnimator animation = ObjectAnimator.ofFloat(welcome, "translationY", 200f);
        animation.setDuration(1000);
        welcomeSound.start();
        animation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent out = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(out);
                welcomeSound.release();
                finish();
            }
        }, 1100);
    }
}
