package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.ui.RecipeStepsFragment;

public class RecipeDetailsActivity extends AppCompatActivity {

    private RecipeItem mParecelledRecipeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent receivedIntent = getIntent();
        //TODO: parcellable RecipeItem
        // receivedIntent.getExtras();

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setmRecipeItem(mParcelledRecipeItem);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.steps_list_container, stepsFragment)
                .commit();

    }
}
