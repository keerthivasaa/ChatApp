package com.mrtoi.chatx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity
{
    private TextView textView;
    private ImageView imageView;
    private static int SplashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView textView = (TextView) findViewById(R.id.sstextView);
        imageView = (ImageView) findViewById(R.id.ss_iconn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent splashIntent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(splashIntent);
                finish();
            }
        },SplashTime);

    }
}
