package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocatePeopleMaps extends FragmentActivity implements OnMapReadyCallback , Locate {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_people_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);









    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    String phonenumber;

    @Override
    public void onMapReady(GoogleMap googleMap) {


        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));


        } catch (Resources.NotFoundException e) {
        }



        mMap = googleMap;



//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            //@Override
          //  public boolean onMarkerClick(Marker marker) {
  //              String phonenumbersnippet = marker.getSnippet();
    //            Intent intent = new Intent(LocatePeopleMaps.this,ProfileActivity.class);
      //          intent.putExtra("PhoneNumber",phonenumbersnippet);
        //        startActivity(intent);
          //      return false;
           // }
        //});

       mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
           @Override
           public void onInfoWindowClick(Marker marker) {
               String phonenumbersnippet = marker.getSnippet();
               Intent intent = new Intent(LocatePeopleMaps.this,ProfileActivity.class);
               intent.putExtra("PhoneNumber",phonenumbersnippet);
               startActivity(intent);
           }
       });

          //  Intent intent2 = getIntent();
            //String phonenumber = intent2.getStringExtra("Phone Number 1");



  // Locate.Floodref.addValueEventListener(new ValueEventListener() {
    //   @Override
      // public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //   for(DataSnapshot ds : dataSnapshot.getChildren()){
          //  phonenumber = ds.getKey();
            //           Mark(phonenumber,"flood");

//
  //         }
    //   }

//       @Override
  //     public void onCancelled(@NonNull DatabaseError databaseError) {
//
  //     }
   //}//);

    Message("Locating the Phonenumber..");

                final Intent intent = getIntent();
                Handler mhandler = new Handler();
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        phonenumber=intent.getStringExtra("Phone Number 1");
                        Mark(phonenumber,"a");
                    }
                }, 3000);



      //  Locate.LandSlideref.addValueEventListener(new ValueEventListener() {
        //    @Override
          //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ///    for(DataSnapshot ds : dataSnapshot.getChildren()){
               //     phonenumber = ds.getKey();
                 //           Mark(phonenumber,"landslide");


                //}/
           // }

            //@Override
           // public void onCancelled(@NonNull DatabaseError databaseError) {

          //  }
       // });








      //  Locate.EarthQuakeref.addValueEventListener(new ValueEventListener() {
        //    @Override
          //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //    for(DataSnapshot ds : dataSnapshot.getChildren()){
              //      phonenumber = ds.getKey();
//
//
  //                          Mark(phonenumber,"earthquake");
//
//
//
  //              }
    //        }

        //    @Override
      //      public void onCancelled(@NonNull DatabaseError databaseError) {

          //  }
       // });

      //  Locate.Robbery.addValueEventListener(new ValueEventListener() {
        //    @Override
          //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //    for(DataSnapshot ds : dataSnapshot.getChildren()){
              //      phonenumber = ds.getKey();

  //                          Mark(phonenumber,"robbery");
//

    //            }
      //      }

        //    @Override
          //  public void onCancelled(@NonNull DatabaseError databaseError) {

            //}
        //});










        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void Message(String s)
    {
        Toast.makeText(LocatePeopleMaps.this,s,Toast.LENGTH_LONG).show();
    }
private         String Latitude_,Longitude_;
    public void Mark(String s,String y)
    {

        final String phone =s;

        FirebaseDatabase mdatabase;
        mdatabase = FirebaseDatabase.getInstance();
        DatabaseReference Latituderef = mdatabase.getReference("Locate/"+phone+"/Latitude");
        DatabaseReference Longituderef = mdatabase.getReference("Locate/"+phone+"/Longitude");

      Latituderef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Latitude_ = dataSnapshot.getValue().toString();



          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

      Longituderef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Longitude_ = dataSnapshot.getValue().toString();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                mMap.addMarker(new MarkerOptions().position(location).title("Please Help").snippet(phonenumber)).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));

            }
        },2000);









    }

















}
