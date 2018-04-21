package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.RecipeItem;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static RecipeItem mRecipeItem = null;
    private static List<Ingredient> mIngredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetTitle;
        CharSequence widgetText;

        if(mRecipeItem == null){
            widgetTitle = context.getString(R.string.appwidget_no_recipe);
            widgetText = context.getString(R.string.appwidget_select_recipe);
        } else {
            widgetTitle = mRecipeItem.getName();

            //get ingredients in string format
            mIngredients = mRecipeItem.getIngredients();
            StringBuilder ingredientsForWidget = new StringBuilder();

            for (int i = 0; i<mIngredients.size(); i++){
                Ingredient passedIngredient = mIngredients.get(i);
                String ingredientDetail = String.valueOf(passedIngredient.getQuantity()) + " " +
                        passedIngredient.getMeasure() + " " + passedIngredient.getIngredient() + "\n";
                ingredientsForWidget.append(ingredientDetail);
            }
            widgetText = ingredientsForWidget.toString();
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        views.setTextViewText(R.id.appwidget_title, widgetTitle);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        //set Pending intent to open MainActivity upon clicking
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // This scrolls through all instances of a widget, and updates them
        //method called upon creation and upon timed-update as defined in the xml file
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mRecipeItem = intent.getParcelableExtra(Constants.RECIPE_TO_WIDGET);
        //TODO: receive recipe from the MainActivity, and send it as an extra to the IntentService
        //start IntentService
        Intent widgetIntentService = new Intent(context, BakingAppWidgetService.class);
        widgetIntentService.putExtra(Constants.RECIPE_FROM_MAINACTIVITY, mRecipeItem);
        context.startService(widgetIntentService);
    }

}

