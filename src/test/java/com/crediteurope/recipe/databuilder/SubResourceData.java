package com.crediteurope.recipe.databuilder;

import org.springframework.stereotype.Component;

import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.service.SubResourceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubResourceData {

	private final SubResourceService subResourceService;

	public void prepareSubResources(Recipe recipe) {
		recipe.setUser(subResourceService.findOrCreateUser(recipe));
		recipe.setCategory(subResourceService.findOrCreateCategory(recipe));
		recipe.setRecipeIngredient(subResourceService.findOrCreateIngredient(recipe));
		recipe.assignParentToChilds();
	}

}
