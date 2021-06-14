package com.example.android.fundamentalscapstone;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
//The view model is designed to pass data onto the UI and handle configuration changes. The
//View model (Think MVC ish) passes information between the Repository and the UI. It is part of
//the lifecycle library. The fragments and activities are for drawing information to the screen
//and the Viewmodel is meant to hold and process the data.
public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    private LiveData<List<Recipe>> mAllRecipesABC, mAllRecipesRegion, mAllRecipesMeal;

    //Again we take in the Application as the parameter and pass in all the values from the
    //application's instance of the repository.
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mAllRecipesABC = mRepository.getAllRecipesABC();
        mAllRecipesRegion = mRepository.getAllRecipesRegion();
        mAllRecipesMeal = mRepository.getAllRecipesMeal();
    }

    //The getters and the insert/delete methods are passed up from the repository. No need to
    //define the AsyncTasks as they have been defined in the Repository.
    LiveData<List<Recipe>> getAllRecipesABC() {
        return mAllRecipesABC;
    }

    LiveData<List<Recipe>> getAllRecipesRegion() {
        return mAllRecipesRegion;
    }

    LiveData<List<Recipe>> getAllRecipesMeal() {
        return mAllRecipesMeal;
    }

    LiveData<Recipe> getRecipe(String title) { return mRepository.getRecipe(title);};

    public void insert (Recipe recipe) {
        mRepository.insert(recipe);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteRecipe(Recipe recipe) {mRepository.deleteRecipe(recipe);}

}
