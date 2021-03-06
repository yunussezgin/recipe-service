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

	@Size(max = 100)
	@JsonProperty("name")
	@ApiModelProperty(value = "Name of the recipe.")
	private String name = null;

	@Size(max = 255)
	@JsonProperty("description")
	@ApiModelProperty(value = "Description of the recipe.")
	private String description = null;

	@JsonProperty("cookTime")
	@ApiModelProperty(value = "Recipe cooking duration.")
	private Integer cookTime = null;

	@JsonProperty("prepTime")
	@ApiModelProperty( value = "Recipe preparation during.")
	private Integer prepTime = null;

	@JsonProperty("serving")
	@ApiModelProperty(value = "Recipe is suitable for how many people.")
	private Integer serving = null;

	@JsonProperty("isVegetarian")
	@ApiModelProperty(value = "The recipe is suitable for vegetarians or not.")
	private Boolean isVegetarian = null;

	@Valid
	@ApiModelProperty(value = "Category reference.")
	@JsonProperty("category")
	private Category category = null;

	@Valid
	@ApiModelProperty(value = "User reference.")
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
