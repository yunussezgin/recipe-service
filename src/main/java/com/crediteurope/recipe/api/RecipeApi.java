package com.crediteurope.recipe.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crediteurope.recipe.entity.Recipe;
import com.crediteurope.recipe.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@Api(value = "recipe", description = "Recipe API")
public interface RecipeApi {

	@ApiOperation(value = "Creates a Recipe", nickname = "createRecipe", notes = "This operation creates a Recipe entity.", response = Recipe.class, tags = {
			"recipe", })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = Recipe.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
			@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class),
			@ApiResponse(code = 422, message = "Unprocessable Entity", response = Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/recipe", produces = { "application/json;charset=utf-8" }, consumes = {
			"application/json;charset=utf-8" }, method = RequestMethod.POST)
	ResponseEntity<Recipe> createRecipe(
			@ApiParam(value = "The Recipe to be created", required = true) @Valid @RequestBody RecipeCreate recipeCreate)
			throws JsonProcessingException;

	@ApiOperation(value = "Deletes a Recipe", nickname = "deleteRecipe", notes = "This operation deletes a Recipe entity.", tags = {
			"recipe", })
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Deleted"),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
			@ApiResponse(code = 404, message = "Not Found", response = Error.class),
			@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/recipe/{id}", produces = { "application/json;charset=utf-8" }, consumes = {
			"application/json;charset=utf-8" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteRecipe(
			@ApiParam(value = "Identifier of the Recipe", required = true) @PathVariable("id") String id)
			throws NotFoundException;

	@ApiOperation(value = "List or find Recipe objects", nickname = "listRecipe", notes = "This operation list or find Recipe entities", response = Recipe.class, responseContainer = "List", tags = {
			"recipe", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Recipe.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
			@ApiResponse(code = 404, message = "Not Found", response = Error.class),
			@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/recipe", produces = { "application/json;charset=utf-8" }, method = RequestMethod.GET)
	ResponseEntity<List<Recipe>> listRecipe(
			@ApiParam(value = "Requested index for start of resources to be provided in response") @Valid @RequestParam(value = "offset", required = false) Integer offset,
			@ApiParam(value = "Requested number of resources to be provided in response") @Valid @RequestParam(value = "limit", required = false) Integer limit,
			@ApiParam(value = "Query parameter to filter records") @QuerydslPredicate(root = Recipe.class) Predicate predicate);

	@ApiOperation(value = "Updates partially a Recipe", nickname = "patchRecipe", notes = "This operation updates partially a Recipe entity.", response = Recipe.class, tags = {
			"recipe", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated", response = Recipe.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
			@ApiResponse(code = 404, message = "Not Found", response = Error.class),
			@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/recipe/{id}", produces = { "application/json;charset=utf-8" }, consumes = {
			"application/json;charset=utf-8" }, method = RequestMethod.PATCH)
	ResponseEntity<Recipe> patchRecipe(
			@ApiParam(value = "Identifier of the Recipe", required = true) @PathVariable("id") String id,
			@ApiParam(value = "The Recipe to be updated", required = true) @Valid @RequestBody RecipeUpdate recipeUpdate)
			throws JsonProcessingException, NotFoundException;

	@ApiOperation(value = "Retrieves a Recipe by ID", nickname = "retrieveRecipe", notes = "This operation retrieves a Recipe entity.", response = Recipe.class, tags = {
			"recipe", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Recipe.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
			@ApiResponse(code = 404, message = "Not Found", response = Error.class),
			@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/recipe/{id}", produces = { "application/json;charset=utf-8" }, method = RequestMethod.GET)
	ResponseEntity<Recipe> retrieveRecipe(
			@ApiParam(value = "Identifier of the Recipe", required = true) @PathVariable("id") String id)
			throws NotFoundException;

}
