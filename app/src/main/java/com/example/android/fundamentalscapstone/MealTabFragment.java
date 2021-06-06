package com.example.android.fundamentalscapstone;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MealTabFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;

    public MealTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mealFragment = inflater.inflate(R.layout.fragment_abc_tab, container, false);

        RecyclerView recyclerView = mealFragment.findViewById(R.id.abc_recyclerview);
        final RecipeListAdapter adapter = new RecipeListAdapter(mealFragment.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mealFragment.getContext()));

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getAllRecipesMeal().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable final List<Recipe> recipes) {
                // Update the cached copy of the words in the adapter.
                adapter.setRecipesAbc(recipes);
            }
        });


        return mealFragment;
    }
}