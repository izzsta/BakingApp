package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by izzystannett on 10/04/2018.
 */

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ViewHolder> {

    private ArrayList<RecipeItem> mListOfRecipes;
    private Context mContext;
    private RecipeItemAdapterListener mClickHandler;

    //construct a constructor that takes a list of recipeItems
    public RecipeItemAdapter(Context c, ArrayList<RecipeItem> listOfRecipes, RecipeItemAdapterListener clickHandler) {
        mListOfRecipes = listOfRecipes;
        mContext = c;
        mClickHandler = clickHandler;
    }

    @Override
    public RecipeItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_view, parent,
                false);
        //pass the view to the ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeItemAdapter.ViewHolder holder, int position) {
        RecipeItem recipe = mListOfRecipes.get(position);
        String recipeName = recipe.getName();
        holder.mRecipeItemTv.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        return mListOfRecipes.size();
    }

    public void setRecipesToAdapter(ArrayList<RecipeItem> foundRecipeList){
        mListOfRecipes = foundRecipeList;
        notifyDataSetChanged();
    }

    //create onClickListener interface
    public interface RecipeItemAdapterListener {
        void onClickMethod(RecipeItem recipeItem, int position);
    }

    //create viewholder class
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_item_tv)
        TextView mRecipeItemTv;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            RecipeItem recipeItem;
            int adapterPosition = getAdapterPosition();
            recipeItem = mListOfRecipes.get(adapterPosition);
            mClickHandler.onClickMethod(recipeItem, adapterPosition);
        }
    }
}
