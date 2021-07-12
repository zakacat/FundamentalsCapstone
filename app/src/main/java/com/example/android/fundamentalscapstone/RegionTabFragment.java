package com.example.android.fundamentalscapstone;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

//Refer to AbcTabFragment for details.
public class RegionTabFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private RecipeListAdapter mAdapter;
    private View regionFragment;
    private static final String LOG_TAG = AbcTabFragment.class.getSimpleName();

    public RegionTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        regionFragment = inflater.inflate(R.layout.fragment_tab_recyclerview, container, false);

        RecyclerView recyclerView = regionFragment.findViewById(R.id.tab_recyclerview);
        mAdapter = new RecipeListAdapter(regionFragment.getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(regionFragment.getContext()));

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getAllRecipesRegion().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable final List<Recipe> recipes) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setRecipesAbc(recipes);
            }
        });

        registerForContextMenu(recyclerView);


        return regionFragment;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = mAdapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        if (getUserVisibleHint()) {
            if (item.getItemId() == R.id.menu_delete) {
                //Delete the recycler view at this position.
                int localPosition = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(regionFragment.getContext());
                builder.setTitle("Do you wish to continue?");
                builder.setMessage("Clicking \"Yes\" will delete the selected recipe and image from the database. Are you sure that you would like to continue?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing Happens and the dialog should close.

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Recipe myRecipe = mAdapter.getRecipeAtPosition(localPosition);
                        String imageToDelete = myRecipe.getImageResource();
                        mRecipeViewModel.deleteRecipe(myRecipe);
                        if (imageToDelete != null) {
                            File recipeImage = new File(imageToDelete);
                            recipeImage.delete();
                            Log.d(LOG_TAG, "Deleted an image from RegionTabFragment.");
                        }
                        Log.d(LOG_TAG, "Deleted a recipe from RegionTabFragment.");

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        return super.onContextItemSelected(item);
    }
}