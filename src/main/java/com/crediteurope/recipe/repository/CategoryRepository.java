package com.crediteurope.recipe.repository;

import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {

	Category findFirstByName(String name);
	
}
