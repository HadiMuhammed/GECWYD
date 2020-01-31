package com.ffc.emnet.ui.share;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ffc.emnet.ItemUploadActivity;
import com.ffc.emnet.Locate;
import com.ffc.emnet.PublicChatDatabase;
import com.ffc.emnet.R;
import com.ffc.emnet.Upload;
import com.ffc.emnet.Upload2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment implements PublicChatDatabase {

    private ShareViewModel shareViewModel;
    private Button send;
    private EditText messagebox;
    private ListView messages;
    private String Phonenumber;
    private List<String> MessagesString;
    private List<Upload2> mUploads;
    private List<String> imageName;
    private List<String> videoName;
    private List<String> message;
    private List<String> urlforimage;
    private List<String> urlforvideo;
    private List<String> phonenumber;
    private StorageReference storageReference;
    private DatabaseReference mref;
    private Button upload;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        messagebox = (EditText) root.findViewById(R.id.textGroup);
        send = (Button) root.findViewById(R.id.SendButtonForPublic);
        Phonenumber = PublicChatDatabase.muser;
        messages = (ListView) root.findViewById(R.id.messageGroup);
        final CustomadaptorForpublic customadaptorForpublic = new CustomadaptorForpublic();
        MessagesString =  new ArrayList<>();
        mUploads = new ArrayList<>();
        imageName = new ArrayList<>();
        videoName = new ArrayList<>();
        message = new ArrayList<>();
        urlforimage = new ArrayList<>();
        urlforvideo = new ArrayList<>();
        phonenumber = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference("/Public/");
        mref = FirebaseDatabase.getInstance().getReference("/PublicChat/");
        upload =  (Button) root.findViewById(R.id.uploadbuttonforimageandvideo);

        Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                messages.setAdapter(customadaptorForpublic);
            }
        },3000);




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(messagebox.getText() != null) {
                    Upload2 upload = new Upload2(messagebox.getText().toString(),"no uri","nothing","no uri",PublicChatDatabase.muser);
                    String uploadID=mref.push().getKey();
                    mref.child(Locate.CurrentUserPhoneNumber).child(uploadID).setValue(upload);

                    messagebox.setText("");
                }
            }
        });



        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                imageName.clear();
                videoName.clear();
                urlforimage.clear();
                urlforvideo.clear();
                phonenumber.clear();

                for(DataSnapshot ks : dataSnapshot.getChildren())
                    for(DataSnapshot ds : ks.getChildren()){

                        Upload2 upload = ds.getValue(Upload2.class);
                        mUploads.add(upload);
                        imageName.add(upload.getImgname());
                        videoName.add(upload.getVideoname());
                        urlforimage.add(upload.getImgurl());
                        urlforvideo.add(upload.getVideourl());
                        phonenumber.add(upload.getPhonenumber());

                    }
                customadaptorForpublic.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ItemUploadActivity.class);
                    intent.putExtra("message",messagebox.getText());
                    startActivity(intent);


                }
            });





        return root;


    }


    public class CustomadaptorForpublic extends BaseAdapter{

        @Override
        public int getCount() {
            return mUploads.size();
        }

        @Override
        public Object getItem(int i) {
            return phonenumber.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            try{
                if(view == null)
                {
                    view =  getLayoutInflater().inflate(R.layout.customlayoutforpublic,null);
                }
                TextView phonenumber1 = (TextView) view.findViewById(R.id.phonenumberInThePublicChat);
                TextView username1 = (TextView) view.findViewById(R.id.usernameInThePublicChat);
                TextView message = (TextView) view.findViewById(R.id.messageInThePublicChat);
                ImageView image = (ImageView) view.findViewById(R.id.ImageinthePublicChat);
                final VideoView video = (VideoView) view.findViewById(R.id.VideointhePublicChat);

                phonenumber1.setText(phonenumber.get(i));
                message.setText(imageName.get(i));
                Picasso.with(getActivity()).load((urlforimage.get(i))).into(image);
                image.setVisibility(View.VISIBLE);
                video.setVideoURI(Uri.parse(urlforvideo.get(i)));
                video.setVisibility(View.VISIBLE);
                video.setMediaController(new MediaController(getActivity()));
               // video.start();

                video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                       // video.setVisibility(View.GONE);
                    }
                });
                video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        video.setVisibility(View.GONE);

                        return true;
                    }
                });

                Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.newanim);
                view.startAnimation(animation);
                return view;



            }catch (Exception e)
            {

            }



            return view;
        }
    }
}