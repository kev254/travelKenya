package com.example.tourme;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Ht4 extends AppCompatActivity implements View.OnClickListener {
    Button Call,Book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ht4);
        Call=findViewById(R.id.detail_call_btn);
        Book=findViewById(R.id.detail_directions_btn);
        Book.setOnClickListener(this);
        Call.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==Book){
            Intent m= new Intent(Ht4.this, BookActvity.class);
            startActivity(m);
        }
        if(view==Call){
            Dexter.withActivity(Ht4.this)
                    .withPermission(Manifest.permission.CALL_PHONE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Intent intent=new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:0711651962"));
                            startActivity(intent);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();

                        }
                    }).check();

        }
    }
}