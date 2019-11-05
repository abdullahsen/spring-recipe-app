package com.iafnstudios.springrecipeapp.service;

import com.iafnstudios.springrecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
