package com.example.android.fundamentalscapstone;


import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

//The RecipeListAdapter is what handles the items in the recyclerview.
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes;
    private MenuInflater mMenuInflater;
    private int mPosition;

    //Creating a RecipeListAdapter from the context parameter and creating the layout inflater
    //and menu inflater from the context.
    RecipeListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mMenuInflater = new MenuInflater(context);
    }

    //This method takes the viewgroup from the context layout file and the viewType as an int... but
    //then this is cast as a View when a RecipeViewHolder is created to be returned?
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    //Get the position of the item in the list.
    public int getPosition() {
        return mPosition;
    }

    //Set the position of the item.
    public void setPosition(int position) {
        this.mPosition = position;
    }

    //onBindViewHolder takes the holder object and the position and finds the Recipe
    //object (representation from the database) and then modifies the card view widgets to
    //displayed in the card in recycler view in the tab fragments.
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (mRecipes != null) {
            Recipe current = mRecipes.get(position);
            holder.recipeTitleView.setText(current.getTitle());
            holder.recipeDescriptionView.setText(current.getBriefDescription());

            //I may need to copy this switch block several times...
            //And again it may have been easier to just assign a string from user input! But
            //I did want to demonstrate that I could work with other datatypes.
            String region;
            switch (current.getRegionOfOrigin()) {
                case 0:
                    region = "North America";
                    break;
                case 1:
                    region = "Central America";
                    break;
                case 2:
                    region = "South America";
                    break;
                case 3:
                    region = "Western Europe";
                    break;
                case 4:
                    region = "Eastern Europe";
                    break;
                case 5:
                    region = "Middle East";
                    break;
                case 6:
                    region = "Western Asia";
                    break;
                case 7:
                    region = "Eastern Asia";
                    break;
                case 8:
                    region = "Oceania";
                    break;
                default:
                    region = "no data";
            }
            holder.recipeRegionView.setText(region);

            //And this block as well...
            String meal;
            switch (current.getTypeOfMeal()) {
                case 0:
                    meal = "Breakfast";
                    break;
                case 1:
                    meal = "Lunch";
                    break;
                case 2:
                    meal = "Supper";
                    break;
                case 3:
                    meal = "Snack";
                    break;
                default:
                    meal = "no data";
            }
            holder.recipeMealView.setText(meal);

            //Here I will load the images as I see fit. I will start with the starter data and I also
            //included a clause as to avoid a null circumstance.
            if (current.getImageResource() != 0) {
                Glide.with(holder.itemView.getContext()).load(current.getImageResource()).into(holder.recipeImageView);
            }
            else {
                Glide.with(holder.itemView.getContext()).load(R.drawable.image_not_found).into(holder.recipeImageView);
            }
        } else {
            //In case the data is not ready yet
            holder.recipeTitleView.setText("no data");
            holder.recipeDescriptionView.setText("no data");
            holder.recipeRegionView.setText("no data");
            holder.recipeMealView.setText("no data");
            Glide.with(holder.itemView.getContext()).load(R.drawable.image_not_found).into(holder.recipeImageView);
        }

        //This is the listener that will handle the longclick. It simply updates the position from
        //the holder. (I tried to delete this as I thought the onCreateContextMenu would handle the
        //the long clicks, and it kinda does but ther was an of by one error visible in the region
        // tab). I am wondering why I can't handle this within the holder private class?
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });

        //This is definitely needed. as a short click uses an intent to open the recipe detail
        //activity. Only the recipe title is passed in, as a reference to the Recipe object cannot
        //be passed in with this method.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the intent for opening the detail activity.
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_RECIPE_PRIMARY_KEY, mRecipes.get(position).getTitle());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }
    //This Recycles the view and resets the onLongClickListener???? I am not sure what is happening
    //here.
    @Override
    public void onViewRecycled(@NonNull RecipeViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    //I need to change this name as it alludes to it being only ABC, but this is called to
    //display all the recipes accordingly of how they are pulled from the ViewModel in the fragement
    //whether it is the ABC or Region or Meal.
    void setRecipesAbc(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mRecipes has not been updated (means initially, it's null, and we can't return null)
    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        } else {
            return 0;
        }
    }

    //I think that this is used as the holder for the itemVIew. which is passed in during
    //during onCreateViewHolder. It recognizes the different components of the item view and
    //assign a menuinflater to go with the holder.
    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private final TextView recipeTitleView, recipeDescriptionView, recipeRegionView, recipeMealView;
        private final ImageView recipeImageView;

        private RecipeViewHolder(View itemView) {
            super(itemView);
            recipeTitleView = itemView.findViewById(R.id.card_title);
            recipeDescriptionView = itemView.findViewById(R.id.card_brief_description);
            recipeRegionView = itemView.findViewById(R.id.card_region);
            recipeMealView = itemView.findViewById(R.id.card_meal);
            recipeImageView = itemView.findViewById(R.id.card_imageView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            mMenuInflater.inflate(R.menu.menu_delete, menu);
        }


    }

    //Getter for recipe position.
    public Recipe getRecipeAtPosition(int position) {
        return mRecipes.get(position);
    }

}
