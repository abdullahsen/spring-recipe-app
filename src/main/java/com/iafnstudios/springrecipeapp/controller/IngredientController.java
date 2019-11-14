package com.iafnstudios.springrecipeapp.controller;



import com.iafnstudios.springrecipeapp.command.IngredientCommand;
import com.iafnstudios.springrecipeapp.command.RecipeCommand;
import com.iafnstudios.springrecipeapp.command.UnitOfMeasureCommand;
import com.iafnstudios.springrecipeapp.service.IngredientService;
import com.iafnstudios.springrecipeapp.service.RecipeService;
import com.iafnstudios.springrecipeapp.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientList(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: "+ recipeId);

        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId,
                                       Model model){

        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
                        ,Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String createRecipeIngredient(@PathVariable String recipeId, Model model){

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient",ingredientCommand);

        //init Unit Of Measure
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList",unitOfMeasureService.listAllUnitOfMeasures());

        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId,
                                         Model model){

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUnitOfMeasures() );
        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateRecipeIngredient(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredientCommand = ingredientService.savedIngredientCommand(ingredientCommand);
        log.debug("saved recipe id: "+savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id: "+savedIngredientCommand.getId());
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId){
        log.debug("Deleting ingredient id: " + ingredientId);
        ingredientService.deleteById(Long.valueOf(recipeId),Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
