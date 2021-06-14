package com.example.android.fundamentalscapstone;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
//The Recipe Database is an abstract class that builds the database from the table.
//Instance and singleton is used to avoid multiple versions of the database exisitng
//at one time, which is not good.
@Database(entities = Recipe.class, version = 2, exportSchema = false)//Version numbers don't need to be changed as the user has creative control of Recipes at this point.
public abstract class RecipeRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();

    private static RecipeRoomDatabase INSTANCE; //Singleton

    public static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { //check to see if there is a database
            synchronized (RecipeRoomDatabase.class) {
                if (INSTANCE == null) {
                    //Create the database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipeRoomDatabase.class, "recipe_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            // I am not quite sure how this will affect my app.
                            .fallbackToDestructiveMigration() //this can prevent updating the low-level information as there is no proper migration strategy.
                            .addCallback(sRoomDatabaseCallback) //calls everytime.
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    //This private class acts when the database is opened. onOpen (if the INSTANCE is null) this will be called and
    //directed to the startdata call which is an asynctask.
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {

        private final RecipeDao mDao;
        //Constructor to migrate the DAO.
        PopulateDBAsync(RecipeRoomDatabase db) {
            mDao = db.recipeDao();
        }

        //Only if there is no recipe to retrieve from the database
        @Override
        protected Void doInBackground(final Void... params) {

            if (mDao.getAnyRecipe().length < 1) { //I am not sure what returns with the getAnyWord() call but it is definitely not null... must be an empty string cause a check for null didn't work. Nevermind, I am dumb, it returns an array of Recipes, and if the array is less than 1, yada yada yada.
                mDao.insert(new Recipe("Eggs and Toast", "Delicate Eggs on delicate toast", "2 eggs \n2 pieces of toast \n1 spoon of butter", "eggs, bread, butter", "Fry eggs and put atop toast", 0, 0));
                mDao.insert(new Recipe("Cereal", "Scrumtious Cereal", "", "", "", 1, 3));
                mDao.insert(new Recipe("Milk", "White liquidy maternal fluid", "", "", "", 0, 2));
                mDao.insert(new Recipe("Pakora", "Delious Indian fried vegetables", "", "", "", 4, 1));
                mDao.insert(new Recipe("Raisins", "Yes, they's raisins!", "", "", "", 7, 3));
                mDao.insert(new Recipe("Ham Sammich", "Wiggle, wiggle, wiggle", "", "", "", 4, 0));
                mDao.insert(new Recipe("Fudge", "It is like chocolate, but with more sugar!", "", "", "", 2, 1));
                mDao.insert(new Recipe("Something something", "Could be?", "", "", "", 3, 0));
                mDao.insert(new Recipe("I am bored...", "of writing starter data", "", "", "", 6, 0));
            }
            return null;
        }

    }

}
