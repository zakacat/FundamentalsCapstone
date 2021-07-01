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

//Tab for listing the recipes by title in alphabetical order.
public class AbcTabFragment extends Fragment {

    private RecipeListAdapter mAdapter;
    private RecipeViewModel mRecipeViewModel;
    private View abcFragment;

    private static final String LOG_TAG = AbcTabFragment.class.getSimpleName();

    public AbcTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Remember to create the view first, and then we can assign view widgets and adjust them as such...
        //All the tab fragments now call the same layout and thusly the same recyclerview widget.
        abcFragment = inflater.inflate(R.layout.fragment_tab_recyclerview, container, false);

        RecyclerView recyclerView = abcFragment.findViewById(R.id.tab_recyclerview);
        //Setting the adapter
        mAdapter = new RecipeListAdapter(abcFragment.getContext());
        recyclerView.setAdapter(mAdapter);
        //Setting the adapter to display the items in the recycler view in a linear view.
        recyclerView.setLayoutManager(new LinearLayoutManager(abcFragment.getContext()));

        //This call needs to be called when instantiating the ViewModel in the class. I think
        //this again is making sure to reference only the one copy of the ViewModel that exists.
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getAllRecipesABC().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable final List<Recipe> recipes) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setRecipesAbc(recipes);
            }
        });
        registerForContextMenu(recyclerView);

        return abcFragment;
    }

    //Although the call to CREATE the context menu is located in the ListAdapter, handling it is
    //done here in the fragment. There needs to be a check for which tab is in view or more than
    //one card will delete.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = mAdapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        //This is deprecated, but it this the integral part of checking what tab the user is interacting with.
        //Without this, there are some  strange errors that occur with multiple deletions over the list.
        if (getUserVisibleHint()) {
            if (item.getItemId() == R.id.menu_delete) {
                //Delete the recycler view at this position.
                Recipe myRecipe = mAdapter.getRecipeAtPosition(position);
                String imageToDelete = myRecipe.getImageResource();
                mRecipeViewModel.deleteRecipe(myRecipe);
                if (imageToDelete != null) {
                    File recipeImage = new File(imageToDelete);
                    recipeImage.delete();
                    Log.d(LOG_TAG, "Deleted an image from AbcTabFragment.");
                }
                Log.d(LOG_TAG, "Deleted a recipe from AbcTabFragment.");
            }
        }

        return super.onContextItemSelected(item);
    }
}