package com.example.android.fundamentalscapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_PRIMARY_KEY = "recipe_id";
    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //This needs to be set as the default have been changed as we are using custom toolbar settings
        //in this app. This will enable the toolbar to show th up arrow (which is what I want).
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        String tempTitle = intent.getStringExtra(EXTRA_RECIPE_PRIMARY_KEY);
        //Printout to prove that I received the title of the recipe from the intent and can then
        //use the getRecipe() method to retrieve the Recipe.
        Log.d("DetailActivity onCreate", tempTitle);

        //Assigning View widgets
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

                //And Here is where I can update the Widgets with the correct attributes.
                titleTextView.setText(recipe.getTitle());
                //Region and Meal attributes are stored as ints so they are converted to Strings to
                //be displayed into text views.
                regionTextView.setText(changeIntToRegion(recipe.getRegionOfOrigin()));
                mealTextView.setText(changeIntToMeal(recipe.getTypeOfMeal()));

                briefDescriptionTextView.setText(recipe.getBriefDescription());
                ingredientsTextView.setText(recipe.getIngredientsWithMeasurements());
                instructionsTextView.setText(recipe.getInstructions());
            }
        });

    }
    //Method to convert the int representation of the region to the text representation
    private String changeIntToRegion(int regionNum) {
        switch (regionNum) {
            case 0:
                return "North America";

            case 1:
                return "Central America";

            case 2:
                return "South America";

            case 3:
                return "Western Europe";

            case 4:
                return "Eastern Europe";

            case 5:
                return "Middle East";

            case 6:
                return "Western Asia";

            case 7:
                return "Eastern Asia";

            case 8:
                return "Oceania";

            default:
                return "no data";
        }
    }
    //Method to convert the int representation of the meal to the text representation
    private String changeIntToMeal(int mealNum) {
        switch (mealNum) {
                case 0:
                    return "Breakfast";
                case 1:
                    return "Lunch";
                case 2:
                    return "Supper";
                case 3:
                    return "Snack";
                default:
                    return "no data";
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // I can programmatically remove the add option like this >>> menu.removeItem(R.id.menu_add); >>> nice
        //I don't want the add or the clear all, I may add a delete this recipe option that will show in the menu instead of thh
        //context menu.
        menu.removeItem(R.id.menu_add);
        menu.removeItem(R.id.menu_clear_all);
        menu.removeItem(R.id.menu_settings);
        return true;
    }
    //Here is where I can add the intents for the dialogs and activities...
    //A dialog for add, an activity for settings (see the text), a dialog for about, and a dialog (for practice) or activity for feedback.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add: {
                //I will come back here and add the intent or whatever that is needed to start the dialog.
                DialogFragment addRecipeDialog = new AddRecipeFragment();
                addRecipeDialog.show(getSupportFragmentManager(), "Add");
                return true; //no need for break statements as return statements also exit the switch block
            }
            case R.id.menu_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_about: {
                return true;
            }
            case R.id.menu_feedback: {
                return true;
            }
            case R.id.menu_clear_all: {
                mRecipeViewModel.deleteAll();
                return true;
            }
            default:
        }

        return super.onOptionsItemSelected(item);
    }


}
