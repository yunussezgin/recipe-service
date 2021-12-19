package com.crediteurope.recipe.service;

import org.springframework.data.domain.Page;

import com.crediteurope.recipe.api.RecipeCreate;
import com.crediteurope.recipe.api.RecipeUpdate;
import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.core.types.Predicate;

public interface RecipeService {

	Recipe createRecipe(RecipeCreate recipeCreate) throws JsonProcessingException;

	void deleteRecipe(String id) throws NotFoundException;

	Page<Recipe> listRecipe(Integer offset, Integer limit, Predicate predicate);

	Recipe patchRecipe(String id, RecipeUpdate recipeUpdate) throws JsonProcessingException, NotFoundException;

	Recipe retrieveRecipe(String id) throws NotFoundException;

}
