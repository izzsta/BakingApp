package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.RecipeItem;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by izzystannett on 21/04/2018.
 */

public class BakingAppWidgetService extends IntentService{

    public BakingAppWidgetService(){
        super("BakingAppWidgetService");
    }

    public static final String ACTION_UPDATE_WIDGET = "com.example.android.bakingapp.action.update_update";
    public static final String RECIPE_ITEM_EXTRA_WIDGET = "recipe_item_extra_for_widget";

    //method that triggers the service to perform the update widget action
    public static void startActionUpdateWidget(Context context, RecipeItem recipeItem){
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(RECIPE_ITEM_EXTRA_WIDGET, recipeItem);
        context.startService(intent);
        }


    //onHandleIntent extracts action and handles each action seperately
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    if(intent != null){
        final String action = intent.getAction();
        if(ACTION_UPDATE_WIDGET.equals(action)){
            RecipeItem receivedRecipeItem = intent.getParcelableExtra(RECIPE_ITEM_EXTRA_WIDGET);
            handleUpdateWidget(receivedRecipeItem);
        }
    }
    }

    public void handleUpdateWidget(RecipeItem recipeItem){

        AppWidgetManager appWidgetManager  = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                BakingAppWidgetProvider.class));
        //update all widgets
        BakingAppWidgetProvider.updateBakingAppWidgets(this, appWidgetManager, recipeItem, appWidgetIds);
    }
}
