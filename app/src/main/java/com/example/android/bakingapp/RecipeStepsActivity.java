package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.android.bakingapp.adapters.recipeStepsAdapter;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.ui.InstructionDetailFragment;
import com.example.android.bakingapp.ui.RecipeStepsFragment;
import com.example.android.bakingapp.ui.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.onStepClickedListener {

    private RecipeItem mParcelledRecipeItem;
    private boolean mIsTwoPane;
    private FragmentManager fragmentManager;
    private Steps mSelectedStep;
    private static final String PARCELLED_RECIPE_ITEM = "parcelled_recipe_item";
    private static final String PARCELLED_RECIPE_TO_STEP_FRAGMENT = "parcelled_recipe_to_step";
    private static final String BUNDLED_STEPS_TO_DETAIL_ACTIVITY = "bundles_steps_to_detail";
    private static final String STEP_INDEX = "selected_step_index";
    private static final String LIST_OF_STEPS = "list_of_steps";
    private static final String STEP_TO_FRAGMENT = "step_to_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent receivedIntent = getIntent();
        mParcelledRecipeItem = receivedIntent.getParcelableExtra(PARCELLED_RECIPE_ITEM);

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        //set arguments for fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELLED_RECIPE_TO_STEP_FRAGMENT, mParcelledRecipeItem);
        stepsFragment.setArguments(bundle);
        //add fragment to layout
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.steps_list_container, stepsFragment)
                .commit();

        //TODO: sort out onSavedInstance state to avoid this occuring upon rotation
        List<Steps> allSteps = mParcelledRecipeItem.getRecipeSteps();
        mSelectedStep = allSteps.get(0);

        if(findViewById(R.id.tablet_detail_layout) != null){
            mIsTwoPane = true;

            if(savedInstanceState == null){
                VideoFragment videoFragment = new VideoFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.video_container, videoFragment)
                        .commit();
            }
            InstructionDetailFragment instructionDetailFragment = new InstructionDetailFragment();
            //send received information to fragments
            Bundle bundleInstructionsFragment = new Bundle();
            bundleInstructionsFragment.putParcelable(STEP_TO_FRAGMENT, mSelectedStep);
            instructionDetailFragment.setArguments(bundleInstructionsFragment);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.detailed_instructions_container, instructionDetailFragment)
                    .commit();
        }

    }

    @Override
    public void onStepClicked(List<Steps> stepsToRecipe, int position) {
        if (mIsTwoPane){
            mSelectedStep = stepsToRecipe.get(position);
            InstructionDetailFragment instructionDetailFragment = new InstructionDetailFragment();

            //send received information to fragments
            Bundle bundleInstructionsFragment = new Bundle();
            bundleInstructionsFragment.putParcelable(STEP_TO_FRAGMENT, mSelectedStep);
            instructionDetailFragment.setArguments(bundleInstructionsFragment);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.detailed_instructions_container, instructionDetailFragment)
                    .commit();
        } else {
        Intent openRecipeDetailsIntent = new Intent(this, RecipeDetailedPhone.class);
        //put necessary info through the intent
        Bundle stepArgs = new Bundle();
        stepArgs.putParcelableArrayList(LIST_OF_STEPS, new ArrayList<Parcelable>(stepsToRecipe));
        openRecipeDetailsIntent.putExtra(BUNDLED_STEPS_TO_DETAIL_ACTIVITY, stepArgs);
        openRecipeDetailsIntent.putExtra(STEP_INDEX, position);
        startActivity(openRecipeDetailsIntent);
    }}
}
