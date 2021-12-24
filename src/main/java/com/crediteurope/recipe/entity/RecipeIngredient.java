package com.crediteurope.recipe.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel(description = "The entity defines incredient scale to prepare recipe.")
public class RecipeIngredient {

	@Id
	@JsonIgnore
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the ingredient scale entity.")
	private String id = null;

	@NotNull
	@JsonProperty("amount")
	@ApiModelProperty(required = true, value = "Numeric value in a given unit.")
	private Float amount = null;

	@NotBlank
	@NotNull
	@JsonProperty("unit")
	@ApiModelProperty(value = "Unit of used ingredients.")
	private String unit = null;

	@Valid
	@ApiModelProperty(value = "Ingredient reference.")
	@JsonProperty("ingredient")
	@JoinColumn(name = "ingredient_id", foreignKey = @ForeignKey(name = "fk_ingredient_recipe_ingredient"))
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Ingredient.class)
	private Ingredient ingredient;

}
