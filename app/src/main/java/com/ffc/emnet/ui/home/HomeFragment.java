package com.ffc.emnet.ui.home;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
    private String[] Items = {"Natural Disaster","Robbery","Road Accident","Child Missing","Iam in Trouble","Delete Alerts"};
    private int[] Images = {R.drawable.natural_disaster,R.drawable.download,R.drawable.acccident,R.drawable.child,R.drawable.trouble,R.drawable.delete};


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);









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
                    Message("ALert is Send");
                    Locate.Robbery.child(Locate.CurrentUserPhoneNumber).setValue("");
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);
                }
              else if(i==2)
                {
                    Message("ALert is Send");
                    Locate.Roadaccident.child(Locate.CurrentUserPhoneNumber).setValue("");
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);
                }
              else  if(i==3)
                {
                    Message("ALert is Send");
                    Locate.Childmissing.child(Locate.CurrentUserPhoneNumber).setValue("");
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);
                }
              else if(i==4)
                {
                    Message("ALert is Send");
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
            }
        });

        Locate.notification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (!dataSnapshot.getValue(String.class).isEmpty())
                        ShowNotification("Help", dataSnapshot.getValue(String.class));
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return root;
    }
    public void ShowNotification(String title,String Message) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
            Intent intent = new Intent(getActivity(), SendFragment.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 01, intent, 0);
            builder.setContentIntent(pendingIntent);
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setContentText(Message);
            builder.setContentTitle(title);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(001, builder.build());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CharSequence name = getString("channel");
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", "channel", importance);
            channel.setDescription("Disaster Management");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager1 = getActivity().getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(channel);




            Notification newMessageNotification = new Notification.Builder(getActivity(), "channel")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentText(Message)
                    .build();

// Issue the notification.
            NotificationManagerCompat notificationManager3 = NotificationManagerCompat.from(getActivity());
            notificationManager3.notify(001, newMessageNotification);


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