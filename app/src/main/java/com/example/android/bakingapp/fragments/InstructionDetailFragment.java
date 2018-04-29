package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.utils.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Step;

import java.util.List;

/**
 * Created by izzystannett on 14/04/2018.
 */

public class InstructionDetailFragment extends Fragment {

    private static Step mStepSelected;
    private static RecipeItem mRecipeItem;
    private List<Step> mListOfSteps;
    private static int mStepIndex = 0;

    public InstructionDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the recipe steps fragment
        View rootView = inflater.inflate(R.layout.fragment_instruction_detail, container, false);

        //get bundled info from Activity
        Bundle bundle = this.getArguments();
        if(bundle != null){
            mStepIndex = bundle.getInt(Constants.STEP_INDEX);
            mRecipeItem = bundle.getParcelable(Constants.PARCELLED_RECIPE_ITEM);
        }
        if(mRecipeItem != null) {
            mListOfSteps = mRecipeItem.getSteps();
            mStepSelected = mListOfSteps.get(mStepIndex);
        }

        TextView detailedInstructionsView = rootView.findViewById(R.id.detailed_instructions_tv);
        detailedInstructionsView.setText(mStepSelected.getDescription());

        return rootView;
    }
}
