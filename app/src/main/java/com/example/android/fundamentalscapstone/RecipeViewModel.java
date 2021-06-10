package com.example.android.fundamentalscapstone;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    private LiveData<List<Recipe>> mAllRecipesABC, mAllRecipesRegion, mAllRecipesMeal;


    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mAllRecipesABC = mRepository.getAllRecipesABC();
        mAllRecipesRegion = mRepository.getAllRecipesRegion();
        mAllRecipesMeal = mRepository.getAllRecipesMeal();
    }

    LiveData<List<Recipe>> getAllRecipesABC() {
        return mAllRecipesABC;
    }

    LiveData<List<Recipe>> getAllRecipesRegion() {
        return mAllRecipesRegion;
    }

    LiveData<List<Recipe>> getAllRecipesMeal() {
        return mAllRecipesMeal;
    }

    public void insert (Recipe recipe) {
        mRepository.insert(recipe);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteRecipe(Recipe recipe) {mRepository.deleteRecipe(recipe);}

}
