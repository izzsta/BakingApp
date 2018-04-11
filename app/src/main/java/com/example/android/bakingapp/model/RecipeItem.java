package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by izzystannett on 09/04/2018.
 */

public class RecipeItem implements Parcelable {

    private String mName;
    private List<Ingredients> mIngredients;
    private List<Steps> mRecipeSteps;

    public RecipeItem(String name, List<Ingredients> ingredients, List<Steps> recipeSteps){
        mName = name;
        mIngredients = ingredients;
        mRecipeSteps = recipeSteps;
    }
    //getter methods
    public String getName(){
        return mName;
    }

    public List<Ingredients> getIngredients(){
        return mIngredients;
    }

    public List<Steps> getRecipeSteps(){
        return mRecipeSteps;
    }

    //setter methods
    public void setName(String name){
        mName = name;
    }

    public void setIngredients(List<Ingredients> ingredients){
        mIngredients = ingredients;
    }

    public void setRecipeSteps(List<Steps> recipeSteps){
        mRecipeSteps = recipeSteps;
    }

    //parcellable methods

    public static final Creator CREATOR = new Creator() {
        @Override
        public RecipeItem createFromParcel(Parcel parcel) {
            return new RecipeItem(parcel);
        }

        @Override
        public RecipeItem[] newArray(int i) {
            return new RecipeItem[i];
        }
    };

    //override Parcelable methods
    private RecipeItem(Parcel in) {
        mName = in.readString();
        mIngredients = in.readList();
        mRecipeSteps = in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeList(mIngredients);
        parcel.writeList(mRecipeSteps);
    }
}
