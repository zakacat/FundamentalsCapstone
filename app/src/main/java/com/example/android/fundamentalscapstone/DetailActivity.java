package com.example.android.fundamentalscapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_PRIMARY_KEY = "recipe_id";
    private RecipeViewModel mRecipeViewModel;
    private Recipe mOpenRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        String tempTitle = intent.getStringExtra(EXTRA_RECIPE_PRIMARY_KEY);
        Log.d("tester", tempTitle);

        TextView titleTextView = findViewById(R.id.title_detail);
        TextView regionTextView = findViewById(R.id.region_detail);
        TextView mealTextView = findViewById(R.id.meal_detail);
        TextView briefDescriptionTextView = findViewById(R.id.brief_description_detail);
        TextView ingredientsTextView = findViewById(R.id.ingredients_detail);
        TextView instructionsTextView = findViewById(R.id.instructions_detail);

        //This observer for livedata is required because livedata will run data calls in the background and
        //calls to the database should not be run on the main thread. LiveData and its implementations are another
        //thing that I will need to take a bit of a deep dive into.
        mRecipeViewModel.getRecipe(tempTitle).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {

                titleTextView.setText(recipe.getTitle());
                regionTextView.setText(String.valueOf(recipe.getRegionOfOrigin()));
                mealTextView.setText(String.valueOf(recipe.getTypeOfMeal()));
                briefDescriptionTextView.setText(recipe.getBriefDescription());
                ingredientsTextView.setText(recipe.getIngredientsWithMeasurements());
                instructionsTextView.setText(recipe.getInstructions());
            }
        });




    }


}
