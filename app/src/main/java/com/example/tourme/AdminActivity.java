package com.example.tourme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;

public class AdminActivity extends AppCompatActivity {

    private AppCompatEditText TitleText,DescText,TextEmail;
    private ImageButton ChooseButton;
    private Button PostButton;
    private Uri filePath=null;
    private Bitmap bitmap;
    private static int GALLERY_REQUEST=1;
    private String downloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        TitleText=findViewById(R.id.title);
        DescText=findViewById(R.id.description);
        TextEmail=findViewById(R.id.agencyMail);

        ChooseButton=findViewById(R.id.chooseImage);

        PostButton=findViewById(R.id.post);

        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Dexter.withActivity(AdminActivity.this)
                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Intent galleryPicker=new Intent(Intent.ACTION_GET_CONTENT);
                                    galleryPicker.setType("image/*");
                                    startActivityForResult(Intent.createChooser(galleryPicker,"Select an Image"),GALLERY_REQUEST);

                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response){
                                //TODO: implement info dialog
                                    finish();
                                    Toast.makeText(getApplicationContext(),"Access Denied",Toast.LENGTH_LONG).show();

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                             token.continuePermissionRequest();
                                }
                            }).check();
                }else {

                    Intent galleryPicker=new Intent(Intent.ACTION_GET_CONTENT);
                    galleryPicker.setType("image/*");
                    startActivityForResult(Intent.createChooser(galleryPicker,"Select an Image"),GALLERY_REQUEST);


                }


            }
        });

        PostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title,desc,email;
                title=TitleText.getText().toString().trim();
                desc=DescText.getText().toString().trim();
               // email=TextEmail.getText().toString();

                if(TextUtils.isEmpty(title)){
                    TitleText.setError("Title cannot be empty");
                    //PostButton.setEnabled(false);
                    return;
                }

                /*if(TextUtils.isEmpty(email)){
                    TextEmail.setError("Email cannot be empty");
                    //PostButton.setEnabled(false);
                    return;
                }*/

                if(TextUtils.isEmpty(desc)){
                    TitleText.setError("Description cannot be empty");
                   // PostButton.setEnabled(false);
                    return;
                }

                if (filePath==null){
                    Toast.makeText(getApplicationContext(),"Please select an image first",Toast.LENGTH_LONG).show();
                    PostButton.setEnabled(false);
                    return;
                }
                final ProgressDialog dialog=new ProgressDialog(AdminActivity.this,R.style.AppTheme);
                dialog.setTitle("Posting Just a moment");
                dialog.setMessage("Do not Cancel");
                dialog.show();

                FirebaseStorage storage=FirebaseStorage.getInstance();
                final StorageReference reference=storage.getReference().child("Images").child(System.currentTimeMillis()+"."+getextension(filePath));

                UploadTask uploadTask=reference.putFile(filePath);
                Task<Uri> task=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                      if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_LONG).show();
                      }
                        return reference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                     //download uri for image
                     Uri uri=task.getResult();

                     //Download url of the image in firestore
                        assert uri!=null;
                     downloadUrl=uri.toString();

                    }
                }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                 if (downloadUrl!=null){
                     final DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Images").push();
                     reference1.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reference1.child("image_id").setValue(dataSnapshot.getKey()) ;
                            reference1.child("image_url").setValue(downloadUrl);
                            reference1.child("image_title").setValue(title);
                            reference1.child("image_desc").setValue(desc);
                           // reference1.child("image_email").setValue(email);
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {
return;
                         }
                     });

                 Toast.makeText(getApplicationContext(),"Posted successfully",Toast.LENGTH_LONG)  .show();
                 dialog.dismiss();
                 finish();

                 }

                    }
                });



            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
         filePath=data.getData();

         try {
             bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
             ChooseButton.setImageBitmap(bitmap);
             ChooseButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
         }catch (Exception e){
             Toast.makeText(getApplicationContext(),"could not load image please try again",Toast.LENGTH_LONG).show();
             return;
         }
     }

    }

    String getextension(Uri uri){
        ContentResolver resolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));

    }
}
