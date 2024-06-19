package com.project.smartfrigde.model;

import java.io.Serializable;
import java.util.List;

public class FoodRecommend implements Serializable {
    private String name;
    private String description;
    private List<RecipeStep> recipe;

    public FoodRecommend() {
    }

    public FoodRecommend(String name, String description, List<RecipeStep> recipe) {
        this.name = name;
        this.description = description;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RecipeStep> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<RecipeStep> recipe) {
        this.recipe = recipe;
    }
}


