package com.crediteurope.recipe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crediteurope.recipe.entity.Category;
import com.crediteurope.recipe.entity.Ingredient;
import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.entity.RecipeIngredient;
import com.crediteurope.recipe.entity.User;
import com.crediteurope.recipe.repository.CategoryRepository;
import com.crediteurope.recipe.repository.IngredientRepository;
import com.crediteurope.recipe.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubResourceService {

	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	private final IngredientRepository ingredientRepository;

	public User findOrCreateUser(Recipe recipe) {
		User user = userRepository.findFirstByName(recipe.getUser().getName());
		if (user == null)
			user = userRepository.save(recipe.getUser());
		return user;
	}

	public Category findOrCreateCategory(Recipe recipe) {
		Category category = categoryRepository.findFirstByName(recipe.getCategory().getName());
		if (category == null)
			category = categoryRepository.save(recipe.getCategory());
		return category;
	}

	public List<RecipeIngredient> findOrCreateIngredient(Recipe recipe) {
		List<RecipeIngredient> recipeIngredientList = recipe.getRecipeIngredient();
		for (RecipeIngredient recipeIngredient : recipeIngredientList) {
			Ingredient ingredient = ingredientRepository.findFirstByName(recipeIngredient.getIngredient().getName());
			if (ingredient == null)
				ingredient = ingredientRepository.save(recipeIngredient.getIngredient());
			recipeIngredient.setIngredient(ingredient);
		}
		return recipeIngredientList;
	}
}
