package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.recipeStepsAdapter;
import com.example.android.bakingapp.model.RecipeItem;


/**
 * Created by izzystannett on 11/04/2018.
 */

public class RecipeStepsFragment extends Fragment {

    //TODO: add a list of recipe Items, and a setter method for getting them, and a setter method for setting the index

    private RecipeItem mRecipeItem;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private recipeStepsAdapter mAdapter;

    //empty constructor for creating the Fragment
    public RecipeStepsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //method to inflate the appropriate xml layout for the fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        mRecyclerView = rootView.findViewById(R.id.steps_list_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new recipeStepsAdapter(getContext(), mRecipeItem);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public void setmRecipeItem(RecipeItem recipeItem){
        mRecipeItem = recipeItem;
    }
}
