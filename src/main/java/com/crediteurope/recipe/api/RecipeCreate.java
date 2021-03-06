package com.crediteurope.recipe.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.crediteurope.recipe.entity.Category;
import com.crediteurope.recipe.entity.Image;
import com.crediteurope.recipe.entity.RecipeIngredient;
import com.crediteurope.recipe.entity.RecipeInstruction;
import com.crediteurope.recipe.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Recipe_Create", description = "The entity provides to create recipe data.")
public class RecipeCreate {

	@NotBlank
	@NotNull
	@Size(max = 100)
	@JsonProperty("name")
	@ApiModelProperty(required = true, value = "Name of the recipe.")
	private String name = null;

	@Size(max = 255)
	@JsonProperty("description")
	@ApiModelProperty(required = true, value = "Description of the recipe.")
	private String description = null;

	@JsonProperty("cookTime")
	@ApiModelProperty(required = true, value = "Recipe cooking duration.")
	private Integer cookTime = null;

	@NotNull
	@JsonProperty("prepTime")
	@ApiModelProperty(required = true, value = "Recipe preparation during.")
	private Integer prepTime = null;

	@NotNull
	@JsonProperty("serving")
	@ApiModelProperty(required = true, value = "Recipe is suitable for how many people.")
	private Integer serving = null;

	@JsonProperty("isVegetarian")
	@ApiModelProperty(value = "The recipe is suitable for vegetarians or not.")
	private Boolean isVegetarian = null;

	@Valid
	@NotNull
	@ApiModelProperty(required = true, value = "Category reference.")
	@JsonProperty("category")
	private Category category = null;

	@Valid
	@NotNull
	@ApiModelProperty(required = true, value = "User reference.")
	@JsonProperty("user")
	private User user = null;

	@Valid
	@NotNull
	@Size(min = 1)
	@ApiModelProperty(required = true, value = "Recipe instruction reference.")
	@JsonProperty("recipeInstruction")
	private List<RecipeInstruction> recipeInstruction = new ArrayList<>();

	@Valid
	@NotNull
	@Size(min = 1)
	@ApiModelProperty(required = true, value = "Recipe ingredient reference.")
	@JsonProperty("recipeIngredient")
	private List<RecipeIngredient> recipeIngredient = new ArrayList<>();

	@Valid
	@ApiModelProperty(value = "Image reference.")
	@JsonProperty("image")
	private List<Image> image;

}
