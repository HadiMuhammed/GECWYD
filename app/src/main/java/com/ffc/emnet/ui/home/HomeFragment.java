package com.ffc.emnet.ui.home;

import android.Manifest;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ffc.emnet.EventsActivity;
import com.ffc.emnet.HomePage;
import com.ffc.emnet.Locate;
import com.ffc.emnet.MainActivity;
import com.ffc.emnet.R;
import com.ffc.emnet.SosSetting;
import com.ffc.emnet.ui.send.SendFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.v4.app.INotificationSideChannel;

import static android.content.Context.NOTIFICATION_SERVICE;

public class HomeFragment extends Fragment {

    private NotificationManagerCompat notificationManagerCompat;
    private HomeViewModel homeViewModel;
    private String datapath;
    private DatabaseReference dref;
    private GridView HomeItems;
    private String[] Items = {"Natural Disaster","Robbery","Road Accident","Child Missing","Iam in Trouble","Delete Alerts","Set SOS","Delete SOS List"};
    private int[] Images = {R.drawable.natural_disaster,R.drawable.download,R.drawable.acccident,R.drawable.child,R.drawable.trouble,R.drawable.delete,R.drawable.ic_call,R.drawable.delete};


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        getActivity().startService(new Intent(getActivity(),MyService.class));





        HomeItems =  root.findViewById(R.id.HomeItems);
        CustomAdapter customAdapter = new CustomAdapter();
        HomeItems.setAdapter(customAdapter);

        HomeItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    Intent intent = new Intent(getActivity(),HomePage.class);
                    startActivity(intent);
                }
              else  if(i==1)
                {
                 //   Message("ALert is Send");
                    Locate.Robbery.child(Locate.CurrentUserPhoneNumber).setValue("");
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);
                }
              else if(i==2)
                {
                   // Message("ALert is Send");
                    Locate.Roadaccident.child(Locate.CurrentUserPhoneNumber).setValue("");
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);
                }
              else  if(i==3)
                {
                    //Message("ALert is Send");
                    Locate.Childmissing.child(Locate.CurrentUserPhoneNumber).setValue("");
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);
                }
              else if(i==4)
                {
                   // Message("ALert is Send");
                    Locate.Iamintrouble.child(Locate.CurrentUserPhoneNumber).setValue("");
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);

                }
              else if(i==5)
                {

                    Toast.makeText(getActivity(),"All Alerts are Deleted !",Toast.LENGTH_SHORT).show();
                    Locate.Floodref.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.LandSlideref.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.EarthQuakeref.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Robbery.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Roadaccident.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Childmissing.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Iamintrouble.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Fireref.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.notification.removeValue();
                    dref = FirebaseDatabase.getInstance().getReference("Events/"+Locate.CurrentUserPhoneNumber);
                    dref.removeValue();


                }
              else if(i==6)
                {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {


                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);


                    }
                    else
                    {
                    Intent intent = new Intent(getActivity(), SosSetting.class);
                    startActivity(intent);
                    }
                }
              else if(i==7)
                {
                    Locate.SOS.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Toast.makeText(getActivity(),"SoS list Deleted !",Toast.LENGTH_SHORT).show();

                }
            }
        });







        return root;
    }
    public void ShowNotification(String title,String Message) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setContentText(Message);
            builder.setContentTitle(title);
            builder.setSmallIcon(R.drawable.exo_notification_small_icon);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
           notificationManager.notify(001, builder.build());

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", importance);
            channel.setDescription("Disaster Management");
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

           NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
           Notification notification = new NotificationCompat.Builder(getActivity(),"channel_id")
                   .setSmallIcon(R.drawable.exo_notification_small_icon)
                   .setDefaults(Notification.DEFAULT_ALL)
                   .setContentText(Message)
                   .setContentTitle(title)
                   .setPriority(NotificationCompat.PRIORITY_HIGH)
                   .build()
                   ;

           notificationManager.notify(001,notification);


        }
    }


    public void Message(String s)
    {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Items.length;
        }

        @Override
        public Object getItem(int i) {
            return Items[i];
        }



        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView textView = (TextView) view.findViewById(R.id.title);
            textView.setText(Items[i]);
            imageView.setImageResource(Images[i]);
            Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.newanim);
            view.startAnimation(animation);

            return view;
        }

    }








}