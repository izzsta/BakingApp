package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.ui.InstructionDetailFragment;
import com.example.android.bakingapp.ui.VideoFragment;

import java.util.List;

public class RecipeDetailedPhone extends AppCompatActivity {

    private static final String BUNDLED_STEPS_TO_DETAIL_ACTIVITY = "bundles_steps_to_detail";
    private static final String STEP_TO_FRAGMENT = "step_to_fragment";
    private static final String STEP_INDEX = "selected_step_index";
    private static final String LIST_OF_STEPS = "list_of_steps";
    private List<Steps> mListOfSteps;
    private Steps mSelectedStep;
    private int mStepNumber;
    private Bundle bundleToStepsFragment;
    private FragmentManager fragmentManager;
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detailed_phone);

        //get information from intent
        Intent receivedIntent = getIntent();
        Bundle bundledSteps = receivedIntent.getBundleExtra(BUNDLED_STEPS_TO_DETAIL_ACTIVITY);
        mListOfSteps = bundledSteps.getParcelableArrayList(LIST_OF_STEPS);
        mStepNumber = receivedIntent.getIntExtra(STEP_INDEX, 0);
        mSelectedStep = mListOfSteps.get(mStepNumber);

        fragmentManager = getSupportFragmentManager();

        //if activity is newly created, add new fragments
        if (savedInstanceState == null) {

            if (findViewById(R.id.landscape_phone) != null) {

                isLandscape = true;

                VideoFragment videoFragment = new VideoFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.video_container, videoFragment)
                        .commit();

            } else {
                isLandscape = false;

                VideoFragment videoFragment = new VideoFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.video_container, videoFragment)
                        .commit();
            }

            //create new instruction fragment and send appropriate info to it
            //TODO: perhaps send a list of steps to it, to allow clicking 'next'
            InstructionDetailFragment instructionDetailFragment = new InstructionDetailFragment();
            //send received information to fragments
            bundleToStepsFragment = new Bundle();
            bundleToStepsFragment.putParcelable(STEP_TO_FRAGMENT, mSelectedStep);
            instructionDetailFragment.setArguments(bundleToStepsFragment);

            fragmentManager.beginTransaction()
                    .add(R.id.detailed_instructions_container, instructionDetailFragment)
                    .commit();

            //set up button functionality
            if (!isLandscape) {
                Button button = findViewById(R.id.next_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: make this work, condense this code, make it highlight a different item in the steps list
                        if (mStepNumber < mListOfSteps.size()) {
                            mStepNumber++;
                            mSelectedStep = mListOfSteps.get(mStepNumber);
                            bundleToStepsFragment.putParcelable(STEP_TO_FRAGMENT, mSelectedStep);
                            InstructionDetailFragment newInstructionFragment = new InstructionDetailFragment();
                            newInstructionFragment.setArguments(bundleToStepsFragment);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.detailed_instructions_container, newInstructionFragment)
                                    .commit();
                        } else {
                            mStepNumber = 0;
                            mSelectedStep = mListOfSteps.get(mStepNumber);
                            bundleToStepsFragment.putParcelable(STEP_TO_FRAGMENT, mSelectedStep);
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
    }
}


