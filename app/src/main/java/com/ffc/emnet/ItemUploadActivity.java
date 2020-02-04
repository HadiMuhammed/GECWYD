package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ItemUploadActivity extends AppCompatActivity implements PublicChatDatabase{
    private EditText textbox;
    private ProgressBar progressBar;
    private Button videobtn;
    private Button imagebtn;
    private Button uploader;
    private ImageView Imageuploadview;
    private VideoView Videouploadview;
    private Uri Itemuri;
    private static final int CHOOSE_IMAGE = 1;
    private static final int CHOOSE_VIDEO = 2;
    private String message;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_upload);
        progressBar = (ProgressBar) findViewById(R.id.progressuploads);
        videobtn = (Button) findViewById(R.id.videoupload);
        imagebtn = (Button) findViewById(R.id.imageoupload);
        uploader = (Button) findViewById(R.id.uploadbuttonforpublic);
        Imageuploadview = (ImageView) findViewById(R.id.imageSelect);
        Videouploadview = (VideoView) findViewById(R.id.videoSelect);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Public");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PublicChat/");
        textbox = (EditText) findViewById(R.id.textboxforupload);
        Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                message = intent.getStringExtra("message");

                if(message == null)
                {
                    message = "No name";
                }
            }
        },3000);


       videobtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               chooseVideo();
           }
       });

       imagebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               chooseImages();
           }
       });

       uploader.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               UploadFiles();
           }
       });



    }
    private void chooseImages(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CHOOSE_IMAGE);
    }

    private void chooseVideo(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CHOOSE_VIDEO);
    }

    private String getFileEx(Uri itemuri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(itemuri));
    }

    private String imageuri;
    private String videouri;
    private void UploadFiles(){
        progressBar.setVisibility(View.VISIBLE);
        if(Itemuri!=null)
        {
            final StorageReference fileref =storageReference.child(System.currentTimeMillis()+"."+getFileEx(Itemuri));
            fileref.putFile(Itemuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ItemUploadActivity.this,"Uploaded",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            if(getFileEx(Itemuri)== "mp4")
                            {
                                imageuri = "no uri";
                                videouri = uri.toString();
                            }
                            if(getFileEx(Itemuri)== "jpg")
                            {
                                videouri = "no uri";
                                imageuri = uri.toString();
                            }
                            if(getFileEx(Itemuri)== "jpeg")
                            {
                                videouri = "no uri";
                                imageuri =  uri.toString();
                            }
                            if(getFileEx(Itemuri)== "png")
                            {
                                videouri = "no uri";
                                imageuri =  uri.toString();
                            }
                            if(getFileEx(Itemuri)== "gif")
                            {
                                videouri = "no uri";
                                imageuri =  uri.toString();
                            }
                            Upload2 upload = new Upload2(textbox.getText().toString().trim(),imageuri,message.trim(),videouri,PublicChatDatabase.muser.toString());
                            String uploadID=databaseReference.push().getKey();
                            databaseReference.push().child(uploadID).setValue(upload);

                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ItemUploadActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            });


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE&& resultCode==RESULT_OK&&data!=null&&data.getData()!=null){

            Itemuri=data.getData();
            Picasso.with(this).load(Itemuri).into(Imageuploadview);
            Imageuploadview.setVisibility(View.VISIBLE);
            Videouploadview.setVideoURI(null);
            Videouploadview.setVisibility(View.GONE);

        }
        if(requestCode==CHOOSE_VIDEO&& resultCode==RESULT_OK&&data!=null&&data.getData()!=null){

            Itemuri=data.getData();
            Videouploadview.setVideoURI(Itemuri);
            Videouploadview.setVisibility(View.VISIBLE);
            Videouploadview.seekTo(1);
            Imageuploadview.setImageURI(null);
            Imageuploadview.setVisibility(View.GONE);
        }
    }

}
