package com.ffc.emnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class HomePage extends AppCompatActivity implements  Locate {
GridView listview;
String[] Items = {"Flood","Land Slide","Earth Quake","Fire"};
int[] Images = {R.drawable.flood,R.drawable.landslide,R.drawable.earth,R.drawable.fire};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    listview =  (GridView) findViewById(R.id.listview);
    CustomAdapter customAdapter = new CustomAdapter();
    listview.setAdapter(customAdapter);






    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==0)
            {
               // Toast.makeText(HomePage.this,"An Alert is Send !",Toast.LENGTH_SHORT).show();
                Locate.Floodref.child(Locate.CurrentUserPhoneNumber).setValue("");
                Intent intent = new Intent(HomePage.this, EventsActivity.class);
                startActivity(intent);
            }
            else if(i==1)
            {
               // Toast.makeText(HomePage.this,"An Alert is Send !",Toast.LENGTH_SHORT).show();
                Locate.LandSlideref.child(Locate.CurrentUserPhoneNumber).setValue("");
                Intent intent = new Intent(HomePage.this, EventsActivity.class);
                startActivity(intent);
            }
           else if(i==2)
            {
             //   Toast.makeText(HomePage.this,"An Alert is Send !",Toast.LENGTH_SHORT).show();
                Locate.EarthQuakeref.child(Locate.CurrentUserPhoneNumber).setValue("");
                Intent intent = new Intent(HomePage.this, EventsActivity.class);
                startActivity(intent);
            }
            else if(i==3)
            {
              //  Toast.makeText(HomePage.this,"An Alert is Send !",Toast.LENGTH_SHORT).show();
                Locate.Fireref.child(Locate.CurrentUserPhoneNumber).setValue("");
                Intent intent = new Intent(HomePage.this, EventsActivity.class);
                startActivity(intent);
            }

            Animation animation = AnimationUtils.loadAnimation(HomePage.this,R.anim.newanim);
            listview.getChildAt(i).startAnimation(animation);
        }
    });




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
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

                view = getLayoutInflater().inflate(R.layout.customlayout, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                TextView textView = (TextView) view.findViewById(R.id.title);
                textView.setText(Items[i]);
                imageView.setImageResource(Images[i]);


            Animation animation = AnimationUtils.loadAnimation(HomePage.this,R.anim.newanim);
            view.startAnimation(animation);
            return view;
        }

    }
}
