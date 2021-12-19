package com.crediteurope.recipe.repository;

import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
