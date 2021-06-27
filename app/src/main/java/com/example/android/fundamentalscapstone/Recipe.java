package com.example.android.fundamentalscapstone;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//This is the entity of the database that holds the recipe objects.
// The entity is equivalent to the table wand each table on the row is represented
//by a table row. Each table column corresponds with  a Recipe object attribute.
@Entity(tableName = "recipe_table")
public class Recipe {
    //The title is also the primary key so recipes with the same title cannot coexist. That is why
    // there is a conflict strategy for handling this situation.
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
    //To prevent confusion of representation, especially in the alternate tabs, I have decided to store
    //the region and meal as ints. This appeared to me to be a cleaner way of interpreting the information.
    //I use a spinner and radiobuttons to define the selection. This prevents unacceptable input. Maybe in
    //the future I could just deal with the string... as long as the input is consistent... This would prevent
    //the need for methods to convert back to a String.
    @ColumnInfo(name = "Region of Origin")
    private int mRegionOfOrigin;

    @ColumnInfo(name = "Type of Meal")
    private int mTypeOfMeal;

    @ColumnInfo(name = "Image Path")
    private int mImageResource;


    // I would like to also find a way to store an image... I think I can save the file locally and access it with the saved file path.
    //private String mImageURI; I can refer to 5.2 Cards and colors.


    //The constructor needs a input for all the parameters/attributes. Null input should be avoided.
    //Null cases should be replaced with an empty string "".
    public Recipe(@NonNull String title, String briefDescription, String ingredientsWithMeasurements, String ingredientsForShopping, String instructions, int regionOfOrigin, int typeOfMeal, int imageResource) {
        this.mTitle = title;
        this.mBriefDescription = briefDescription;
        this.mIngredientsWithMeasurements = ingredientsWithMeasurements;
        this.mIngredientsForShopping = ingredientsForShopping;
        this.mInstructions = instructions;
        this.mRegionOfOrigin = regionOfOrigin;
        this.mTypeOfMeal = typeOfMeal;
        this.mImageResource = imageResource;
    }
    //Getters for all the attributes. I also think I will be adding two new attributes. One a boolean
    //for whether the recipe is on the shopping list or not (in which case the shopping ingredients can be
    //gathered from each recipe in the shopping list (avoiding duplicate information somehow) and displayed
    //for the user to take to the grocery store. The other will be a reference to img using Glide. This
    //image will be saved locally and will be used in the card and in the details activity.
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

    public int getImageResource() {return mImageResource;}
}
