package com.example.android.fundamentalscapstone;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.security.acl.Owner;
import java.util.Calendar;
import java.util.List;

import static com.example.android.fundamentalscapstone.DetailActivity.EXTRA_REPLY;


public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecipeViewModel mRecipeViewModel;
    private CustomReceiver mReceiver = new CustomReceiver(this);
    private CustomReceiver mCustomReceiver = new CustomReceiver(this);
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private NotificationManager mNotificationManager;
    private static final int BREAKFAST_NOTIFICATION_ID = 0, LUNCH_NOTIFICATION_ID = 1, SUPPER_NOTIFICATION_ID = 2;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";


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

        //This code is for the normal broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        // Register the receiver using the activity context.
        this.registerReceiver(mReceiver, filter);

        //This code is for the custom broadcast receiver
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mCustomReceiver,
                        new IntentFilter(ACTION_CUSTOM_BROADCAST));


        //Initializing the notification service using getSystemService
        createNotificationChannel();


        long repeatInterval = AlarmManager.INTERVAL_DAY;

        //Breakfast alarm and notification
        //
        //
        Intent notifyBreakfastIntent = new Intent(this, AlarmReceiver.class);
        notifyBreakfastIntent.putExtra("intentKey", 0);
        PendingIntent notifyBreakfastPendingIntent = PendingIntent.getBroadcast
                (this, BREAKFAST_NOTIFICATION_ID, notifyBreakfastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager breakfastAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // Set the alarm to start at approximately 7:00 a.m.
        Calendar breakfastCalendar = Calendar.getInstance();
        breakfastCalendar.setTimeInMillis(System.currentTimeMillis());
        breakfastCalendar.set(Calendar.HOUR_OF_DAY, 7);



        breakfastAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, breakfastCalendar.getTimeInMillis(), repeatInterval, notifyBreakfastPendingIntent);
        //Lunch alarm and notification
        //
        //
        Intent notifyLunchIntent = new Intent(this, AlarmReceiver.class);
        notifyLunchIntent.putExtra("intentKey", 1);
        PendingIntent notifyLunchPendingIntent = PendingIntent.getBroadcast
                (this, LUNCH_NOTIFICATION_ID, notifyLunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager lunchAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // Set the alarm to start at approximately 11:00 a.m.
        Calendar lunchCalendar = Calendar.getInstance();
        lunchCalendar.setTimeInMillis(System.currentTimeMillis());
        lunchCalendar.set(Calendar.HOUR_OF_DAY, 11);

        lunchAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, lunchCalendar.getTimeInMillis(), repeatInterval, notifyLunchPendingIntent);
        //Supper alarm and notification
        //
        //
        Intent notifySupperIntent = new Intent(this, AlarmReceiver.class);
        notifySupperIntent.putExtra("intentKey", 2);
        PendingIntent notifySupperPendingIntent = PendingIntent.getBroadcast
                (this, SUPPER_NOTIFICATION_ID, notifySupperIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager supperAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // Set the alarm to start at approximately 5:00 p.m.
        Calendar supperCalendar = Calendar.getInstance();
        supperCalendar.setTimeInMillis(System.currentTimeMillis());
        supperCalendar.set(Calendar.HOUR_OF_DAY, 17);

        supperAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, supperCalendar.getTimeInMillis(), repeatInterval, notifySupperPendingIntent);


    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    protected void onDestroy() {
        //Unregister the receiver
        this.unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mCustomReceiver);

        super.onDestroy();
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
                LifecycleOwner owner = this; //Wow, this seems like cheating. Is there a way to see what "this" is directly referencing???
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you wish to continue?");
                builder.setMessage("Clicking \"Yes\" will delete  ALL the recipes and images from the database. Are you sure that you would like to continue?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing Happens and the dialog should close.

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRecipeViewModel.getAllRecipesABC().observe(owner, new Observer<List<Recipe>>() {
                            @Override
                            public void onChanged(List<Recipe> recipes) {
                                for (int i = 0; i < recipes.size(); i++) {
                                    File imageFile = new File(recipes.get(i).getImageResource());
                                    imageFile.delete();
                                }
                            }
                        });
                        mRecipeViewModel.deleteAll();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

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