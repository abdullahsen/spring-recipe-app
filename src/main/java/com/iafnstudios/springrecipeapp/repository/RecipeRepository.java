package com.iafnstudios.springrecipeapp.repository;

import com.iafnstudios.springrecipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
