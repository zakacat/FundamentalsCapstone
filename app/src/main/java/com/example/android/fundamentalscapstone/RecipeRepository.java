package com.example.android.fundamentalscapstone;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipesABC, mAllRecipesRegion, mAllRecipesMeal;

    RecipeRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        mRecipeDao = db.recipeDao();
        mAllRecipesABC = mRecipeDao.getAllRecipesABC();
        mAllRecipesRegion = mRecipeDao.getAllRecipesRegion();
        mAllRecipesMeal = mRecipeDao.getAllRecipesMeal();
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

    public void insert(Recipe recipe) {
        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {

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

    public void deleteAll() {
        new deleteAllRecipesAsyncTask(mRecipeDao).execute();
    }

    private static class deleteAllRecipesAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecipeDao mAsyncTaskDao;

        deleteAllRecipesAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteWord(Recipe recipe) {
        new deleteRecipeAsyncTask(mRecipeDao).execute(recipe);
    }


    private static class deleteRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDao mAsyncTaskDao;

        deleteRecipeAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            mAsyncTaskDao.deleteRecipe(params[0]);
            return null;
        }
    }

}
