package com.iafnstudios.springrecipeapp.service;

import com.iafnstudios.springrecipeapp.command.RecipeCommand;
import com.iafnstudios.springrecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
