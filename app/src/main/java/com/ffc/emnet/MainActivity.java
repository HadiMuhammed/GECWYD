package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ffc.emnet.ui.send.SendFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements  Locate {
private Button loginBtn;
private EditText phoneNumber;
private FirebaseAuth mAuth;
private EditText userName;
private FirebaseDatabase database = FirebaseDatabase.getInstance();
private DatabaseReference myRef = database.getReference("Users/");
private ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressbar = (ProgressBar) findViewById(R.id.progress);
        userName = (EditText) findViewById(R.id.username);
        loginBtn =  (Button) findViewById(R.id.loginbtn);
        phoneNumber = (EditText) findViewById(R.id.phonenumber);
        mAuth = FirebaseAuth.getInstance();
        checklogin();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

                //finish();


                // Intent intent1 = new Intent(ProfileActivity.this,ProfileActivity.class);
                // startActivity(intent1);
                // finish();



                //  return;
            }
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumberString;
                progressbar.setVisibility(View.VISIBLE);
                phoneNumberString = phoneNumber.getText().toString();
                if(phoneNumberString.isEmpty())
                {
                    phoneNumber.setError("Must enter your number");
                    phoneNumber.requestFocus();
                }
               else if(phoneNumberString.length()!=13)
                {
                    phoneNumber.setError("Must enter 10 digit number after +91");
                    phoneNumber.requestFocus();
                }
               String usernameString=userName.getText().toString();
               if(usernameString.isEmpty())
               {
                   userName.setError("Enter your Name");
                   userName.requestFocus();
               }

               PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumberString, 30L, TimeUnit.SECONDS, MainActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                   @Override
                   public void onCodeSent(String verificationId,
                                          PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                       // Save the verification id somewhere
                       // ...

                       // The corresponding whitelisted code above should be used to complete sign-in.
                       Message("Verification Code Send");
                   }

                   @Override
                   public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                       Signin(phoneAuthCredential);


                   }

                   @Override
                   public void onVerificationFailed(FirebaseException e) {
                        Message("try after 24 hours");
                       progressbar.setVisibility(View.GONE);
                   }
               });

            }
        });










    }




    private void Signin(PhoneAuthCredential Credential)
{
    mAuth.signInWithCredential(Credential).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
        Message(mAuth.getCurrentUser().getPhoneNumber());
        myRef = database.getReference("Users/");
        myRef.child(mAuth.getCurrentUser().getPhoneNumber()).setValue(userName.getText().toString());
            progressbar.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this,UserPage.class);
            startActivity(intent);

        }
    });
}
    private void Message(String Message) {
        Toast.makeText(MainActivity.this,Message,Toast.LENGTH_SHORT).show();
    }

    private void checklogin() {
       try{
           String currentUser = mAuth.getCurrentUser().getPhoneNumber().toString();

           if(currentUser.isEmpty())
        {
            //code to request login
            Message("please login");
            progressbar.setVisibility(View.GONE);
        }
        else
        {
            //code to
            Message("You are logged in");
            progressbar.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this,UserPage.class);
            startActivity(intent);
        }
        }
       catch (Exception e)
       {
           //catch
           Message("please login");
           progressbar.setVisibility(View.GONE);
       }

    }

}
