package com.crediteurope.recipe.api;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
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
@ApiModel(value = "Recipe_Update", description = "The entity provides to update recipe record.")
public class RecipeUpdate {

	@JsonProperty("name")
	@ApiModelProperty(required = true, value = "Name of the ingredient.")
	private String name = null;

	@JsonProperty("description")
	@ApiModelProperty(required = true, value = "Description of the instruction.")
	private String description = null;

	@JsonProperty("cookTime")
	@ApiModelProperty(required = true, value = "Recipe cooking duration.")
	private Integer cookTime = null;

	@JsonProperty("prepTime")
	@ApiModelProperty(required = true, value = "Recipe total preparation time.")
	private Integer prepTime = null;

	@JsonProperty("serving")
	@ApiModelProperty(required = true, value = "Recipe is suitable for how many people.")
	private Integer servings = null;

	@JsonProperty("vegetarianFlag")
	@ApiModelProperty(required = true, value = "The recipe is suitable for vegetarians.")
	private Boolean vegetarianFlag = null;

	@Valid
	@JsonProperty("category")
	private Category category = null;

	@Valid
	@JsonProperty("user")
	@ManyToOne(fetch = FetchType.EAGER)
	private User user = null;

	@Valid
	@Size(min = 1)
	@ApiModelProperty(value = "Recipe instruction reference.")
	@JsonProperty("recipeInstruction")
	private List<RecipeInstruction> recipeInstruction;

	@Valid
	@Size(min = 1)
	@ApiModelProperty(value = "Recipe ingredient reference.")
	@JsonProperty("recipeIngredient")
	private List<RecipeIngredient> recipeIngredient;

	@Valid
	@ApiModelProperty(value = "Image reference.")
	@JsonProperty("image")
	private List<Image> image;

}
