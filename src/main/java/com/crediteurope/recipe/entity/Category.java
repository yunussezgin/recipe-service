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
@ApiModel(description = "The category defined for recipes.")
public class Category extends BaseEntity {

	@Id
	@JsonIgnore
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the category entity.")
	private String id = null;

	@NotBlank
	@NotNull
	@Size(max = 50)
	@Column(length = 50)
	@JsonProperty("name")
	@ApiModelProperty(required = true, value = "Name of the category.")
	private String name = null;

	@Valid
	@JsonBackReference
	@ApiModelProperty(value = "Recipe reference.")
	@JsonProperty("recipe")
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Recipe.class)
	private List<Recipe> recipe = null;

	public void assignParentToChilds() {
		if (getRecipe() != null) {
			for (Recipe entity : getRecipe()) {
				entity.setCategory(this);
			}
		}
	}

}
