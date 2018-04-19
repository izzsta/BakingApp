package com.example.android.bakingapp.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by izzystannett on 14/04/2018.
 */

public class VideoFragment extends Fragment {

    private long playbackPosition;
    private boolean playbackReady = true;
    private int currentWindow;
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private static final String PLAYER_POSITION = "playback_position";
    private static final String PLAYBACK_READY = "playback_ready";
    private String exampleVideo1 = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
    private String exampleVideo2 = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4";


    public VideoFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //set up views
        View rootView = inflater.inflate(R.layout.fragment_video_player, container, false);
        simpleExoPlayerView = rootView.findViewById(R.id.exo_player_view);

        //if video has already been started, pick up from where it left off
        if (savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong(PLAYER_POSITION);
            playbackReady = savedInstanceState.getBoolean(PLAYBACK_READY);
        }

        //TODO: put a whole list of videos in the ExoPlayer
        //initialise ExoPlayer

        initializeExoPlayer(Uri.parse(exampleVideo1), Uri.parse(exampleVideo2));

        return rootView;
    }

    //initialise ExoPlayer
    public void initializeExoPlayer(Uri firstUri, Uri secondUri){
            if(mSimpleExoPlayer == null){
                //create an instance of the ExoPlayer
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                simpleExoPlayerView.setPlayer(mSimpleExoPlayer);

                //prepare the mediasource
                String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                //TODO: make this a loop, to allow looping through all videos
                MediaSource firstMediaSource = new ExtractorMediaSource(firstUri, new DefaultDataSourceFactory(getContext(),
                        userAgent), new DefaultExtractorsFactory(), null, null);
                MediaSource secondMediaSource = new ExtractorMediaSource(secondUri, new DefaultDataSourceFactory(getContext(),
                        userAgent), new DefaultExtractorsFactory(), null, null);
                ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource(firstMediaSource, secondMediaSource);
                mSimpleExoPlayer.prepare(concatenatingMediaSource);
                mSimpleExoPlayer.setPlayWhenReady(playbackReady);
                mSimpleExoPlayer.seekTo(currentWindow, playbackPosition);
            }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save state so that upon rotation, the video doesn't restart
        if(mSimpleExoPlayer != null){
            playbackPosition = mSimpleExoPlayer.getCurrentPosition();
            playbackReady = mSimpleExoPlayer.getPlayWhenReady();
            currentWindow = mSimpleExoPlayer.getCurrentWindowIndex();
        }
        outState.putLong(PLAYER_POSITION, playbackPosition);
        outState.putBoolean(PLAYBACK_READY, playbackReady);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void releasePlayer(){
        if(mSimpleExoPlayer != null){
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }
}
