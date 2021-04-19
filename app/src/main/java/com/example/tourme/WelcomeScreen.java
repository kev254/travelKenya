package com.example.tourme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {
    Button LogMe, SignMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogMe=findViewById(R.id.btn_main_login);
        SignMe=findViewById(R.id.btn_main_signup);

        LogMe.setOnClickListener(this);
        SignMe.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==LogMe){
            this.finish();
            startActivity(new Intent(this,SigninActivity.class));
        }
        if(v==SignMe){
            this.finish();
            startActivity(new Intent(this,SignupActivity.class));
        }

    }
}
