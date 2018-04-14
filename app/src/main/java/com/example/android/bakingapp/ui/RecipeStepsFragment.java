package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.recipeStepsAdapter;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Steps;

import java.util.List;

import butterknife.BindView;


/**
 * Created by izzystannett on 11/04/2018.
 */

public class RecipeStepsFragment extends Fragment {

    //TODO: add a list of recipe Items, and a setter method for getting them, and a setter method for setting the index

    private RecipeItem mRecipeItem;
    private List<Steps> mSteps;
    private List<Ingredients> mIngredients;
    private RecyclerView mRecyclerView;
    private TextView mIngredientsTv;
    private RecyclerView.LayoutManager mLayoutManager;
    private recipeStepsAdapter mAdapter;
    private static String PARCELLED_RECIPE = "parcelled_recipe";


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
        //inflate the recipe steps fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        //get the Bundled, selected recipe item from the MainActivity, and pass it to the stepsAdapter
        Bundle bundle = this.getArguments();
        if(bundle != null){
            mRecipeItem = bundle.getParcelable(PARCELLED_RECIPE);
        }
        //set up Ingredient's view
        mIngredientsTv = rootView.findViewById(R.id.ingredients_tv);
        mIngredients = mRecipeItem.getIngredients();
        StringBuilder ingredientsToDisplay = new StringBuilder();

        for (int i = 0; i<mIngredients.size(); i++){
            Ingredients passedIngredient = mIngredients.get(i);
            String ingredientDetail = String.valueOf(passedIngredient.getQuantity()) + " " +
                    passedIngredient.getMeasure() + " " + passedIngredient.getIngredientDescription() + "\n";
            ingredientsToDisplay.append(ingredientDetail);
        }

        mIngredientsTv.setText(ingredientsToDisplay.toString());

        //set the fragment's recycler view to a linear layout manager
        mRecyclerView = rootView.findViewById(R.id.steps_list_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set recipe steps to adapter
        mSteps = mRecipeItem.getRecipeSteps();
        mAdapter = new recipeStepsAdapter(getContext(), mSteps);
        mAdapter.setStepsForNextView(mSteps);
        //set adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);

        //return the inflated, populated view
        return rootView;
    }

    public void setmRecipeItem(RecipeItem recipeItem){
        mRecipeItem = recipeItem;
    }
}
