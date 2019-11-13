package com.iafnstudios.springrecipeapp.convertor;


import com.iafnstudios.springrecipeapp.command.RecipeCommand;
import com.iafnstudios.springrecipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NoteCommandToNote noteCommandToNote;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, NoteCommandToNote noteCommandToNote) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.noteCommandToNote = noteCommandToNote;
    }


    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null){
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setNote(noteCommandToNote.convert(recipeCommand.getNoteCommand()));

        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size()>0){
            recipeCommand.getIngredients().forEach(
                    ingredientCommand -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand))
            );
        }

        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size()>0){
            recipeCommand.getCategories().forEach(categoryCommand ->
                    recipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));
        }

        return recipe;
    }
}
