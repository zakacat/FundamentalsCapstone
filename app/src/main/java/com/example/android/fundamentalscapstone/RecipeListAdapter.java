package com.example.android.fundamentalscapstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes;

    RecipeListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (mRecipes!= null) {
            Recipe current = mRecipes.get(position);
            holder.recipeTitleView.setText(current.getTitle());
            holder.recipeDescriptionView.setText(current.getBriefDescription());

            //I may need to copy this switch block several times...
            String region;
            switch (current.getRegionOfOrigin()){
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
                default :
                    meal = "no data";
            }
            holder.recipeMealView.setText(meal);
        } else {
            //In case the data is not ready yet
            holder.recipeTitleView.setText("no data");
            holder.recipeDescriptionView.setText("no data");
            holder.recipeRegionView.setText("no data");
            holder.recipeMealView.setText("no data");
        }

    }

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

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final TextView recipeTitleView, recipeDescriptionView, recipeRegionView, recipeMealView;

        private RecipeViewHolder(View itemView) {
            super(itemView);
            recipeTitleView = itemView.findViewById(R.id.card_title);
            recipeDescriptionView = itemView.findViewById(R.id.card_brief_description);
            recipeRegionView = itemView.findViewById(R.id.card_region);
            recipeMealView = itemView.findViewById(R.id.card_meal);
        }
    }
}