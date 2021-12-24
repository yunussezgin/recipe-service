package com.crediteurope.recipe.repository;

import org.springframework.data.repository.CrudRepository;

import com.crediteurope.recipe.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

	User findFirstByName(String name);

}
