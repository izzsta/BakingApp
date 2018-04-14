package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by izzystannett on 10/04/2018.
 */

public class Ingredients implements Parcelable {

    private int mQuantity;
    private String mMeasure;
    private String mIngredientDescription;

    public Ingredients(int quantity, String measure, String ingredientDescription){
        mQuantity = quantity;
        mMeasure = measure;
        mIngredientDescription = ingredientDescription;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mIngredientDescription);
    }

    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int i) {
            return new Ingredients[i];
        }
    };

    public Ingredients(Parcel parcel) {
        mQuantity = parcel.readInt();
        mMeasure = parcel.readString();
        mIngredientDescription = parcel.readString();
    }
}
