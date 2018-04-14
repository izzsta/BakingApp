package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.ui.InstructionDetailFragment;

import java.util.List;

public class RecipeDetailedPhone extends AppCompatActivity {

    private List<Steps> mListOfSteps;
    private Steps mSelectedStep;
    private int mStepNumber;
    private Bundle bundleToStepsFragment;
    private InstructionDetailFragment instructionDetailFragment;
    private FragmentManager fragmentManager;
    private static final String BUNDLED_STEPS_TO_DETAIL_ACTIVITY = "bundles_steps_to_detail";
    private static final String STEP_TO_FRAGMENT = "step_to_fragment";
    private static final String STEP_INDEX = "selected_step_index";
    private static final String LIST_OF_STEPS = "list_of_steps";

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

        InstructionDetailFragment instructionDetailFragment = new InstructionDetailFragment();

        //send received information to fragments
        bundleToStepsFragment = new Bundle();
        bundleToStepsFragment.putParcelable(STEP_TO_FRAGMENT, mSelectedStep);
        instructionDetailFragment.setArguments(bundleToStepsFragment);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.detailed_instructons_container, instructionDetailFragment)
                .commit();

        Button button = findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStepNumber++;
                mSelectedStep = mListOfSteps.get(mStepNumber);
                bundleToStepsFragment.putParcelable(STEP_TO_FRAGMENT, mSelectedStep);
                InstructionDetailFragment newInstructionFragment = new InstructionDetailFragment();
                newInstructionFragment.setArguments(bundleToStepsFragment);
                fragmentManager.beginTransaction()
                        .replace(R.id.detailed_instructons_container, newInstructionFragment)
                        .commit();

            }
        });
    }
}
