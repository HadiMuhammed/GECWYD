package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Contacts;
import android.view.View;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SosSetting extends AppCompatActivity {
    private Button btn;
    private ListView listView1;

    ArrayAdapter adapter;
    //List<String> namelist;
    private SearchView searchView;
    ArrayList<Mycontacts> mycontactsList = new ArrayList<Mycontacts>();
    final MyAdaptor myAdaptor = new MyAdaptor(SosSetting.this,mycontactsList);
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_setting);
        listView1 = (ListView) findViewById(R.id.soslistingOnsetting);
        btn = (Button) findViewById(R.id.selectsos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get();
            }
        });
        searchView = (SearchView) findViewById(R.id.searchbar);
        //listView1.setTextFilterEnabled(true);



        listView1.setAdapter(myAdaptor);


        Locate.SOS.child(Locate.CurrentUserPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Mycontacts contacts = new Mycontacts();
                mycontactsList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    contacts = ds.getValue(Mycontacts.class);
                    mycontactsList.add(contacts);
                    myAdaptor.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    public void get() {

        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContact, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1 && data !=null)
        {


        Uri contactData = data.getData();
        Cursor c = getContentResolver().query(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String num = c.getString(phoneIndex);
            String name = c.getString(nameIndex);
            Toast.makeText(SosSetting.this, "Number: " + num + " added", Toast.LENGTH_LONG).show();

            Mycontacts mycontacts = new Mycontacts(name, num);
            Locate.SOS.child(Locate.CurrentUserPhoneNumber).push().setValue(mycontacts);
            mycontactsList.add(mycontacts);


            myAdaptor.notifyDataSetChanged();


            //((ArrayAdapter<Object>) listView1.getAdapter()).notifyDataSetChanged();

        }
        }
        else
        {

        }
    }
}