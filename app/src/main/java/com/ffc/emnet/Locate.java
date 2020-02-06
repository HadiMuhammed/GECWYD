package com.ffc.emnet;

import android.Manifest;
import android.app.Application;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Provider;

import static android.content.Context.LOCATION_SERVICE;

public interface Locate {

     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference mref = database.getReference("Locate");
     DatabaseReference Floodref = database.getReference("FLOOD");
     DatabaseReference LandSlideref = database.getReference("LANDSLIDE");
     DatabaseReference EarthQuakeref = database.getReference("EARTHQUAKE");
     DatabaseReference Fireref = database.getReference("FIRE");
     DatabaseReference Robbery = database.getReference("ROBBERY");
     DatabaseReference Roadaccident = database.getReference("ROADACCIDENT");
     DatabaseReference Childmissing = database.getReference("CHILDMISSING");
     DatabaseReference Iamintrouble = database.getReference("IAMINTROUBLE");
     DatabaseReference Events = database.getReference("Events");
     DatabaseReference notification = database.getReference("notification");
     DatabaseReference SOS = database.getReference("SOS");


     FirebaseAuth mAuth = FirebaseAuth.getInstance();
     FirebaseUser Currentuser =  mAuth.getCurrentUser();
     String CurrentUserPhoneNumber = Currentuser.getPhoneNumber();

}
