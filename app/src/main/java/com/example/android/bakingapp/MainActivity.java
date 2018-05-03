package com.example.android.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.adapters.RecipeItemAdapter;
import com.example.android.bakingapp.data.App;
import com.example.android.bakingapp.data.InternetConnectionListener;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.utils.Constants;
import com.example.android.bakingapp.utils.SimpleIdlingResource;
import com.example.android.bakingapp.widget.BakingAppWidgetProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements RecipeItemAdapter.RecipeItemAdapterListener, InternetConnectionListener {

    @BindView(R.id.phone_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.no_internet_connection)
    TextView mNoInternetTv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeItemAdapter mAdapter;
    private ArrayList<RecipeItem> mRecipeData;
    private boolean mIsTwoPane = false;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getIdlingResource();

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

        //set layout manager to recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //set the found recipe data to the adapter
        mAdapter = new RecipeItemAdapter(this, new ArrayList<RecipeItem>(0), this);
        mRecyclerView.setAdapter(mAdapter);

        //load data for adapter
        ((App) getApplication()).setInternetConnectionListener(this);
        loadAnswers();
    }


    //method for loading data via Retrofit for the Adapter
    public void loadAnswers(){
        mIdlingResource.setIdleState(false);
        ((App) getApplication()).getRecipeService().getAnswers()
            .enqueue(new Callback<ArrayList<RecipeItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeItem>> call, Response<ArrayList<RecipeItem>> response) {
                if(response.isSuccessful()) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mNoInternetTv.setVisibility(View.GONE);

                    mRecipeData = response.body();
                    mAdapter.setRecipesToAdapter(mRecipeData);
                    mIdlingResource.setIdleState(true);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", "failed with status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeItem>> call, Throwable t) {
                Log.d("MainActivity", "error loading from API" + t.getMessage());
            }
    });
    }

    @Override
    public void onInternetUnavailable() {
        mRecyclerView.setVisibility(View.GONE);
        mNoInternetTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickMethod(RecipeItem recipeItem, int position) {
        //on clicking an item in the recycler view, open new activity and pass recipeItem
        Intent newIntent = new Intent(this, RecipeStepsActivity.class);
        newIntent.putExtra(Constants.PARCELLED_RECIPE_ITEM, recipeItem);
        startActivity(newIntent);

        //sends recipe to widget
        Intent intent = new Intent(this, BakingAppWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(Constants.RECIPE_TO_WIDGET, recipeItem);
        sendBroadcast(intent);
    }


}
