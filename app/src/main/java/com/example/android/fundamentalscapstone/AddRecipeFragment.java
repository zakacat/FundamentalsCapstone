package com.example.android.fundamentalscapstone;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.List;

//This is the fragment which displays the recipe creator dialog.
public class AddRecipeFragment extends DialogFragment {

    private View mAddRecipeDialog;

    public AddRecipeFragment() {
        // Required empty public constructor
        //Nothing happens directly with the constructor. These overridden methods take
        //care of business.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Creating the view from which all the view elements will refer from
        mAddRecipeDialog = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        //Adding buttons programatically and handling them with private methods.
        Button addButton = mAddRecipeDialog.findViewById(R.id.add_recipe_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
            }
        });

        Button cancelButton = mAddRecipeDialog.findViewById(R.id.add_recipe_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClicked();
            }
        });

        return mAddRecipeDialog;
    }

    private void onAddButtonClicked() {

        String title;
        int mealType;
        int region;
        String briefDescription;
        String ingredientsWithMeasurements;
        String ingredientsForShopping;
        String instructions;


        /** This block below is for handling the first EditText for the Title attribute.
         */
        EditText titleEditText = mAddRecipeDialog.findViewById(R.id.add_recipe_title_edittext);
        if (titleEditText.getText() != null) {
            //Here I can assign the value of the text as a string to a member variable
            title = titleEditText.getText().toString();
        } else {
            //and here I will assign the null as "" so as night to pass in null values especially to the
            //title as that becomes the @PrimaryKey and it cannot be null.
            title = "";
        }
        /** This block below is for handling the radiobutton group
         */
        // I will incorporate a switch block to assign the int values to be stored.
        //I shouldn't need a null fail-safe as first it is a radio button, and second
        RadioGroup mealRadioGroup = mAddRecipeDialog.findViewById(R.id.add_recipe_radiogroup);
        switch (mealRadioGroup.getCheckedRadioButtonId()) {

            case R.id.add_recipe_breakfast_button: { //Add variable assignment here
                mealType = 0;
                break;
            }
            case R.id.add_recipe_lunch_button: {
                mealType = 1;
                break;
            }
            case R.id.add_recipe_supper_button: {
                mealType = 2;
                break;
            }
            case R.id.add_recipe_snack_button: {
                mealType = 3;
                break;
            }
            default: {
                //2 for supper should be the default.
                mealType = 2;
            }
        }
        /** This block below is for handling the spinner
         */
        //Recognizing the spinner and using an array adapter to read the information.
        Spinner regionSpinner = mAddRecipeDialog.findViewById(R.id.add_recipe_spinner);
        region = regionSpinner.getSelectedItemPosition();

        /** This block is for handling the BriefDescription EditText
         */
        EditText briefDescriptionEditText = mAddRecipeDialog.findViewById(R.id.add_recipe_brief_description);
        if (briefDescriptionEditText.getText() != null) {
            briefDescription = briefDescriptionEditText.getText().toString();
        } else {
            //assign "". An Empty string is easier to handle than a null return.
            briefDescription = "";
        }

        /** This block is for handling the Ingredients with Measurements EditText
         */
        EditText ingredientsWithMeasurementsEditText = mAddRecipeDialog.findViewById(R.id.add_recipe_ingredients_with_measurements);
        if (ingredientsWithMeasurementsEditText.getText() != null) {
            ingredientsWithMeasurements = ingredientsWithMeasurementsEditText.getText().toString();
        } else {
            //assign ""
            ingredientsWithMeasurements = "";
        }
        /** This block is for handling the Ingredients for shopping EditText
         */
        EditText shoppingEditText = mAddRecipeDialog.findViewById(R.id.add_recipe_ingredients_for_shopping);
        if (shoppingEditText.getText() != null) {
            ingredientsForShopping = shoppingEditText.getText().toString();
        } else {
            //assign""
            ingredientsForShopping = "";
        }
        /** This block is for handling the instructions EditText
         */
        EditText instructionsEditText = mAddRecipeDialog.findViewById(R.id.add_recipe_instructions);
        if (instructionsEditText.getText() != null) {
            instructions = instructionsEditText.getText().toString();
        } else {
            //assign ""
            instructions = "";
        }
         //Using the ViewModel as it is the highest level of access.
        //Here I am trying to add a check to make sure that the title doesn't match another recipe.
        //Because it is also the primary key, the app will crash if I don't do this check and the same
        //recipe is created twice. Maybe I will come back to this. *I figured this out somewhere else.
        //I was able to add a conflict strategy to the DAO.
        RecipeViewModel newRecipe = ViewModelProviders.of(this).get(RecipeViewModel.class);
        newRecipe.insert(new Recipe(title, briefDescription, ingredientsWithMeasurements, ingredientsForShopping, instructions, region, mealType));

        onStop();
    }

    private void onCancelButtonClicked() {
        onStop();
    }

}