package com.crediteurope.recipe.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.QUsers;
import com.crediteurope.recipe.entity.Users;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface UserRepository
		extends CrudRepository<Users, String>, QuerydslPredicateExecutor<Users>, QuerydslBinderCustomizer<QUsers> {

	@Override
	default void customize(QuerydslBindings bindings, QUsers root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}

}
