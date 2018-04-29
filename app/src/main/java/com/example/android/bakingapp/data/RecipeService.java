package com.example.android.bakingapp.data;

import com.example.android.bakingapp.model.RecipeItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by izzystannett on 17/04/2018.
 */

public interface RecipeService {

    String URL = "http://go.udacity.com/";

    @GET("android-baking-app-json")
    Call<ArrayList<RecipeItem>> getAnswers();
}
