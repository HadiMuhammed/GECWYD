package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference usernameref;
    private String Username,Phonenumber;
    private TextView pUsername;
    private TextView pPhonenumber;
    private ProgressBar pProgress;
    private String phonenumber;
    private Button pCaller;
    private Button pChatter;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("PhoneNumber");
        usernameref = database.getReference("Users/" + phonenumber);
        pUsername = (TextView) findViewById(R.id.profileUsername);
        pPhonenumber = findViewById(R.id.profileNumber);
        pProgress = findViewById(R.id.profileProgress);
        pCaller = (Button) findViewById(R.id.profileCaller);
        pChatter = (Button) findViewById(R.id.profileChat);

        pProgress.setVisibility(View.VISIBLE);
        usernameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              try {
                  Username = dataSnapshot.getValue().toString();
                  Phonenumber = dataSnapshot.getKey();

              }catch(Exception e){

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FillData();

        pCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkpermissionforcall();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    makephonecall();
                }
                else
                {
                    Toast.makeText(ProfileActivity.this,"App is not Supported",Toast.LENGTH_SHORT).show();
                }
            }
        });



        pChatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,ProfileChatActivity.class);
                intent.putExtra("Phone Number",phonenumber);
                intent.putExtra("User Name",pUsername.getText());
                startActivity(intent);
            }
        });





    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void makephonecall() {
        String number = pPhonenumber.getText().toString().trim();
        if (number.length() != 13) {
            pPhonenumber.setError("Number is not Loaded");
            pPhonenumber.requestFocus();
        } else {
            String dial = "tel:" + number;
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                ActivityCompat.requestPermissions(ProfileActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);

            }
            else
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    private void checkpermissionforcall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(ProfileActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);

                //finish();


                // Intent intent1 = new Intent(ProfileActivity.this,ProfileActivity.class);
                // startActivity(intent1);
                // finish();



                //  return;
            }
            else
            {
                Toast.makeText(ProfileActivity.this,"Calling",Toast.LENGTH_SHORT).show();

            }
        }
        else
        {
            Toast.makeText(ProfileActivity.this,"Your Mobile doesnot support this app",Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void FillData() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pUsername.setText(Username);

                pPhonenumber.setText(phonenumber);
                if(pUsername.getText().toString().toLowerCase()!="user name")
                    pProgress.setVisibility(View.GONE);
            }
        },3000);

    }

    private void Message(String username) {
        Toast.makeText(ProfileActivity.this,username,Toast.LENGTH_SHORT).show();
    }
}
