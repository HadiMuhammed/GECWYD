package com.ffc.emnet.ui.share;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ffc.emnet.ItemUploadActivity;
import com.ffc.emnet.PublicChatDatabase;
import com.ffc.emnet.R;
import com.ffc.emnet.Upload2;
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

                messages.setSmoothScrollbarEnabled(true);
                messages.canScrollVertically(-1);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(messagebox.getText() != null) {
                    Upload2 upload = new Upload2(messagebox.getText().toString(),"no uri","nothing","no uri",PublicChatDatabase.muser);
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
                    intent.putExtra("message",messagebox.getText());
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
                TextView username1 = (TextView) view.findViewById(R.id.usernameInThePublicChat);
                TextView message = (TextView) view.findViewById(R.id.messageInThePublicChat);
                final ImageView image = (ImageView) view.findViewById(R.id.ImageinthePublicChat);
              //  final SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.VideointhePublicChat);
               // final SimpleExoPlayer simpleExoPlayer;
                //BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
               // TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                //simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),trackSelector);
                //DefaultHttpDataSourceFactory dataSource = new DefaultHttpDataSourceFactory("Exoplayer");
                //ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                //MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(urlforvideo.get(i)),dataSource,extractorsFactory,null,null);
              //final MediaPlayer mp = new MediaPlayer();
               // simpleExoPlayerView.setPlayer(simpleExoPlayer);
               /// simpleExoPlayer.prepare(mediaSource);
               // simpleExoPlayer.setPlayWhenReady(false);
                //simpleExoPlayerView.getLayoutParams().width=10;
                //simpleExoPlayerView.getLayoutParams().height=10;
               // simpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);



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






            //   video.setVideoURI(Uri.parse(urlforvideo.get(i)));
              // video.setVisibility(View.VISIBLE);
            //   progressBar.setVisibility(View.VISIBLE);
                //   MediaController mediaController = new MediaController(getActivity());
              //     video.setMediaController(mediaController);
                //   mediaController.setAnchorView(video);
                  //     video.seekTo(1);
                    //   video.start();
              //  final View finalView = view;





             //   final MediaController mc = new MediaController(getActivity());
              //  final  CustomMediaController mc = new CustomMediaController(getContext(), video);
            //    mc.setMediaPlayer(video);
               // mc.setAnchorView(video);
          //      video.setMediaController(mc);
               // video.requestFocus();





              //  video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  ///  @Override
                ///    public void onCompletion(MediaPlayer mediaPlayer) {
                       // video.setVisibility(View.GONE);
                   // }
                //});
            //    video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
              //      @Override
                //    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                  //      video.setVisibility(View.GONE);
                    //    return true;
                   // }
               // });
               // video.seekTo(1);



                //view.clearAnimation();

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