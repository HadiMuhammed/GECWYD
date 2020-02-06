package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.view.View.GONE;

public class Sos extends AppCompatActivity {

    private Button btn;
    private ListView listView;
    private ArrayList<Mycontacts> mycontacts = new ArrayList<Mycontacts>();
    private MyAdaptor adaptor = new MyAdaptor(Sos.this,mycontacts);
    private EditText message;
    private ProgressBar progressBar;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        listView = (ListView) findViewById(R.id.SosContactslisting);
        message = (EditText) findViewById(R.id.messagetosendsos);
        listView.setAdapter(adaptor);
        progressBar = (ProgressBar) findViewById(R.id.progressforSOS);
        btn = (Button) findViewById(R.id.SelectorbuttonforSOS);
      //  btn2 = (Button) findViewById(R.id.DeleterforSOS);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = listView.getCount();
                if(!message.getText().toString().isEmpty())
                for(i=i;i>=0;i--)
                {
                    sendsms(i-1,message.getText().toString());
                }


                }

        });


        Locate.SOS.child(Locate.CurrentUserPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mycontacts.clear();
                Mycontacts mycontacts1class = new Mycontacts();
                for(DataSnapshot ds :  dataSnapshot.getChildren())
                {
                    mycontacts1class = ds.getValue(Mycontacts.class);
                    mycontacts.add(mycontacts1class);
                    adaptor.notifyDataSetChanged();

                }
                progressBar.setVisibility(GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }




    private void sendsms(int i, String message) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                int permission = checkSelfPermission(Manifest.permission.SEND_SMS);
                int permission2 = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);

                if(permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED)
                {

                    ActivityCompat.requestPermissions(Sos.this,new String[] {Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},10);

                }
                else if(permission == PackageManager.PERMISSION_GRANTED )
                {
                    try{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(listView.getItemAtPosition(i).toString(),null,message,null,null);
                    Toast.makeText(Sos.this,"Message Send",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {

                }
                }

            }
            else
            {
                Toast.makeText(Sos.this, "Phone Not Supported", Toast.LENGTH_SHORT).show();
            }



        }

}
