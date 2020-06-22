package com.mrtoi.chatx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class PrivacyPolicyActivity extends AppCompatActivity
{

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        TextView textView = (TextView) findViewById(R.id.pptextView);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        imageView = (ImageView) findViewById(R.id.pp_iconn);
    }
}
