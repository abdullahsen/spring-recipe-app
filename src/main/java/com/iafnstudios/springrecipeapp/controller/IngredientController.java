package com.iafnstudios.springrecipeapp.controller;


import com.iafnstudios.springrecipeapp.service.IngredientService;
import com.iafnstudios.springrecipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientList(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: "+ recipeId);

        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId,
                                       Model model){

        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
                        ,Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";

    }
}
