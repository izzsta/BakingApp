package com.example.android.bakingapp.data;

import android.content.Context;
import android.util.Log;

import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.RecipeItem;
import com.example.android.bakingapp.model.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by izzystannett on 09/04/2018.
 */
//method to extract recipe info from the JSON file in the assets folder

public class RecipeData {

    private static final String LOG_TAG = RecipeData.class.getSimpleName();

    //get JSON in the form of a string (code taken from StackOverflow)
    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("recipe_JSON.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //parse JSON into RecipeItems
    public static ArrayList<RecipeItem> extractRecipesFromJson(String jsonString) {
        ArrayList<RecipeItem> recipeItems = new ArrayList<>();

        try {
            JSONArray baseJsonArray = new JSONArray(jsonString);

            String name;

            for (int i = 0; i < baseJsonArray.length(); i++) {

                List<Ingredients> listOfIngredients = new ArrayList<>();
                List<Steps> listOfSteps = new ArrayList<>();

                JSONObject recipeObject = baseJsonArray.getJSONObject(i);

                if (recipeObject.has("name")) {
                    name = recipeObject.getString("name");
                } else {
                    name = null;
                }

                Ingredients foundIngredient;

                if (recipeObject.has("ingredients")) {
                    JSONArray ingredientsArray = recipeObject.getJSONArray("ingredients");

                    for (int j = 0; j < ingredientsArray.length(); j++){

                        JSONObject ingredient = ingredientsArray.getJSONObject(j);

                        int quantity = ingredient.getInt("quantity");
                        String measure = ingredient.getString("measure");
                        String ingredientDescription = ingredient.getString("ingredient");

                        foundIngredient = new Ingredients(quantity, measure, ingredientDescription);
                        listOfIngredients.add(foundIngredient);
                    }
                } else {
                    foundIngredient = null;
                }

                Steps foundStep;

                if(recipeObject.has("steps")){
                    JSONArray stepsArray = recipeObject.getJSONArray("steps");

                    for(int k = 0; k < stepsArray.length(); k++){
                        JSONObject step = stepsArray.getJSONObject(k);

                        String shortDescription = step.getString("shortDescription");
                        String description = step.getString("description");
                        String videoUrl = step.getString("videoURL");

                        foundStep = new Steps(shortDescription, description, videoUrl);
                        listOfSteps.add(foundStep);
                    }
                } else {
                    foundStep = null;
                }

                //create new RecipeItem object and add to the Array List
               RecipeItem foundRecipe = new RecipeItem(name, listOfIngredients, listOfSteps);
                String foundName = foundRecipe.getName();
                List<Ingredients> foundIngredients = foundRecipe.getIngredients();
                List<Steps> foundSteps = foundRecipe.getRecipeSteps();
                Log.e(LOG_TAG, "Name = "+foundName +" Ingredients = "+ foundIngredients + " Steps = " + foundSteps);
                recipeItems.add(foundRecipe);

            }
            return recipeItems;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing movie item JSON results", e);
        }
        return null;
    }
}
