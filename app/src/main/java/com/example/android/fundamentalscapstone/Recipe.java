package com.example.android.fundamentalscapstone;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class Recipe {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Title")
    private String mTitle;

    @ColumnInfo(name = "Brief Description")
    private String mBriefDescription;

    @ColumnInfo(name = "Ingredients with Measurements")
    private String mIngredientsWithMeasurements;

    @ColumnInfo(name = "Ingredients for Shopping")
    private String mIngredientsForShopping;

    @ColumnInfo(name = "Instructions")
    private String mInstructions;

    @ColumnInfo(name = "Region of Origin")
    private int mRegionOfOrigin;

    @ColumnInfo(name = "Type of Meal")
    private int mTypeOfMeal;


    // I would like to also find a way to store an image... I think I can save the file locally and access it with the saved file path.
    //private String mImageURI; I can refer to 5.2 Cards and colors.


    public Recipe(@NonNull String title, String briefDescription, String ingredientsWithMeasurements, String ingredientsForShopping, String instructions, int regionOfOrigin, int typeOfMeal) {
        this.mTitle = title;
        this.mBriefDescription = briefDescription;
        this.mIngredientsWithMeasurements = ingredientsWithMeasurements;
        this.mIngredientsForShopping = ingredientsForShopping;
        this.mInstructions = instructions;
        this.mRegionOfOrigin = regionOfOrigin;
        this.mTypeOfMeal = typeOfMeal;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getIngredientsWithMeasurements() {
        return mIngredientsWithMeasurements;
    }

    public String getIngredientsForShopping() {
        return mIngredientsForShopping;
    }

    public String getInstructions() { return mInstructions; }

    public String getBriefDescription() {
        return mBriefDescription;
    }

    public int getRegionOfOrigin() {
        return mRegionOfOrigin;
    }

    public int getTypeOfMeal() {
        return mTypeOfMeal;
    }
}
