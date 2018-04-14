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
import com.example.android.bakingapp.ui.RecipeStepsFragment;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.onStepClickedListener {

    private RecipeItem mParcelledRecipeItem;
    private static final String PARCELLED_RECIPE_ITEM = "parcelled_recipe_item";
    private static final String PARCELLED_RECIPE_TO_STEP_FRAGMENT = "parcelled_recipe_to_step";
    private static final String BUNDLED_STEPS_TO_DETAIL_ACTIVITY = "bundles_steps_to_detail";
    private static final String STEP_INDEX = "selected_step_index";
    private static final String LIST_OF_STEPS = "list_of_steps";


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
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.steps_list_container, stepsFragment)
                .commit();

    }

    @Override
    public void onStepClicked(List<Steps> stepsToRecipe, int position) {
        Intent openRecipeDetailsIntent = new Intent(this, RecipeDetailedPhone.class);

        //put necessary info through the intent
        Bundle stepArgs = new Bundle();
        stepArgs.putParcelableArrayList(LIST_OF_STEPS, new ArrayList<Parcelable>(stepsToRecipe));
        openRecipeDetailsIntent.putExtra(BUNDLED_STEPS_TO_DETAIL_ACTIVITY, stepArgs);
        openRecipeDetailsIntent.putExtra(STEP_INDEX, position);
        startActivity(openRecipeDetailsIntent);
    }
}
