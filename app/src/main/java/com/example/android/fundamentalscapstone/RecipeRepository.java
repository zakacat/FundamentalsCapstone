package com.example.android.fundamentalscapstone;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipesABC, mAllRecipesRegion, mAllRecipesMeal;

    RecipeRepository(Application application){
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        mRecipeDao = db.recipeDao();
        mAllRecipesABC = mRecipeDao.getAllRecipesABC();
        mAllRecipesRegion = mRecipeDao.getAllRecipesRegion();
        mAllRecipesMeal = mRecipeDao.getAllRecipesMeal();
    }

    LiveData<List<Recipe>> getAllRecipesABC(){
        return mAllRecipesABC;
    }

    LiveData<List<Recipe>> getAllRecipesRegion() {
        return mAllRecipesRegion;
    }

    LiveData<List<Recipe>> getAllRecipesMeal() {
        return mAllRecipesMeal;
    }

    public void insert (Recipe recipe){
        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void>{

        private RecipeDao mAsyncTaskDao;

        insertAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
