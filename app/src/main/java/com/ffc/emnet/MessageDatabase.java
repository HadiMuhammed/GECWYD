package com.ffc.emnet;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public interface MessageDatabase {
     FirebaseAuth mauth=FirebaseAuth.getInstance();
     String muser=mauth.getCurrentUser().getPhoneNumber();
     FirebaseDatabase database=FirebaseDatabase.getInstance();
     DatabaseReference inboxref=database.getReference("InboxOf/"+muser);
     DatabaseReference inboxofref = database.getReference("InboxOf/");

}
