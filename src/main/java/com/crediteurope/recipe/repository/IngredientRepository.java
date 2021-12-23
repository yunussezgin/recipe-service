package com.crediteurope.recipe.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.Ingredient;
import com.crediteurope.recipe.entity.QIngredient;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface IngredientRepository extends CrudRepository<Ingredient, String>, QuerydslPredicateExecutor<Ingredient>,
		QuerydslBinderCustomizer<QIngredient> {

	@Override
	default void customize(QuerydslBindings bindings, QIngredient root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}

}
