package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ffc.emnet.ui.home.HomeFragment;
import com.ffc.emnet.ui.send.SendFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Random;

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
                    Random random = new Random();
                    Locate.notification.setValue("People needs your help. Watch Events "+"ID:"+random.nextInt());
             //       HomeFragment homeFragment = new HomeFragment();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ShowNotification("Alert", "Donot forget to delete events");

                        }
                    },2000);


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

    public void ShowNotification(String title,String Message) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(EventsActivity.this);
        Intent intent = new Intent(EventsActivity.this, SendFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(EventsActivity.this, 01, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentText(Message);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(001, builder.build());
    }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CharSequence name = getString("channel");
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", "channel", importance);
            channel.setDescription("Disaster Management");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(channel);




            Notification newMessageNotification = new Notification.Builder(EventsActivity.this, "channel")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentText(Message)
                    .build();

// Issue the notification.
            NotificationManagerCompat notificationManager3 = NotificationManagerCompat.from(EventsActivity.this);
            notificationManager3.notify(001, newMessageNotification);


        }
    }


}
