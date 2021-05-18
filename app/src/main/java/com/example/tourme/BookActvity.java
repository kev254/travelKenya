package com.example.tourme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookActvity extends AppCompatActivity implements View.OnClickListener {
    Button Book;
    Spinner spinner;
    EditText Email,Password,username,userphone,ConfirmPassword,country,age,date,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_actvity);
        Book=findViewById(R.id.btnSignup);
        spinner=findViewById(R.id.select);
        username=findViewById(R.id.txtName);
        Email=findViewById(R.id.txtEmail);
        userphone=findViewById(R.id.txtPhone);
        country=findViewById(R.id.txtCountry);
        age=findViewById(R.id.txtAge);
        date=findViewById(R.id.txtDate);
        number=findViewById(R.id.txtNoOfpersons);
        Book.setOnClickListener(this);
        ArrayAdapter<String> myadapter= new ArrayAdapter<String>(BookActvity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.travel_agency));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myadapter);
    }
    public  void AddBooking() {
        final String email = Email.getText().toString().trim();
        final String Phone = userphone.getText().toString();
        final String Country=country.getText().toString().trim();
        final String Age= age.getText().toString().trim();
        final String Number=number.getText().toString().trim();
        final String Date= date.getText().toString().trim();

        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(Phone)|| TextUtils.isEmpty(Country)|| TextUtils.isEmpty(Age)|| TextUtils.isEmpty(Number)|| TextUtils.isEmpty(Date)) {
            Toast.makeText(this, "Field cannot be Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            final FirebaseDatabase userData=FirebaseDatabase.getInstance();
            final DatabaseReference userRef=userData.getReference("Bookings").push();
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    userRef.child("name").setValue(username.getText().toString());
                    userRef.child("useremail").setValue(email);
                    userRef.child("phone").setValue(Phone);
                    userRef.child("Country").setValue(Country);
                    userRef.child("Age").setValue(Age);
                    userRef.child("Date").setValue(Date);
                    userRef.child("Number Of Persons").setValue(Number);
                    userRef.child("id").setValue(dataSnapshot.getKey());
                    SweetAlertDialog sd= new SweetAlertDialog(BookActvity.this, SweetAlertDialog.SUCCESS_TYPE);
                    sd.setTitle("SUCESS .");
                    sd.setContentText("Booking Details sent Successfully ");
                    sd.show();
                    Intent w= new Intent(BookActvity.this, MenuActivity.class);
                    startActivity(w);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if(view==Book){
            AddBooking();
        }
    }
}