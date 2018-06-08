package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.model.RecipeItem;

import com.example.android.bakingapp.fragments.InstructionDetailFragment;
import com.example.android.bakingapp.fragments.RecipeStepsFragment;
import com.example.android.bakingapp.fragments.VideoFragment;
import com.example.android.bakingapp.utils.Constants;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.onStepClickedListener {

    private RecipeItem mParcelledRecipeItem;
    private boolean mIsTwoPane;
    private Bundle mRecipeBundle;
    private FragmentManager fragmentManager;
    private int mStepIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        //set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            //  mRecipeBundle = savedInstanceState.getBundle(Constants.SAVED_RECIPE_ITEM);
            //} else {
            //get the recipe item received from previous activity and set to bundle
            Intent receivedIntent = getIntent();
            mParcelledRecipeItem = receivedIntent.getParcelableExtra(Constants.PARCELLED_RECIPE_ITEM);
            mRecipeBundle = createBundle(mParcelledRecipeItem, mStepIndex);

            //upon opening activity, open RecipeStepsFragment with selected Recipe Item
            RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
            //set argument to pass to fragment
            stepsFragment.setArguments(mRecipeBundle);
            //add fragment to layout
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_list_container, stepsFragment)
                    .commit();


            //if this is a two pane layout, add a video Fragment and instructions fragment too
            if (findViewById(R.id.tablet_detail_layout) != null) {
                mIsTwoPane = true;

                if (savedInstanceState == null) {
                    //if the video hasn't already started, create and add a new video fragment
                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setArguments(mRecipeBundle);
                    fragmentManager.beginTransaction()
                            .add(R.id.video_container, videoFragment)
                            .commit();
                }

                InstructionDetailFragment instructionDetailFragment = new InstructionDetailFragment();
                //send recipe item to fragments
                instructionDetailFragment.setArguments(mRecipeBundle);
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.detailed_instructions_container, instructionDetailFragment)
                        .commit();
            }
        }
    }

    //override the interface in the RecipeDetailsFragment, and define actions for the received parameters
    @Override
    public void onStepClicked(RecipeItem recipeItem, int position) {
        if (mIsTwoPane) {
            //if we're using a tablet, clicking a step changes the content of the instructions fragment
            mStepIndex = position;

            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(createBundle(mParcelledRecipeItem, mStepIndex));
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, videoFragment)
                    .commit();

            InstructionDetailFragment instructionDetailFragment = new InstructionDetailFragment();
            instructionDetailFragment.setArguments(createBundle(mParcelledRecipeItem, mStepIndex));
            fragmentManager.beginTransaction()
                    .replace(R.id.detailed_instructions_container, instructionDetailFragment)
                    .commit();
        } else {
            //if we're using a phone, open new activity and send recipe to it
            Intent openRecipeDetailsIntent = new Intent(this, RecipeDetailedPhone.class);
            openRecipeDetailsIntent.putExtra(Constants.RECIPE_BUNDLE_TO_DETAIL,
                    createBundle(recipeItem, position));
            startActivity(openRecipeDetailsIntent);
        }
    }

    public Bundle createBundle(RecipeItem bRecipeItem, int mStepIndex){
        Bundle newBundle = new Bundle();
        newBundle.putParcelable(Constants.PARCELLED_RECIPE_ITEM, bRecipeItem);
        newBundle.putInt(Constants.STEP_INDEX, mStepIndex);
        return newBundle;
    }

}
