package com.example.android.fundamentalscapstone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // I can programmatically remove the add option like this >>> menu.removeItem(R.id.menu_add); >>> nice
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
                return true; //no need for break statements as return statements also exit the switch block
            }
            case R.id.menu_settings: {
                return true;
            }
            case R.id.menu_about: {
                return true;
            }
            case R.id.menu_feedback: {
                return true;
            }
            default:
        }

        return super.onOptionsItemSelected(item);
    }

}