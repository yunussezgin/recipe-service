package com.crediteurope.recipe.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@ApiModel(description = "The ingredient to prepare a recipe.")
public class Ingredient extends BaseEntity {

	@Id
	@JsonIgnore
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the ingredient entity.")
	private String id = null;

	@NotBlank
	@NotNull
	@Size(max = 100)
	@Column(length = 100)
	@JsonProperty("name")
	@ApiModelProperty(value = "Name of the ingredient.")
	private String name = null;

	@Valid
	@Size(min = 1)
	@JsonBackReference
	@ApiModelProperty(value = "Ingredient reference.")
	@JsonProperty("recipeIngredient")
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RecipeIngredient.class)
	private List<RecipeIngredient> recipeIngredient = null;

	public void assignParentToChilds() {
		if (getRecipeIngredient() != null) {
			for (RecipeIngredient entity : getRecipeIngredient()) {
				entity.setIngredient(this);
			}
		}
	}

}
