package com.iafnstudios.springrecipeapp.controller;

import com.iafnstudios.springrecipeapp.domain.Category;
import com.iafnstudios.springrecipeapp.domain.UnitOfMeasure;
import com.iafnstudios.springrecipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

   private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String getIndexPage(Model model){
        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }

}
