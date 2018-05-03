package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Step;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by izzystannett on 11/04/2018.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    private Context mContext;
    private RecipeItem mRecipeItem;
    private List<Step> mSteps;
    private RecipeStepsAdapterListener mClickHandler;

    //constructor
    public RecipeStepsAdapter(Context c, RecipeItem recipeItem,
                              RecipeStepsAdapterListener recipeStepsAdapterListener){
    mRecipeItem = recipeItem;
    mContext = c;
    mClickHandler = recipeStepsAdapterListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate layout to contain viewholders on creation
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_view,
                parent, false);
        //pass the view to the viewholder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //returns a step
        mSteps = mRecipeItem.getSteps();
        Step bindingStep = mSteps.get(position);
        //gets short description of step
        String bindingDescription = bindingStep.getShortDescription();
        //puts description to text view
        holder.stepsTv.setText(bindingDescription);
    }

    @Override
    public int getItemCount() {
        if (mSteps != null) {
            return mSteps.size();
        } else {
            return 0;
        }
    }

    public void setStepsForNextView(List<Step> selectedSteps){
        mSteps = selectedSteps;
        notifyDataSetChanged();
    }

    //create interface that requires onClick Method to be implemented
    public interface RecipeStepsAdapterListener {
        void onClickMethod(RecipeItem recipeItem, int position);
    }

    //create custom viewholder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.steps_tv)
        TextView stepsTv;

        //create ViewHolder constructor
        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClickMethod(mRecipeItem, adapterPosition);
        }
    }
}
