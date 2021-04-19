package com.example.tourme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class web extends AppCompatActivity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        url=getIntent().getStringExtra("transfered");
        WebView web=(WebView)findViewById(R.id.viewWeb);
        web.loadUrl(url);
    }
}
