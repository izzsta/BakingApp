package com.example.android.bakingapp.fragments;

import android.content.Context;
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

import com.example.android.bakingapp.utils.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsAdapter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Step;

import java.util.List;


/**
 * Created by izzystannett on 11/04/2018.
 */

public class RecipeStepsFragment extends Fragment implements
        RecipeStepsAdapter.RecipeStepsAdapterListener {

    private static String LOG_TAG = "Recipe Steps Fragment";
    private RecipeItem mRecipeItem;
    private List<Step> mSteps;
    private String mRecipeName;
    private List<Ingredient> mIngredients;
    private RecyclerView mRecyclerView;
    private TextView mRecipeNameTv;
    private TextView mIngredientsTv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeStepsAdapter mAdapter;
    private onStepClickedListener mCallback;

    //empty constructor for creating the Fragment
    public RecipeStepsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the recipe steps fragment xml
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        //get the Bundled recipe item from the MainActivity
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRecipeItem = bundle.getParcelable(Constants.PARCELLED_RECIPE_ITEM);
        }

        //set up Recipe Name and Ingredients text views
        mRecipeNameTv = rootView.findViewById(R.id.recipe_name_tv);
        mIngredientsTv = rootView.findViewById(R.id.ingredients_tv);
        //get recipe name and ingredients from received Recipe Item and reformat them
        mRecipeName = mRecipeItem.getName();
        mIngredients = mRecipeItem.getIngredients();
        //set recipe name to text view
        mRecipeNameTv.setText(mRecipeName);
        //create string to set ingredients text view to
        StringBuilder ingredientsToDisplay = new StringBuilder();

        for (int i = 0; i<mIngredients.size(); i++){
            Ingredient passedIngredient = mIngredients.get(i);
            String ingredientDetail = String.valueOf(passedIngredient.getQuantity()) + " " +
                    passedIngredient.getMeasure() + " " + passedIngredient.getIngredient() + "\n";
            ingredientsToDisplay.append(ingredientDetail);
        }
        //set all ingredients to the text view
        mIngredientsTv.setText(ingredientsToDisplay.toString());

        //set the fragment's recycler view to a linear layout manager
        mRecyclerView = rootView.findViewById(R.id.steps_list_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set recipe steps to adapter
        mSteps = mRecipeItem.getSteps();
        mAdapter = new RecipeStepsAdapter(getContext(), mRecipeItem, this);
        mAdapter.setStepsForNextView(mSteps);
        //set adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);

        //return the inflated, populated view
        return rootView;
    }

    //override the recycler view's interface
    @Override
    public void onClickMethod(RecipeItem recipeItem, int position) {
        //pass the arguments to this fragment's interface
        mCallback.onStepClicked(recipeItem, position);
    }

    //check that the supporting activity has this fragment's interface implemented upon attachment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //check that th
        try {
            mCallback = (onStepClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    //define a listener interface, which has the same parameters as the recycler view's interface
    public interface onStepClickedListener {
        void onStepClicked(RecipeItem recipeItem, int position);
    }
}
