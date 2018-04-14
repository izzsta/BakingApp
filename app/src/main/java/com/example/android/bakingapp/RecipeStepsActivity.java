package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.android.bakingapp.adapters.recipeStepsAdapter;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.ui.RecipeStepsFragment;

public class RecipeStepsActivity extends AppCompatActivity implements
        recipeStepsAdapter.RecipeStepsAdapterListener {

    private RecipeItem mParcelledRecipeItem;
    private static String PARCELLED_RECIPE = "parcelled_recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent receivedIntent = getIntent();
        mParcelledRecipeItem = receivedIntent.getParcelableExtra("parcelled_recipeItem");

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        //set arguments for fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELLED_RECIPE, mParcelledRecipeItem);
        stepsFragment.setArguments(bundle);
        //add fragment to layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.steps_list_container, stepsFragment)
                .commit();

    }

    @Override
    public void onClickMethod(Steps step, int position) {
        Intent intent = new Intent(RecipeStepsActivity.this, RecipeDetailedPhone.class);
        String detailedDescription = step.getDescription();
        startActivity(intent);
        Toast.makeText(this, detailedDescription,Toast.LENGTH_SHORT);
    }
}
