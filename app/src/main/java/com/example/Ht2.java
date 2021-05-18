package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.tourme.R;

public class Ht2 extends AppCompatActivity {
    Button call,web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ht2);
        call=findViewById(R.id.detail_call_btn);
        web=findViewById(R.id.detail_directions_btn);
    }
}