package com.example.android.fundamentalscapstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.net.UriCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.UriPermission;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.UriLoader;

import java.io.File;
import java.net.URI;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_PRIMARY_KEY = "recipe_id";
    public static final String EXTRA_REPLY = "com.example.android.fundamentalscapstone.extra.REPLY";
    private RecipeViewModel mRecipeViewModel;
    private String mImageResource, mBriefDescription;
    private Recipe mOpenRecipe;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private boolean mIsDelete = false;
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";




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
        ImageView imageView = findViewById(R.id.imageView_detail);
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
                if (recipe.getImageResource() != null) {
                    Glide.with(getApplicationContext()).load(recipe.getImageResource()).into(imageView);
                    mImageResource = recipe.getImageResource();
                } else {
                    Glide.with(getApplicationContext()).load(R.drawable.image_not_found).into(imageView);
                }
                //And Here is where I can update the Widgets with the correct attributes.
                titleTextView.setText(recipe.getTitle());
                //Region and Meal attributes are stored as ints so they are converted to Strings to
                //be displayed into text views.
                regionTextView.setText(changeIntToRegion(recipe.getRegionOfOrigin()));

                mealTextView.setText(changeIntToMeal(recipe.getTypeOfMeal()));

                briefDescriptionTextView.setText(recipe.getBriefDescription());
                mBriefDescription = "Summary:\n" + recipe.getBriefDescription() + "\n\n";

                ingredientsTextView.setText(recipe.getIngredientsWithMeasurements());

                instructionsTextView.setText(recipe.getInstructions());
                mOpenRecipe = recipe;
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
        menu.removeItem(R.id.menu_about);
        menu.removeItem(R.id.menu_feedback);
        return true;
    }

    //Here is where I can add the intents for the dialogs and activities...
    //A dialog for add, an activity for settings (see the text), a dialog for about, and a dialog (for practice) or activity for feedback.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add: { //should not be displayed.
                //I will come back here and add the intent or whatever that is needed to start the dialog.
                DialogFragment addRecipeDialog = new AddRecipeFragment();
                addRecipeDialog.show(getSupportFragmentManager(), "Add");
                return true; //no need for break statements as return statements also exit the switch block
            }
            case R.id.menu_settings: {//should not be displayed.
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_about: {//should not be displayed.
                return true;
            }
            case R.id.menu_feedback: {//should not be displayed.
                return true;
            }
            case R.id.menu_clear_all: {//should not be displayed.
                mRecipeViewModel.deleteAll();
                return true;
            }
            case R.id.menu_delete_this: {
                //I will at code to this later to delete the current recipe and send the user
                //back to the main screen.
//                Intent replyIntent = new Intent(this, MainActivity.class);
//                replyIntent.putExtra(EXTRA_REPLY, mOpenRecipe.getTitle());
//                startActivity(replyIntent);
//                onDestroy();

//                mRecipeViewModel.deleteRecipe(mOpenRecipe);
                //I can't do it this way... I need to pass this information back to MainActivity and
                //then I should be able to delete the recipe from there as there are listeners.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you wish to continue?");
                builder.setMessage("Clicking \"Yes\" will delete the selected recipe and image from the database. Are you sure that you would like to continue?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing Happens and the dialog should close.
                        mIsDelete = false; //This is unnecessary, but it makes me feel safe
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIsDelete = true;
                        finish();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
            case R.id.menu_share: {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);

                //Add my concatenated values to value of the recipe... woot woot
                //However, this only works with the files the recipes that are added. This does not work with starter data...maybe I need to create a new file on startup ...
                sendIntent.setType("image/*");
                sendIntent.putExtra("sms_body", mBriefDescription + "To get access to the full recipe, download Zakacat's Recipe App from the  Google Play Store.");
                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                sendIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, "com.example.android.fundamentalscapstone", (new File(mImageResource))));
                //Currently, this intent will send all the info in a SMS/MMS message, but only the picture when sendig through other media.
                Intent chooser = Intent.createChooser(sendIntent, "Share this recipe with...");
                startActivity(chooser);
                Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
                LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
                return true;
            }
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsDelete) {
            String imageToDelete = mOpenRecipe.getImageResource();
            mRecipeViewModel.deleteRecipe(mOpenRecipe);
            if (imageToDelete != null) {
                File recipeImage = new File(imageToDelete);
                recipeImage.delete();
                Log.d(LOG_TAG, "Deleted an image from DetailActivity.");
            }
        }
    }
}
