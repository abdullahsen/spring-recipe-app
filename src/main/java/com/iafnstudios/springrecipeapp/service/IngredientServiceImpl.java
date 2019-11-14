package com.iafnstudios.springrecipeapp.service;

import com.iafnstudios.springrecipeapp.command.IngredientCommand;
import com.iafnstudios.springrecipeapp.convertor.IngredientCommandToIngredient;
import com.iafnstudios.springrecipeapp.convertor.IngredientToIngredientCommand;
import com.iafnstudios.springrecipeapp.domain.Ingredient;
import com.iafnstudios.springrecipeapp.domain.Recipe;
import com.iafnstudios.springrecipeapp.repository.RecipeRepository;
import com.iafnstudios.springrecipeapp.repository.UnitOfMeasureRepository;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Transactional
    @Override
    public IngredientCommand savedIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()){
            log.error("Recipe is not found");
            return new IngredientCommand();
        }else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow( () -> new RuntimeException("Unit Of Measure is not found.")));

            }else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional =
                    savedRecipe
                            .getIngredients()
                            .stream()
                            .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                            .findFirst();

            if (!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

           return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        log.debug("Deleting ingredient: " + recipeId + " : " + ingredientId);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("Recipe is found");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()){
                log.debug("Ingredient is found");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);
                recipeRepository.save(recipe);
            }else {
                log.debug("Ingredient is not found");
            }

        }else {
            log.debug("Recipe is not found");
        }
    }
}
