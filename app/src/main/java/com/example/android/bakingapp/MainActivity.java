package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.adapters.recipeItemAdapter;
import com.example.android.bakingapp.data.ApiUtils;
import com.example.android.bakingapp.data.RecipeService;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.widget.BakingAppWidgetProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity implements recipeItemAdapter.RecipeItemAdapterListener{

    @BindView(R.id.phone_recycler_view) RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private recipeItemAdapter mAdapter;
    private ArrayList<RecipeItem> mRecipeData;
    private RecipeService mRecipeService;
    private boolean mIsTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecipeService = ApiUtils.getSOService();

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
        //mRecipeData = extractRecipesFromJson(loadJSONFromAsset(this));

        //set the found recipe data to the adapter
        mAdapter = new recipeItemAdapter(this, new ArrayList<RecipeItem>(0), this);
        mRecyclerView.setAdapter(mAdapter);

        loadAnswers();
    }


    //method for loading data via Retrofit for the Adapter
    public void loadAnswers(){
        mRecipeService.getAnswers().enqueue(new Callback<ArrayList<RecipeItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeItem>> call, Response<ArrayList<RecipeItem>> response) {
                if(response.isSuccessful()) {
                    mRecipeData = response.body();
                    mAdapter.setRecipesToAdapter(mRecipeData);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeItem>> call, Throwable t) {
                Log.d("MainActivity", "error loading from API" + t.getMessage());
            }
    });
    }

    @Override
    public void onClickMethod(RecipeItem recipeItem, int position) {
        //on clicking an item in the recycler view, open new activity and pass recipeItem
        Intent newIntent = new Intent(this, RecipeStepsActivity.class);
        newIntent.putExtra(Constants.PARCELLED_RECIPE_ITEM, recipeItem);
        startActivity(newIntent);

        //TODO: send the recipe to the widget
        Intent intent = new Intent(this, BakingAppWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(Constants.RECIPE_TO_WIDGET, recipeItem);
        sendBroadcast(intent);
    }

}
