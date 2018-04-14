package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by izzystannett on 11/04/2018.
 */

public class recipeStepsAdapter extends RecyclerView.Adapter<recipeStepsAdapter.ViewHolder> {

    private Context mContext;
    private List<Steps> mSteps;

    //constructor
    public recipeStepsAdapter(Context c, List<Steps> listOfSteps){
    mSteps = listOfSteps;
    mContext = c;
    }

    //create custom viewholder
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.steps_tv)
        TextView stepsTv;

        //create ViewHolder constructor
        private ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        //returns a step
            Steps bindingStep = mSteps.get(position);
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

    public void setStepsForNextView(List<Steps> selectedSteps){
        mSteps = selectedSteps;
        notifyDataSetChanged();
    }
}
