package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EventsActivity extends AppCompatActivity implements Locate {
private static final int CHOOSE_IMAGE=1;
private Button choosebtn;
private Button uploadbtn;
private ImageView eImage;
private EditText description;
private Uri imguri;
private StorageReference storageReference;
private FirebaseStorage firebaseStorage;
private FirebaseDatabase firebaseDatabase;
private DatabaseReference databaseReference;
private ProgressBar eprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        choosebtn =  (Button) findViewById(R.id.eGetImage);
        uploadbtn = (Button) findViewById(R.id.eSubmit);
        eImage = (ImageView) findViewById(R.id.eImage);
        description = (EditText) findViewById(R.id.edescription);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Events");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        eprogress = findViewById(R.id.eprogress);

        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFiles();
            }
        });


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFiles();
            }
        });



    }
    private void chooseFiles(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CHOOSE_IMAGE);
    }
    private String getFileEx(Uri imguri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(imguri));
    }



    private void uploadFiles(){
        eprogress.setVisibility(View.VISIBLE);
        if(imguri!=null){
            final StorageReference fileref =storageReference.child(System.currentTimeMillis()+"."+getFileEx(imguri));
            fileref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EventsActivity.this,"Uploaded",Toast.LENGTH_LONG).show();


                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(description.getText().toString().trim(),uri.toString(),Locate.CurrentUserPhoneNumber);
                            String uploadID=databaseReference.push().getKey();
                            databaseReference.child(Locate.CurrentUserPhoneNumber).child(uploadID).setValue(upload);

                        }
                    });


                    eprogress.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EventsActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            });


        }else{
            Toast.makeText(EventsActivity.this,"No file selected",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE&& resultCode==RESULT_OK&&data!=null&&data.getData()!=null){

            imguri=data.getData();
            Picasso.with(this).load(imguri).into(eImage);
        }
    }
}
