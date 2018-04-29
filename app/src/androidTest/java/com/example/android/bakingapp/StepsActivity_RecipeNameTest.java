package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.ui.RecipeStepsFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by izzystannett on 28/04/2018.
 */

@RunWith(AndroidJUnit4.class)

public class StepsActivity_RecipeNameTest {

    //rule for functional testing of one activity
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_PassesRecipeNameToNextActivity() {
        onView(withId(R.id.phone_recycler_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recipe_name_tv)).check(matches(withText("Nutella Pie")));
    }


}

