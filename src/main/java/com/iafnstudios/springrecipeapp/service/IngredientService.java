package com.iafnstudios.springrecipeapp.service;

import com.iafnstudios.springrecipeapp.command.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
