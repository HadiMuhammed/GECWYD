package com.ffc.emnet.ui.send;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.ffc.emnet.LocatePeopleMaps;
import com.ffc.emnet.R;
import com.ffc.emnet.Upload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    private ListView eventlistview;
    private List<Upload> mUploads;
    private List<String> descripttion;
    private List<String> url;
    private List<String> PhoneNumber;
    private DatabaseReference mref;
    private StorageReference storageReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel = ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);

                eventlistview = root.findViewById(R.id.showevents);
                final customadaptorforevents customadaptor = new customadaptorforevents();
                descripttion = new ArrayList<>();
                url =  new ArrayList<>();
                PhoneNumber =  new ArrayList<>();
                mUploads = new ArrayList<>();
                mref = FirebaseDatabase.getInstance().getReference("Events");
                storageReference = FirebaseStorage.getInstance().getReference("Events");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        eventlistview.setAdapter(customadaptor);

                    }
                },3000);

                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ks : dataSnapshot.getChildren())
                        for(DataSnapshot ds : ks.getChildren()){
                            Upload upload = ds.getValue(Upload.class);
                            mUploads.add(upload);
                            descripttion.add(upload.getImgName());
                            PhoneNumber.add(upload.getPhonenumber());
                            url.add(upload.getImgUri());


                        }
                        customadaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                    eventlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String Phone;
                            Phone = PhoneNumber.get(i);
                           final Intent intent = new Intent(getActivity(), LocatePeopleMaps.class);
                            intent.putExtra("Phone Number",Phone);
                            startActivity(intent);


                        }
                    });


        return root;
    }

    private class customadaptorforevents extends BaseAdapter{

        @Override
        public int getCount() {
            return mUploads.size();
        }

        @Override
        public Object getItem(int i) {
            return PhoneNumber.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            try {
                if(view==null) {
                    view = getLayoutInflater().inflate(R.layout.customlayoutforevents, null);
                }
                TextView imgDesc = (TextView) view.findViewById(R.id.EventsDescription);
                ImageView imgView = (ImageView) view.findViewById(R.id.EventsImage);
                TextView imgPhone = view.findViewById(R.id.EventsPhonenumber);
                imgDesc.setText(descripttion.get(i));
                imgPhone.setText(PhoneNumber.get(i));
                Picasso.with(getActivity()).load((url.get(i))).into(imgView);
                   // imgView.setImageURI(Uri.parse(url.get(i)));
                return view;

            }
            catch (Exception e){}

            return view;
        }
    }
}