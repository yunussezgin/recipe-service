package com.crediteurope.recipe.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "A relation between recipe and ingredient.")
public class RecipeIngredient extends BaseEntity {

	@Id
	@JsonProperty("id")
	@ApiModelProperty(required = true, value = "Unique identifier of the recipeingredient entity.")
	private String id = null;

	@NotNull
	@JsonProperty("amount")
	@ApiModelProperty(required = true, value = "Numeric value in a given unit.")
	private Float amount;

	@NotNull
	@JsonProperty("unit")
	@ApiModelProperty(value = "Unit of used ingredients.")
	private String unit = null;

}
