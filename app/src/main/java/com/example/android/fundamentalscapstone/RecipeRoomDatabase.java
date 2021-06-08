package com.example.android.fundamentalscapstone;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Recipe.class, version = 2, exportSchema = false)
public abstract class RecipeRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();

    private static RecipeRoomDatabase INSTANCE;

    public static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeRoomDatabase.class) {
                if (INSTANCE == null) {
                    //Create the database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipeRoomDatabase.class, "recipe_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            // I am not quite sure how this will affect my app.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {

        private final RecipeDao mDao;

        PopulateDBAsync(RecipeRoomDatabase db) {
            mDao = db.recipeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            if (mDao.getAnyRecipe().length < 1) { //I am not sure what returns with the getAnyWord() call but it is definitely not null... must be an empty string cause a check for null didn't work. Nevermind, I am dumb, it returns an array of Recipes, and if the array is less than 1, yada yada yada.
                mDao.insert(new Recipe("Eggs and Toast", "Delicate Eggs on delicate toast", "2 eggs /n 2 pieces of toast /n 1 spoon of butter", "eggs, bread, butter", "Fry eggs and put atop toast", 0, 0));
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
