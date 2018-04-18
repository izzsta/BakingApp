package com.example.android.bakingapp.data;

/**
 * Created by izzystannett on 17/04/2018.
 */

public class ApiUtils {

   //public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public static final String BASE_URL = "http://go.udacity.com/";

    public static RecipeService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(RecipeService.class);
    }
}
