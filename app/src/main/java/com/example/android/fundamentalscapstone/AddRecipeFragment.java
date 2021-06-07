package com.example.android.fundamentalscapstone;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AddRecipeFragment extends DialogFragment {

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addRecipeDialog = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        return addRecipeDialog;
    }

    //I think that I can add a call directly in here to add the Recipe to the database.
}