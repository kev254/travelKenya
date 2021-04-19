package com.example.tourme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    TextView LoginLink;
    Button SignUp;
    EditText Email,Password,username,userphone,ConfirmPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog= new ProgressDialog(this);
        setContentView(R.layout.activity_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        Password=findViewById(R.id.txtPassword);
        SignUp=findViewById(R.id.btnSignup);
        Email=findViewById(R.id.txtEmail);
        ConfirmPassword=findViewById(R.id.txtConfirmPassword);
        username=findViewById(R.id.txtUsername);
        userphone=findViewById(R.id.txtPhone);
        LoginLink=findViewById(R.id.txtLoginLink);

        LoginLink.setOnClickListener(this);
        SignUp.setOnClickListener(this);
    }

    public  void registerUser(){
        final String password=Password.getText().toString().trim();
        final String email=Email.getText().toString().trim();
        final  String conf=ConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(!(password.equals(conf))){
            Toast.makeText(this,"Password does not match",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show();
            return;
        }


        if (!isValidPhone(userphone.getText().toString().trim())){
            Toast.makeText(this,"The phone number is invalid",Toast.LENGTH_LONG).show();

        }
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Registration successful",Toast.LENGTH_LONG).show();
                            final FirebaseDatabase userData=FirebaseDatabase.getInstance();
                            final DatabaseReference userRef=userData.getReference("AllUsers").push();
                            userRef.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             userRef.child("useremail").setValue(email);
                             userRef.child("userpassword").setValue(password);
                             userRef.child("name").setValue(username.getText().toString());
                             userRef.child("phone").setValue(userphone.getText().toString());
                             userRef.child("id").setValue(dataSnapshot.getKey());

                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {


                             }
                         });
                        startActivity(new Intent(SignupActivity.this,SigninActivity.class));

                        }
                        else{
                            Log.e("error",task.getException().getMessage());
                            Toast.makeText(SignupActivity.this,"Registration unsuccessful",Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("erro",e.getMessage().toString())  ;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==SignUp){
            registerUser();
        }
        if(v==LoginLink){
            finish();
            startActivity(new Intent(this, SigninActivity.class));
        }

    }

boolean isValidPhone(String s){
 return s.length()==10 && TextUtils.isDigitsOnly(s);

}

}