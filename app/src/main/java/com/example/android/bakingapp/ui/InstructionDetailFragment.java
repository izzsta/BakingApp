package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Steps;

/**
 * Created by izzystannett on 14/04/2018.
 */

public class InstructionDetailFragment extends Fragment {

    private static Steps mStepSelected;
    private static final String STEP_TO_FRAGMENT = "step_to_fragment";

    public InstructionDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the recipe steps fragment
        View rootView = inflater.inflate(R.layout.fragment_instruction_detail, container, false);

        //get bundled info from Activity
        Bundle bundle = this.getArguments();
        if(bundle != null){
            mStepSelected = bundle.getParcelable(STEP_TO_FRAGMENT);
        }

        TextView detailedInstructionsView = rootView.findViewById(R.id.detailed_instructions_tv);
        detailedInstructionsView.setText(mStepSelected.getDescription());

        return rootView;
    }
}
