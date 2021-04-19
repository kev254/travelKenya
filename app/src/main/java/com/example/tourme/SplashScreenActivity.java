package com.example.tourme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.firebase.storage.internal.Sleeper;
import com.plattysoft.leonids.ParticleSystem;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new ParticleSystem(this, 100, R.drawable.confetti, 5000)

                .setSpeedRange(0.1f, 0.25f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 360)
               .oneShot(findViewById(R.id.emitter), 100);

        Sleep();

    }
    void Sleep(){

        class Sleeper extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                startActivity(new Intent(SplashScreenActivity.this, WelcomeScreen.class));

            }
        }

        new Sleeper().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new ParticleSystem(this, 100, R.drawable.confetti, 5000)

                .setSpeedRange(0.1f, 0.25f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 360)
                .oneShot(findViewById(R.id.emitter), 100);

        Sleep();
    }
}
