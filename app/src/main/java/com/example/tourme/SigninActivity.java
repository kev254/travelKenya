package com.example.tourme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signUpLink;
    EditText Email,Password;
    Button Login;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        firebaseAuth=FirebaseAuth.getInstance();

        signUpLink=findViewById(R.id.txtSignupLink);
        Email=findViewById(R.id.txtEmail);
        Password=findViewById(R.id.txtPassword);
        Login=findViewById(R.id.btnLogin);

        signUpLink.setOnClickListener(this);
        Login.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);

    }
    private   void UserLogin(){
        String email=Email.getText().toString().trim();
        final String password=Password.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MenuActivity.class));

                        }
                        else {
                            //
                        }

                    }

                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(SigninActivity.this,MenuActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        if(v==signUpLink){
            finish();
            startActivity(new Intent(this,SignupActivity.class));
        }
        if(v==Login){
            UserLogin();

        }

    }
}


