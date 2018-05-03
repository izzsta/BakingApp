package com.example.android.bakingapp.fragments;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.bakingapp.utils.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by izzystannett on 14/04/2018.
 */

public class VideoFragment extends Fragment {

    private static final String PLAYER_POSITION = "playback_position";
    private static final String PLAYBACK_READY = "playback_ready";
    private long playbackPosition;
    private boolean playbackReady = true;
    private int currentWindow;
    private int mStepIndex;
    private RecipeItem mRecipeItem;
    private Step mStepSelected;
    private List<Step> mListOfSteps;
    @Nullable private String mVideoString;
    @Nullable
    private String mImageString;
    @Nullable private String mThumbnailString;
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private ImageView placeholderImageView;

    public VideoFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //set up views
        View rootView = inflater.inflate(R.layout.fragment_video_player, container, false);
        simpleExoPlayerView = rootView.findViewById(R.id.exo_player_view);
        placeholderImageView = rootView.findViewById(R.id.placeholder_image_view);

        //if video has already been started, pick up from where it left off
        if (savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong(PLAYER_POSITION);
            playbackReady = savedInstanceState.getBoolean(PLAYBACK_READY);
        }

        Bundle bundle = this.getArguments();
        if(bundle != null){
            mStepIndex = bundle.getInt(Constants.STEP_INDEX);
            mRecipeItem = bundle.getParcelable(Constants.PARCELLED_RECIPE_ITEM);
        }

        if(mRecipeItem != null) {
            mListOfSteps = mRecipeItem.getSteps();
            mStepSelected = mListOfSteps.get(mStepIndex);
            mImageString = mRecipeItem.getImage();
            mThumbnailString = mStepSelected.getThumbnailURL();
            mVideoString = mStepSelected.getVideoURL();
        }

        videoOrImageDisplay(mImageString, mThumbnailString, mVideoString);

        return rootView;
    }

    public void videoOrImageDisplay(String image, String thumbnail, String videoUrl) {
        if (videoUrl.trim().length() != 0){

            simpleExoPlayerView.setVisibility(View.VISIBLE);
            placeholderImageView.setVisibility(View.GONE);

            initializeExoPlayer(Uri.parse(videoUrl));

        } else if (image.trim().length() != 0) {

            simpleExoPlayerView.setVisibility(View.GONE);
            placeholderImageView.setVisibility(View.VISIBLE);

            Picasso.with(getContext())
                    .load(image)
                    .placeholder(R.drawable.cupcake)
                    .error(R.drawable.cupcake)
                    .into(placeholderImageView);

        } else if (thumbnail.trim().length() != 0){

            simpleExoPlayerView.setVisibility(View.GONE);
            placeholderImageView.setVisibility(View.VISIBLE);

            Picasso.with(getContext())
                    .load(thumbnail)
                    .placeholder(R.drawable.cupcake)
                    .error(R.drawable.cupcake)
                    .into(placeholderImageView);

        } else {

            simpleExoPlayerView.setVisibility(View.GONE);
            placeholderImageView.setVisibility(View.VISIBLE);

            placeholderImageView.setImageResource(R.drawable.cupcake);
        }
    }

    //initialise ExoPlayer
    public void initializeExoPlayer(Uri firstUri){
            if(mSimpleExoPlayer == null){
                //create an instance of the ExoPlayer
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                simpleExoPlayerView.setPlayer(mSimpleExoPlayer);

                //prepare the mediasource
                String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                MediaSource firstMediaSource = new ExtractorMediaSource(firstUri, new DefaultDataSourceFactory(getContext(),
                        userAgent), new DefaultExtractorsFactory(), null, null);
                mSimpleExoPlayer.prepare(firstMediaSource);
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
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
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
