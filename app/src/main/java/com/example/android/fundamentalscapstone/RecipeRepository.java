package com.example.android.fundamentalscapstone;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

//Repository. The repository is suggested best practice. It creates a level of abstraction for the
//access to multiple data sources. It handles data operation. The repository can be used to access
//the DAO or possibly a network. Notably, this is also where the background tasks have been handled
//for inserting and deleting recipes.
public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipesABC, mAllRecipesRegion, mAllRecipesMeal;

    //The constructor accepts the Application as the parameter.
    //I am guessing that according to the documentation, that the application
    //acts similarily to a singleton.
    RecipeRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        mRecipeDao = db.recipeDao();
        mAllRecipesABC = mRecipeDao.getAllRecipesABC();
        mAllRecipesRegion = mRecipeDao.getAllRecipesRegion();
        mAllRecipesMeal = mRecipeDao.getAllRecipesMeal();
    }

    //The getters all use LiveData<> and return the calls from the the DAO.
    //LiveData can be used to easily handle tasks in the background when
    //needed.
    LiveData<List<Recipe>> getAllRecipesABC() {
        return mAllRecipesABC;
    }

    LiveData<List<Recipe>> getAllRecipesRegion() {
        return mAllRecipesRegion;
    }

    LiveData<List<Recipe>> getAllRecipesMeal() {
        return mAllRecipesMeal;
    }

    LiveData<Recipe> getRecipe(String title) {
        return mRecipeDao.getRecipe(title);
    }

    //The insert and delete methods also refer to internal static classes that implement
    //a method to carry out work off of the main UI thread.
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

    public void deleteRecipe(Recipe recipe) {
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
