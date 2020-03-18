package com.app.urbfs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.urbfs.R;
import com.app.urbfs.config.SessionManager;

public class Splashscreen extends AppCompatActivity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        session = new SessionManager(getApplicationContext());
        StartAnimations();

    }

    private void StartAnimations() {

      /*  Animation anim = AnimationUtils.loadAnimation(this, R.anim.zoom);
        anim.reset();
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.zoom);
        anim2.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.isLoggedIn() == true) {
                    Intent i = new Intent(getApplicationContext(), Frontview.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                } else {
                    Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        }, 1500);

    }
}