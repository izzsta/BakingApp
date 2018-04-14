package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        //for phones, use linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //get recipe Items to set to adapter
       mRecipeData = extractRecipesFromJson(loadJSONFromAsset(this));

        // specify an adapter
        mAdapter = new recipeItemAdapter(this, mRecipeData, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickMethod(RecipeItem recipeItem, int position) {
        Intent newIntent = new Intent(this, RecipeStepsActivity.class);
        newIntent.putExtra("parcelled_recipeItem", recipeItem);
        startActivity(newIntent);
    }

}
