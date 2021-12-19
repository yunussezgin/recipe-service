package com.crediteurope.recipe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.crediteurope.recipe.api.RecipeApi;
import com.crediteurope.recipe.api.RecipeCreate;
import com.crediteurope.recipe.api.RecipeUpdate;
import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.exception.NotFoundException;
import com.crediteurope.recipe.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RecipeController implements RecipeApi {

	private final RecipeService recipeService;

	@Override
	public ResponseEntity<Recipe> createRecipe(@Valid RecipeCreate recipeCreate) throws JsonProcessingException {
		Recipe recipe = recipeService.createRecipe(recipeCreate);
		return new ResponseEntity<>(recipe, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> deleteRecipe(String id) throws NotFoundException {
		recipeService.deleteRecipe(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<List<Recipe>> listRecipe(@Valid Integer offset, @Valid Integer limit, @Valid String sort,
			HttpServletResponse response, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Recipe> patchRecipe(String id, @Valid RecipeUpdate recipeUpdate)
			throws JsonProcessingException, NotFoundException {
		Recipe recipe = recipeService.patchRecipe(id, recipeUpdate);
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Recipe> retrieveRecipe(String id) throws NotFoundException {
		Recipe recipe = recipeService.retrieveRecipe(id);
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}

}
