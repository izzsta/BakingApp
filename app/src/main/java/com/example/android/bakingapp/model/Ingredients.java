package com.example.android.bakingapp.model;

/**
 * Created by izzystannett on 10/04/2018.
 */

public class Ingredients {

    private int mQuantity;
    private String mMeasure;
    private String mIngredientDescription;

    public Ingredients(int quantity, String measure, String ingredientDescription){
        quantity = mQuantity;
        measure = mMeasure;
        ingredientDescription = mIngredientDescription;
    }

    //getter and setter methods
    public int getQuantity(){
        return mQuantity;
    }

    public String getMeasure(){
        return mMeasure;
    }

    public String getIngredientDescription(){
        return mIngredientDescription;
    }

    public void setQuantity(int quantity){
        mQuantity = quantity;
    }

    public void setMeasure(String measure){
        mMeasure = measure;
    }

    public void setIngredientDescription(String ingredientDescription){
        mIngredientDescription = ingredientDescription;
    }
}
