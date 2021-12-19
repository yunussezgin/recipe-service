package com.crediteurope.recipe.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crediteurope.recipe.api.RecipeCreate;
import com.crediteurope.recipe.api.RecipeUpdate;
import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.exception.NotFoundException;
import com.crediteurope.recipe.repository.RecipeRepository;
import com.crediteurope.recipe.util.Constant;
import com.crediteurope.recipe.util.JsonMergePatcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final ObjectMapper objectMapper;
	private final JsonMergePatcher jsonMergePatcher;

	@Override
	public Recipe createRecipe(RecipeCreate recipeCreate) throws JsonProcessingException {
		log.info("RecipeService -> createRecipe started! request: {}", objectMapper.writeValueAsString(recipeCreate));
		Recipe recipe = new Recipe();
		BeanUtils.copyProperties(recipeCreate, recipe);
		recipe = recipeRepository.save(recipe);
		log.info("RecipeService -> createRecipe completed! response: {}", objectMapper.writeValueAsString(recipe));
		return recipe;
	}

	@Override
	public void deleteRecipe(String id) throws NotFoundException {
		log.info("RecipeService -> deleteRecipe started! id: {}", id);
		recipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(Constant.NOT_FOUND_EXCEPTION_MESSAGE, id)));
		recipeRepository.deleteById(id);
		log.info("RecipeService -> deleteRecipe completed!");
	}

	@Override
	public Page<Recipe> listRecipe(Integer offset, Integer limit, String sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Recipe patchRecipe(String id, RecipeUpdate recipeUpdate) throws JsonProcessingException, NotFoundException {
		log.info("RecipeService -> patchRecipe started! request: {}", objectMapper.writeValueAsString(recipeUpdate));

		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(Constant.NOT_FOUND_EXCEPTION_MESSAGE, id)));

		String serializedBody = objectMapper.writeValueAsString(recipeUpdate);
		Recipe recipeMerged = jsonMergePatcher.mergePatch(serializedBody, recipe);
		recipe = recipeRepository.save(recipeMerged);

		log.info("RecipeService -> patchRecipe completed! response: {}", objectMapper.writeValueAsString(recipe));
		return recipe;
	}

	@Override
	public Recipe retrieveRecipe(String id) throws NotFoundException {
		log.info("RecipeService -> retrieveRecipe started! id: {}", id);
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(Constant.NOT_FOUND_EXCEPTION_MESSAGE, id)));
		log.info("RecipeService -> retrieveRecipe completed!");
		return recipe;
	}

}
