package com.ffc.emnet.ui.share;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.MediaSync;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ffc.emnet.ItemUploadActivity;
import com.ffc.emnet.Locate;
import com.ffc.emnet.LocatePeopleMaps;
import com.ffc.emnet.ProfileActivity;
import com.ffc.emnet.ProfileChatActivity;
import com.ffc.emnet.PublicChatDatabase;
import com.ffc.emnet.R;
import com.ffc.emnet.Upload2;
import com.ffc.emnet.Upload3;
import com.ffc.emnet.UserPage;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.firekast.FKCamera;
import io.firekast.FKCameraFragment;
import io.firekast.FKError;
import io.firekast.FKStream;
import io.firekast.FKStreamer;
import io.firekast.Firekast;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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
    private List<Upload3> mUploads2;
    private List<String> imageName2;
    private List<String> videoName2;
    private List<String> message2;
    private List<String> urlforimage2;
    private List<String> urlforvideo2;
    private List<String> phonenumber2;
    private StorageReference storageReference;
    private DatabaseReference mref;
    private Button upload;
    private  DatabaseReference usernameref;
    private String Username;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 12)
        {

        }
    }



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
        usernameref = database.getReference("Users/" + Locate.CurrentUserPhoneNumber);
        Username=null;



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int permission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);

            int permission2 = getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            if(permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED)
            {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},10);
            }
            else if(permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED)
            {
                if(hasCamera()) {


                }
            }
        }






        usernameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Username = dataSnapshot.getValue().toString();
                    //  Phonenumber = dataSnapshot.getKey();

                }catch(Exception e){

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                messages.setAdapter(customadaptorForpublic);
            }
        },3000);

                messages.setSmoothScrollbarEnabled(true);
                messages.canScrollVertically(-1);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(messagebox.getText() != null) {
                    Upload2 upload = new Upload2(messagebox.getText().toString(),"no uri", Username,"no uri",PublicChatDatabase.muser);
                    String uploadID=mref.push().getKey();
                    mref.push().child(uploadID).setValue(upload);

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
                    intent.putExtra("username",Username);
                    if(!Username.isEmpty())
                    startActivity(intent);
                    else
                        Toast.makeText(getActivity(),"Please Wait..",Toast.LENGTH_SHORT).show();


                }
            });




            messages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String Phone;
                    Phone = phonenumber.get(i);
                    final Intent intent = new Intent(getActivity(), LocatePeopleMaps.class);
                    intent.putExtra("Phone Number 1",Phone);
                    startActivity(intent);



                }
            });


        return root;


    }

    public  class CustomMediaController extends MediaController{
        public CustomMediaController(Context c,View anchor){
            super(c);
            super.setAnchorView(anchor);

        }
        @Override
        public void setAnchorView(View view)
        {
            //do nothing
        }
    }




    private boolean hasCamera() {
        if (getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT)){
            return true;
        } else {
            return false;
        }
    }


    public class CustomadaptorForpublic extends BaseAdapter {

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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            try{
                if(view == null)
                {
                    view =  getLayoutInflater().inflate(R.layout.customlayoutforpublic,null);
                }
                TextView phonenumber1 = (TextView) view.findViewById(R.id.phonenumberInThePublicChat);
                final TextView username1 = (TextView) view.findViewById(R.id.usernameInThePublicChat);
                TextView message = (TextView) view.findViewById(R.id.messageInThePublicChat);
                final ImageView image = (ImageView) view.findViewById(R.id.ImageinthePublicChat);




                username1.setText(videoName.get(i));



                phonenumber1.setText(phonenumber.get(i));
                message.setText(imageName.get(i));
                Picasso.with(getActivity()).load((urlforimage.get(i))).into(image);
                image.setVisibility(View.VISIBLE);
               final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressforpublicchat);


                final UniversalVideoView universalVideoView = (UniversalVideoView) view.findViewById(R.id.VideointhePublicChat);
                final UniversalMediaController universalMediaController = (UniversalMediaController) view.findViewById(R.id.media_controller);

                universalVideoView.setMediaController(universalMediaController);

                universalMediaController.hideError();

                universalVideoView.setVideoURI(Uri.parse(urlforvideo.get(i)));
                universalVideoView.getLayoutParams().height=10;
                universalVideoView.getLayoutParams().width=10;
                universalMediaController.getLayoutParams().height=10;
                universalMediaController.getLayoutParams().width=10;
                progressBar.setVisibility(View.VISIBLE);

                universalVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        universalVideoView.getLayoutParams().height=800;
                        universalVideoView.getLayoutParams().width=800;

                        universalMediaController.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                        universalMediaController.getLayoutParams().width=ViewGroup.LayoutParams.MATCH_PARENT;
                        mediaPlayer.seekTo(1);
                        progressBar.getLayoutParams().width= ViewGroup.LayoutParams.WRAP_CONTENT;
                        progressBar.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
                       // progressBar.setVisibility(View.VISIBLE);
                    }
                });


                universalVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        universalVideoView.getLayoutParams().height=10;
                        universalVideoView.getLayoutParams().width=10;
                        universalMediaController.getLayoutParams().height=10;
                        universalMediaController.getLayoutParams().width=10;
                        universalVideoView.seekTo(1);
                        progressBar.getLayoutParams().width=1;
                        progressBar.getLayoutParams().height=1;
                        return true;
                    }
                });






            Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.newanim);
            view.startAnimation(animation);
            messages.notify();



                //simpleExoPlayer.release();
                return view;



            }catch (Exception e)
            {

            }



            return view;
        }



    }



}