package com.ffc.emnet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ffc.emnet.HomePage;
import com.ffc.emnet.Locate;
import com.ffc.emnet.LocatePeopleMaps;
import com.ffc.emnet.R;
import com.ffc.emnet.UserPage;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ListView HomeItems;
    private String[] Items = {"Natural Disaster","Robbery","Road Accident","Child Missing","Show Alerts","Iam in Trouble","Delete Alerts"};
    private int[] Images = {R.drawable.natural_disaster,R.drawable.download,R.drawable.acccident,R.drawable.child,R.drawable.locate,R.drawable.trouble,R.drawable.delete};


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
                }
              else if(i==2)
                {
                    Message("ALert is Send");
                    Locate.Roadaccident.child(Locate.CurrentUserPhoneNumber).setValue("");
                }
              else  if(i==3)
                {
                    Message("ALert is Send");
                    Locate.Childmissing.child(Locate.CurrentUserPhoneNumber).setValue("");
                }
              else if(i==4)
                {
                    Message("Please wait..");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getActivity(), LocatePeopleMaps.class);
                            startActivity(intent);
                        }
                    },0100);

                }
              else if(i==5)
                {
                    Message("ALert is Send");
                    Locate.Iamintrouble.child(Locate.CurrentUserPhoneNumber).setValue("");

                }
                if(i==6)
                {

                    Toast.makeText(getActivity(),"All Alerts are Deleted !",Toast.LENGTH_SHORT).show();
                    Locate.Floodref.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.LandSlideref.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.EarthQuakeref.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Robbery.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Roadaccident.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Childmissing.child(Locate.CurrentUserPhoneNumber).removeValue();
                    Locate.Iamintrouble.child(Locate.CurrentUserPhoneNumber).removeValue();


                }
            }
        });




        return root;
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
            return view;
        }

    }
}