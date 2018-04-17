package com.example.android.bakingapp.data;

import com.example.android.bakingapp.model.RecipeItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by izzystannett on 17/04/2018.
 */

public interface RecipeService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<RecipeItem>> getAnswers();
}
