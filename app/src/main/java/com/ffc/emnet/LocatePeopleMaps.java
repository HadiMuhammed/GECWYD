package com.ffc.emnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
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





   Locate.Floodref.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for(DataSnapshot ds : dataSnapshot.getChildren()){
            phonenumber = ds.getKey();
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       Mark(phonenumber,"flood");

                   }
               },0100);
           }
       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {

       }
   });

        Locate.LandSlideref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    phonenumber = ds.getKey();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Mark(phonenumber,"landslide");

                        }
                    },0100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        Locate.EarthQuakeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    phonenumber = ds.getKey();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Mark(phonenumber,"earthquake");

                        }
                    },0100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Locate.Robbery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    phonenumber = ds.getKey();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Mark(phonenumber,"robbery");

                        }
                    },0100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Locate.Roadaccident.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    phonenumber = ds.getKey();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Mark(phonenumber,"roadaccident");

                        }
                    },0100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Locate.Childmissing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    phonenumber = ds.getKey();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Mark(phonenumber,"childmissing");

                        }
                    },0100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Locate.Iamintrouble.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    phonenumber = ds.getKey();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Mark(phonenumber,"iamintrouble");

                        }
                    },0100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void Message(String s)
    {
        Toast.makeText(LocatePeopleMaps.this,s,Toast.LENGTH_SHORT).show();
    }
    String Latitude_,Longitude_;
    public void Mark(String s,String y)
    {

        FirebaseDatabase mdatabase;
        mdatabase = FirebaseDatabase.getInstance();
        DatabaseReference Latituderef = mdatabase.getReference("Locate/"+s+"/Latitude");
        DatabaseReference Longituderef = mdatabase.getReference("Locate/"+s+"/Longitude");

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








      if(y=="flood") {
          Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                  LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                  mMap.addMarker(new MarkerOptions().position(location).title("Flood ALert").snippet(phonenumber))
                          .showInfoWindow();
                  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));




              }
          },4000);

          // Add a marker in Sydney and move the camera



      }


        if(y=="earthquake") {

            // Add a marker in Sydney and move the camera

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                    mMap.addMarker(new MarkerOptions().position(location).title("Earthquake ALert").snippet(phonenumber)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));


                }
            },4000);
        }

        if(y=="landslide") {

            // Add a marker in Sydney and move the camera

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                    mMap.addMarker(new MarkerOptions().position(location).title("LandSlide ALert").snippet(phonenumber)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));

                }
            },4000);
        }

        if(y=="robbery") {

            // Add a marker in Sydney and move the camera

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                    mMap.addMarker(new MarkerOptions().position(location).title("Robbery").snippet(phonenumber)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));

                }
            },4000);
        }


        if(y=="roadaccident") {

            // Add a marker in Sydney and move the camera

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                    mMap.addMarker(new MarkerOptions().position(location).title("Road Accident").snippet(phonenumber)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));

                }
            },4000);
        }

        if(y=="childmissing") {

            // Add a marker in Sydney and move the camera

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                    mMap.addMarker(new MarkerOptions().position(location).title("Child Missing").snippet(phonenumber)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));

                }
            },4000);
        }


        if(y=="iamintrouble") {

            // Add a marker in Sydney and move the camera

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatLng location = new LatLng(Double.parseDouble(Latitude_), Double.parseDouble(Longitude_));
                    mMap.addMarker(new MarkerOptions().position(location).title("Please Help").snippet(phonenumber)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));

                }
            },4000);
        }





    }








}
