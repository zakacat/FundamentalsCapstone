package com.example.android.fundamentalscapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    public static final String
            KEY_PREF_DARK_MODE = "dark_mode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //This needs to be set as the default have been changed as we are using custom toolbar settings
        //in this app. This will enable the toolbar to show th up arrow (which is what I want).
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}