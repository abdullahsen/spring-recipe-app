package com.iafnstudios.springrecipeapp.service;

import com.iafnstudios.springrecipeapp.command.IngredientCommand;
import com.iafnstudios.springrecipeapp.convertor.IngredientToIngredientCommand;
import com.iafnstudios.springrecipeapp.domain.Ingredient;
import com.iafnstudios.springrecipeapp.domain.Recipe;
import com.iafnstudios.springrecipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (!recipe.isPresent()){
            log.error("Recipe is not found.");
            throw new RuntimeException("Recipe is not found.");
        }

        Optional<IngredientCommand> ingredientCommandOptional = recipe.get().getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()){
            log.error("Ingredient Id not found");
        }

        return ingredientCommandOptional.get();
    }
}
