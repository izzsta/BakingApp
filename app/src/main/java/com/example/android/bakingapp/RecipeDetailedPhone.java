package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.InstructionDetailFragment;
import com.example.android.bakingapp.ui.VideoFragment;

import java.util.List;

public class RecipeDetailedPhone extends AppCompatActivity {

    private RecipeItem mRecipeItem;
    private List<Step> mListOfSteps;
    private Step mSelectedStep;
    private int mStepIndex;
    private Bundle bundleToStepsFragment;
    private FragmentManager fragmentManager;
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detailed_phone);

        //get information from intent
        Intent receivedIntent = getIntent();
        Bundle receivedBundle = receivedIntent.getExtras();

        if(receivedBundle != null) {
            mRecipeItem = receivedBundle.getParcelable(Constants.PARCELLED_RECIPE_ITEM);
            mStepIndex = receivedBundle.getInt(Constants.STEP_INDEX, 0);
        }
        mListOfSteps = mRecipeItem.getSteps();
        mSelectedStep = mListOfSteps.get(mStepIndex);

        fragmentManager = getSupportFragmentManager();

        //if activity is newly created, add new fragments
        if (savedInstanceState == null) {

            if (findViewById(R.id.landscape_phone) != null) {

                isLandscape = true;

                VideoFragment videoFragment = new VideoFragment();
                videoFragment.setArguments(createBundle(mRecipeItem, mStepIndex));
                fragmentManager.beginTransaction()
                        .add(R.id.video_container, videoFragment)
                        .commit();

            } else {

                isLandscape = false;

                VideoFragment videoFragment = new VideoFragment();
                videoFragment.setArguments(createBundle(mRecipeItem, mStepIndex));
                fragmentManager.beginTransaction()
                        .add(R.id.video_container, videoFragment)
                        .commit();

                //create new instruction fragment and send appropriate info to it
                InstructionDetailFragment instructionDetailFragment = new InstructionDetailFragment();
                instructionDetailFragment.setArguments(createBundle(mRecipeItem, mStepIndex));
                fragmentManager.beginTransaction()
                        .add(R.id.detailed_instructions_container, instructionDetailFragment)
                        .commit();

            }}

            //set up button functionality
            if (!isLandscape) {
                Button button = findViewById(R.id.next_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: make this work, condense this code, make it highlight a different item in the steps list
                        if (mStepIndex < mListOfSteps.size()) {
                            mStepIndex++;
                            mSelectedStep = mListOfSteps.get(mStepIndex);
                            bundleToStepsFragment.putParcelable(Constants.STEP_TO_FRAGMENT, mSelectedStep);
                            InstructionDetailFragment newInstructionFragment = new InstructionDetailFragment();
                            newInstructionFragment.setArguments(bundleToStepsFragment);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.detailed_instructions_container, newInstructionFragment)
                                    .commit();
                        } else {
                            mStepIndex = 0;
                            mSelectedStep = mListOfSteps.get(mStepIndex);
                            bundleToStepsFragment.putParcelable(Constants.STEP_TO_FRAGMENT, mSelectedStep);
                            InstructionDetailFragment newInstructionFragment = new InstructionDetailFragment();
                            newInstructionFragment.setArguments(bundleToStepsFragment);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.detailed_instructions_container, newInstructionFragment)
                                    .commit();
                        }
                    }
                });
            }
        }

    public Bundle createBundle(RecipeItem recipeItem, int stepIndex) {
        Bundle thisBundle = new Bundle();
        thisBundle.putParcelable(Constants.PARCELLED_RECIPE_ITEM, recipeItem);
        thisBundle.putInt(Constants.STEP_INDEX, stepIndex);
        return thisBundle;
    }
}


