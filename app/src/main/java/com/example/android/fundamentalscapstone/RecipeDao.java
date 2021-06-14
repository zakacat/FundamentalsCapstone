package com.example.android.fundamentalscapstone;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
//Database Access Object for the recipes. All these methods must all be annotated with Room annotations.
//These current methods are only dealing with the recipes directly, so anything further donw the
//hierarchy is dealing with recipe objects.
@Dao
public interface RecipeDao {

    //This conflict strategy for inserting a new recipe with the same name/passkey. The new one
    //overwrites the old one.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Delete
    void deleteRecipe(Recipe recipe);
    //LiveData is used with retrieving Recipes for two reasons. 1. There are methods for observation
    //the information in real time (like a listener) 2. Actions based on the information should not
    //be done in the main thread and LiveData can do processes in the background.
    @Query("SELECT * from recipe_table ORDER BY Title ASC")
    LiveData<List<Recipe>> getAllRecipesABC();

    //I will also have to call this multiple times before being displayed (USE A LOOP!!!!) for the separate regions.
    // (0 = North America, 1 = Central America, 2 = South America, 3 = Western Europe, 4 = Eastern Europe, 5 = Middle East, 6 = Western Asia, 7 = Eastern Asia, 8 = Oceania)
    @Query("SELECT * FROM recipe_table ORDER BY `Region of Origin` ASC, Title")
    LiveData<List<Recipe>> getAllRecipesRegion();

    //When I call this, I will have to call this 4 separate times (USE A LOOP!!!) for the separate meal types.
    //This will be easier to do as an int I think. 0-3 (0 = Breakfast, 1 = Lunch, 2 = Supper, 3 = Snack)
    //I will now need to restrict the user selection, so maybe a radio button is preferred upon generation.
    @Query("SELECT * from recipe_table ORDER BY `Type of Meal` ASC, Title")
    LiveData<List<Recipe>> getAllRecipesMeal();

    //This method is called only once in the database to confirm whether the database is empty or not
    // to decide whether to instantiate starter data or not.
    @Query("SELECT * from recipe_table LIMIT 1")
    Recipe[] getAnyRecipe();

    @Query("Select * from recipe_table WHERE title = :title")
    LiveData<Recipe> getRecipe(String title);

}
