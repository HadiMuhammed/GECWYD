package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ProfileChatActivity extends AppCompatActivity implements MessageDatabase {
    private String username,phonenumber;
    private ListView messages;
    private Button sendbtn;
    private EditText messagestext;
    private List<String> MessagesString;
    private List<String> UsernamesString;
    private List<String> PhonenumbersString;
    private CustomAdaptorforMessage customadaptor = new CustomAdaptorforMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_chat);

        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("Phone Number");
        username = intent.getStringExtra("User Name");

        MessagesString = new ArrayList<>();
        UsernamesString = new ArrayList<>();
        PhonenumbersString = new ArrayList<>();
        messages = (ListView) findViewById(R.id.messages);
        messagestext = (EditText) findViewById(R.id.messagetext);
        sendbtn = (Button) findViewById(R.id.message_send);
        messages.setAdapter(customadaptor);
      LoadMessage();



        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDatabase.inboxref.child(phonenumber).setValue(messagestext.getText().toString());
                messagestext.setText("");

            }
        });





    }

    private void LoadMessage()
    {

        MessageDatabase.inboxref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    MessagesString.add(ds.getValue().toString());
                    UsernamesString.add("Message :");
                    PhonenumbersString.add(ds.getKey());



                }

                customadaptor.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private class CustomAdaptorforMessage extends BaseAdapter{

        @Override
        public int getCount() {

                int size=0;
                for(String s : MessagesString)
                {
                    size++;
                }
                return size ;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {


                view = getLayoutInflater().inflate(R.layout.customlayoutformessage, null);
            }
                TextView PhonenumberInTheList = (TextView) view.findViewById(R.id.phonenumberInTheList);
                TextView UsernameInTheList = (TextView) view.findViewById(R.id.usernameInTheList);
                TextView MessageInTheList = (TextView) view.findViewById(R.id.messageInTheList);

            MessageInTheList.setText( MessagesString.get(i));
            UsernameInTheList.setText(UsernamesString.get(i));
            PhonenumberInTheList.setText(PhonenumbersString.get(i));

            return view;
        }
    }
}
