package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingapp.adapters.recipeItemAdapter;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.RecipeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.bakingapp.data.RecipeData.extractRecipesFromJson;
import static com.example.android.bakingapp.data.RecipeData.loadJSONFromAsset;

public class MainActivity extends AppCompatActivity implements recipeItemAdapter.RecipeItemAdapterListener{

    @BindView(R.id.phone_recycler_view) RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private recipeItemAdapter mAdapter;
    private ArrayList<RecipeItem> mRecipeData;
    private static final String PARCELLED_RECIPE_ITEM = "parcelled_recipe_item";
    private boolean mIsTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //set the appropriate layout manager depending on screen size
        if(findViewById(R.id.tablet_main_screen) != null){
            mIsTwoPane = true;
            //for tablets, use grid layout manager
            mLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mIsTwoPane = false;
            //for phones, use linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //get recipe Items to set to adapter
        mRecipeData = extractRecipesFromJson(loadJSONFromAsset(this));

        //set the found recipe data to the adapter
        mAdapter = new recipeItemAdapter(this, mRecipeData, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickMethod(RecipeItem recipeItem, int position) {
        //on clicking an item in the recycler view, open new activity and pass recipeItem
        Intent newIntent = new Intent(this, RecipeStepsActivity.class);
        newIntent.putExtra(PARCELLED_RECIPE_ITEM, recipeItem);
        startActivity(newIntent);
    }

}
