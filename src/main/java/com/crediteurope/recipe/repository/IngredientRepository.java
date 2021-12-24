package com.crediteurope.recipe.repository;

import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

	Ingredient findFirstByName(String name);

}
