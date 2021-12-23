package com.crediteurope.recipe.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.Category;
import com.crediteurope.recipe.entity.QCategory;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface CategoryRepository extends CrudRepository<Category, String>, QuerydslPredicateExecutor<Category>,
		QuerydslBinderCustomizer<QCategory> {

	@Override
	default void customize(QuerydslBindings bindings, QCategory root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}

}
