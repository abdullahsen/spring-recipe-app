package com.iafnstudios.springrecipeapp.controller;

import com.iafnstudios.springrecipeapp.command.RecipeCommand;
import com.iafnstudios.springrecipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe",recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/recipe/new")
    public String createRecipe(Model model){

        model.addAttribute("recipe",new RecipeCommand());

        return "recipe/recipeForm";
    }

    @PostMapping("recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand){

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/"+savedRecipeCommand.getId()+"/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeForm";

    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id){
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
