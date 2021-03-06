package com.example.android.fundamentalscapstone;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

//This is the fragment which displays the recipe creator dialog.
public class AddRecipeFragment extends DialogFragment {

    private View mAddRecipeDialog;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton addImageButton;
    private String currentPhotoPath;


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

        addImageButton = mAddRecipeDialog.findViewById(R.id.add_recipe_imagebutton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getContext(),
                                "com.example.android.fundamentalscapstone",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }


            }
        });
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
        String imageResource;


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

        if (currentPhotoPath != null) {
            imageResource = currentPhotoPath;
        } else {
            imageResource = null;
        }


        //Using the ViewModel as it is the highest level of access.
        //Here I am trying to add a check to make sure that the title doesn't match another recipe.
        //Because it is also the primary key, the app will crash if I don't do this check and the same
        //recipe is created twice. Maybe I will come back to this. *I figured this out somewhere else.
        //I was able to add a conflict strategy to the DAO.
        RecipeViewModel newRecipe = ViewModelProviders.of(this).get(RecipeViewModel.class);
        //the image resource attribute is set to zero in the constructor to test the glide function.
        //There is going to be a fair amount more work involved in making it possible to add their own pictures.
        newRecipe.insert(new Recipe(title, briefDescription, ingredientsWithMeasurements, ingredientsForShopping, instructions, region, mealType, imageResource));

        onStop();
    }

    private void onCancelButtonClicked() {
        onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Glide.with(getContext()).load(currentPhotoPath).into(addImageButton);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



}