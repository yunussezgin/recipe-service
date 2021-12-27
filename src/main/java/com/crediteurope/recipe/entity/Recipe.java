package com.crediteurope.recipe.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
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
@ApiModel(description = "A user defines the recipe.")
public class Recipe extends SubEntity {

	@Id
	@JsonProperty("id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the recipe entity.")
	private String id = null;

	@NotBlank
	@NotNull
	@Column(length = 100)
	@JsonProperty("name")
	@ApiModelProperty(required = true, value = "Name of the recipe.")
	private String name = null;

	@Column(length = 255)
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

	@Valid
	@JsonProperty("isVegetarian")
	@Column(columnDefinition = "boolean default false")
	@ApiModelProperty(value = "The recipe is suitable for vegetarians or not.")
	private Boolean isVegetarian = null;

	@Valid
	@NotNull
	@ApiModelProperty(value = "Category reference.")
	@JsonProperty("category")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_recipe_category"))
	private Category category = null;

	@Valid
	@NotNull
	@ApiModelProperty(value = "User reference.")
	@JsonProperty("user")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_recipe_user"))
	private User user = null;

	@Valid
	@NotNull
	@Size(min = 1)
	@ApiModelProperty(value = "Recipe instruction reference.")
	@JsonProperty("recipeInstruction")
	@JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "fk_recipe_instruction_recipe"))
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RecipeInstruction.class)
	private List<RecipeInstruction> recipeInstruction;

	@Valid
	@NotNull
	@Size(min = 1)
	@ApiModelProperty(value = "Recipe ingredient reference.")
	@JsonProperty("recipeIngredient")
	@JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "fk_recipe_ingredient_recipe"))
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RecipeIngredient.class)
	private List<RecipeIngredient> recipeIngredient;

	@Valid
	@ApiModelProperty(value = "Image reference.")
	@JsonProperty("image")
	@JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "fk_image_recipe"))
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Image.class)
	private List<Image> image;

	public void assignParentToChilds() {
		if (getCategory() != null) {
			getCategory().assignParentToChilds();
		}

		if (getUser() != null) {
			getUser().assignParentToChilds();
		}

		if (getRecipeIngredient() != null) {
			for (RecipeIngredient entity : getRecipeIngredient()) {
				entity.assignParentToChilds();
			}
		}
	}

}
