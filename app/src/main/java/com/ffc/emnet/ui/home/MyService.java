package com.ffc.emnet.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ffc.emnet.Locate;
import com.ffc.emnet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MyService extends Service {
    public void ShowNotification(String title,String Message) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this);
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setContentText(Message);
            builder.setContentTitle(title);
            builder.setSmallIcon(R.drawable.exo_notification_small_icon);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(001, builder.build());

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", importance);
            channel.setDescription("Disaster Management");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyService.this);
            Notification notification = new NotificationCompat.Builder(MyService.this,"channel_id")
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
    public MyService() {

    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startid){



      //  Toast.makeText(MyService.this,"service started",Toast.LENGTH_SHORT).show();

        Locate.notification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (!dataSnapshot.getValue(String.class).isEmpty())
                        ShowNotification("Alert", dataSnapshot.getValue(String.class));
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return Service.START_NOT_STICKY;
    }
    @Override
    public void onCreate(){


    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       // throw new UnsupportedOperationException("Not yet implemented");


        throw new UnsupportedOperationException("Not yet implemented");
    }
}
