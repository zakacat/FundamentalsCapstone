package com.example.android.fundamentalscapstone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

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

        abcFragment = inflater.inflate(R.layout.fragment_abc_tab, container, false);

        RecyclerView recyclerView = abcFragment.findViewById(R.id.abc_recyclerview);
        mAdapter = new RecipeListAdapter(abcFragment.getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(abcFragment.getContext()));

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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = mAdapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        if(getUserVisibleHint()) {
            if (item.getItemId() == R.id.menu_delete) {
                //Delete the recycler view at this position.
                int myPosition = mAdapter.getPosition();
                Recipe myRecipe = mAdapter.getRecipeAtPosition(myPosition);
                mRecipeViewModel.deleteRecipe(myRecipe);
                Log.d(LOG_TAG, "Deleted a recipe from AbcTabFragment.");
            }
        }

        return super.onContextItemSelected(item);
    }
}