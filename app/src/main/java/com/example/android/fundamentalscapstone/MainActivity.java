package com.example.android.fundamentalscapstone;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.security.acl.Owner;
import java.util.List;

import static com.example.android.fundamentalscapstone.DetailActivity.EXTRA_REPLY;


public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecipeViewModel mRecipeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
//      This is me trying to figure out how to get the delete function to work...

//        if (getIntent() != null) {
//            Intent intent = getIntent();
//            String tempTitle = intent.getStringExtra(EXTRA_REPLY);
//            if (tempTitle != null) {
//                Log.d("intent Return test", tempTitle);
//                mRecipeViewModel.getRecipe(tempTitle).observe(this, new Observer<Recipe>() {
//                    @Override
//                    public void onChanged(Recipe recipe) {
//                        mRecipeViewModel.deleteRecipe(recipe);
//                    }
//
//                });
//            }
//        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        //Set the Title text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        // Set the tabs to fill the entire layout.
        //I can do this here programmatically, or I can do it in the layout file with app:tabGravity="fill"
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Use Pager Adapter to manage page views in fragments.
        //Each page is represented by it's own fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //Make sure to set the adapter or nothing will be displayed in the fragment view.
        viewPager.setAdapter(pagerAdapter);
        //Setting click listeners.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); //Automatically handles swipe gestures?
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0: {
                        Log.d(LOG_TAG, "Abc Tab Selected!");
                        break;
                    }
                    case 1: {
                        Log.d(LOG_TAG, "Country Tab Selected!");
                        break;
                    }
                    case 2: {
                        Log.d(LOG_TAG, "Menu Tab Selected!");
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //Call this to ensure that the preferences are properly initialized. Tlas param- the boolean
        //sets to NOT set the default values more than once. True will override previous settings.
        androidx.preference.PreferenceManager
                .setDefaultValues(this, R.xml.preferences, false);
        //Here I am calling from the shared preferences and displaying a taost to show the value
        //of dark_mode.
        SharedPreferences sharedPref =
                androidx.preference.PreferenceManager
                        .getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_DARK_MODE, false);
        //This Toast does not display properly, but I think it is the context as the MainActivity is
        //hosting the view pager, tab elements, and fragments. However the log statement is
        //displaying the information that is to be expected.
//        Toast.makeText(this, switchPref.toString(),
//                Toast.LENGTH_SHORT).show();
        Log.d("sharedPref test", switchPref.toString());

        //Here is the easy way to choose between night mode and day mode...
        //Because the app comes with a themes of night and normal, AppCompatDelegate
        //must switch between the two themes in the background.
        if (switchPref == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // I can programmatically remove the add option like this >>> menu.removeItem(R.id.menu_add); >>> nice
        menu.removeItem(R.id.menu_delete_this);
        menu.removeItem(R.id.menu_share);
        return true;
    }

    //Here is where I can add the intents for the dialogs and activities...
    //A dialog for add, an activity for settings (see the text), a dialog for about, and a dialog (for practice) or acitivity for feedback.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add: {
                //I will come back here and add the intent or whatever that is needed to start the dialog.
                DialogFragment addRecipeDialog = new AddRecipeFragment();
                addRecipeDialog.show(getSupportFragmentManager(), "Add");
                return true; //no need for break statements as rteturn statements also exit the switch block
            }
            case R.id.menu_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_about: {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_feedback: {
                Intent intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_clear_all: {
                mRecipeViewModel.getAllRecipesABC().observe(this, new Observer<List<Recipe>>() {
                    @Override
                    public void onChanged(List<Recipe> recipes) {
                    for (int i = 0; i < recipes.size(); i ++){
                        File imageFile = new File(recipes.get(i).getImageResource());
                        imageFile.delete();
                    }
                    }
                });
                mRecipeViewModel.deleteAll();
                return true;
            }
            case R.id.menu_delete_this: {
                //Do nothing... it shouldn't appear here.
                return true;
            }
            default:
        }

        return super.onOptionsItemSelected(item);
    }


}