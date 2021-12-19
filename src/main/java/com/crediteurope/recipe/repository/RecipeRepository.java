package com.crediteurope.recipe.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.QRecipe;
import com.crediteurope.recipe.entity.Recipe;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface RecipeRepository
		extends CrudRepository<Recipe, String>, QuerydslPredicateExecutor<Recipe>, QuerydslBinderCustomizer<QRecipe> {

	@Override
	default void customize(QuerydslBindings bindings, QRecipe root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}

}
