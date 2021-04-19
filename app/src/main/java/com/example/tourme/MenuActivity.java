package com.example.tourme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    TextView usermail;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    PostAdapter adapter;
    ImageView hotels,parks,cars;
   static List<PostModel> list;
   FloatingActionButton ExtendButton,VisitButton,ContactButton,EmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("");
        }


        recyclerView=findViewById(R.id.recylerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));

//initialize the list;

list=new ArrayList<PostModel>();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference("Images");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //get model from snapshot
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                   // assert dataSnapshot1!=null;
                    Log.e("folder", dataSnapshot1.getKey());
                    String id = null, title = null, url = null, desc = null,email=null;


                        // username=dataSnapshot1.child("name").getValue().toString();
                try {
                    id = dataSnapshot1.child("image_id").getValue().toString();
                    url = dataSnapshot1.child("image_url").getValue().toString();
                    desc = dataSnapshot1.child("image_desc").getValue().toString();
                    title = dataSnapshot1.child("image_title").getValue().toString();
                    email=dataSnapshot1.child("image_email").getValue().toString();
                }catch (NullPointerException e){
                 try {
                     id = dataSnapshot1.child("image_id").getValue().toString();
                     url = dataSnapshot1.child("image_url").getValue().toString();
                     desc = dataSnapshot1.child("image_desc").getValue().toString();
                     title = dataSnapshot1.child("image_title").getValue().toString();
                     email=dataSnapshot1.child("image_email").getValue().toString();
                 }catch (NullPointerException x){
                     x.printStackTrace();
                 }

                }

                //Log.e("data",name+" pass: "+password+" email: "+email+" phone: "+phone);
                    PostModel model = new PostModel(id,url,title,desc,email);
                   Log.e("title",title.toString());

                    list.add(model);
                     Log.e("contenyts",String.valueOf(list.size()));

                }


              //  String name=dataSnapshot.child("Name").getValue().toString();

               // Log.e("Fetched Name",name);

                //String phone=dataSnapshot.child("phone").getValue().toString();
               // Log.e("Fetched Name",name);
                Log.e("Contents",String.valueOf(list.size()));
                //        //instantiate adapter
                adapter=new PostAdapter(MenuActivity.this, list, new RecyclerViewOnclickListener() {
                    @Override
                    public void onClick(View view, int position) {
                TextView detTv,urlTv,titleTv;
                String details, imageUrl,imageTitle;
                detTv=view.findViewById(R.id.description);
                urlTv=view.findViewById(R.id.imageUrl);
                titleTv=view.findViewById(R.id.title);

                details=detTv.getText().toString();
                imageUrl=urlTv.getText().toString();
                imageTitle=titleTv.getText().toString();

                showDetailedView(details,imageUrl,imageTitle);

                    }
                });

                //set the recyclerview adapter
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
       if(firebaseAuth.getCurrentUser()==null){
           finish();
           startActivity(new Intent(this, WelcomeScreen.class));
       }
        usermail=findViewById(R.id.UserEmail);
        usermail.setText("Dashboard");
        progressDialog= new ProgressDialog(this);
    }

    private void showDetailedView(String details, String imageUrl, String imageTitle) {
        setContentView(R.layout.details_activity);
        AppCompatTextView Details,Title;
        ImageView imageView;

        ExtendButton=findViewById(R.id.extend);
        VisitButton=findViewById(R.id.fab3);
        ContactButton=findViewById(R.id.fab1);
        EmailButton=findViewById(R.id.fab2);

       final FloatingActionButton[] fabs=new FloatingActionButton[]{VisitButton,ContactButton,EmailButton};

        ExtendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(FloatingActionButton f:fabs){
                   if (f.getVisibility()==View.GONE){
                       f.setVisibility(View.VISIBLE);
                   }else {
                       f.setVisibility(View.GONE);
                   }
                }
            }
        });

for (FloatingActionButton floatingActionButton:fabs){
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.fab1){
                Dexter.withActivity(MenuActivity.this)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent=new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:0792097883"));
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
            else
            if (v.getId()==R.id.fab2) {
                Intent intent=new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","okombakevin@yahoo.com",null));
                intent.putExtra(Intent.EXTRA_TEXT,"Helllo? i am  so and so and  i am interested in your services as travel agency as i saw in travel Kenya");
                startActivity(Intent.createChooser(intent,"Send Email"));

            }else
                if (v.getId()==R.id.fab3){
                    startActivity(new Intent(MenuActivity.this,agentActivit.class));

                }
        }
    });

}

        Details=findViewById(R.id.description);
        Title=findViewById(R.id.name);
        imageView=findViewById(R.id.image);
       /*visit=findViewById(R.id.visit);

        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,agentActivit.class));
            }
        });*/
        Details.setText(details);
        Title.setText(imageTitle);
        Glide.with(MenuActivity.this).load(imageUrl).into(imageView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.admin){
            startActivity(new Intent(MenuActivity.this,AdminActivity.class));
        }
        if (item.getItemId()==R.id.btn_logout){
            final AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setCancelable(true);
            builder.setMessage("Do you really want to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MenuActivity.this,WelcomeScreen.class));

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }
        if (item.getItemId()==R.id.hire_car){
            startActivity(new Intent(MenuActivity.this,TaxiActivity.class));
        }

        if (item.getItemId()==R.id.locate_sites){
            startActivity(new Intent(MenuActivity.this,SitesActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }


}