package com.example.android.fundamentalscapstone;

import android.os.Bundle;

import androidx.annotation.Nullable;
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

//Refer to AbdTabFragment for details.
public class MealTabFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private RecipeListAdapter mAdapter;
    private View mealFragment;
    private static final String LOG_TAG = AbcTabFragment.class.getSimpleName();

    public MealTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mealFragment = inflater.inflate(R.layout.fragment_tab_recyclerview, container, false);

        RecyclerView recyclerView = mealFragment.findViewById(R.id.tab_recyclerview);
        mAdapter = new RecipeListAdapter(mealFragment.getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mealFragment.getContext()));

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getAllRecipesMeal().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable final List<Recipe> recipes) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setRecipesAbc(recipes);
            }
        });

        registerForContextMenu(recyclerView);


        return mealFragment;
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
                Recipe myRecipe = mAdapter.getRecipeAtPosition(position);
                String imageToDelete = myRecipe.getImageResource();
                mRecipeViewModel.deleteRecipe(myRecipe);
                if (imageToDelete != null) {
                    File recipeImage = new File(imageToDelete);
                    recipeImage.delete();
                    Log.d(LOG_TAG, "Deleted an image from MealTabFragment.");
                }
                Log.d(LOG_TAG, "Deleted a recipe from MealTabFragment.");
            }
        }

        return super.onContextItemSelected(item);
    }
}