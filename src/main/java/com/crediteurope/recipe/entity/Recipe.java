package com.crediteurope.recipe.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class Recipe extends BaseEntity {

	@Id
	@JsonProperty("id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the recipe entity.")
	private String id = null;

	@NotBlank
	@NotNull
	@JsonProperty("name")
	@ApiModelProperty(required = true, value = "Name of the recipe.")
	private String name = null;

	@NotBlank
	@NotNull
	@JsonProperty("description")
	@ApiModelProperty(required = true, value = "Description of the recipe.")
	private String description = null;

	@NotNull
	@JsonProperty("cookTime")
	@ApiModelProperty(required = true, value = "Recipe cooking duration.")
	private Integer cookTime = null;

	@NotNull
	@JsonProperty("prepTime")
	@ApiModelProperty(required = true, value = "Recipe total preparation time.")
	private Integer prepTime = null;

	@NotNull
	@JsonProperty("serving")
	@ApiModelProperty(required = true, value = "Recipe is suitable for how many people.")
	private Integer serving = null;

	@Valid
	@NotNull
	@JsonProperty("vegetarianFlag")
	@ApiModelProperty(required = true, value = "The recipe is suitable for vegetarians.")
	private Boolean vegetarianFlag = null;

	@Valid
	@ApiModelProperty(value = "Category reference.")
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_recipe_category"))
	@JsonProperty("category")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Category.class)
	private Category category = null;

	@Valid
	@NotNull
	@ApiModelProperty(value = "User reference.")
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_recipe_user"))
	@JsonProperty("user")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Users.class)
	private Users user = null;

	@Valid
	@ApiModelProperty(value = "Recipe instruction reference.")
	@JsonProperty("recipeInstruction")
	@JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "fk_recipe_instruction_recipe"))
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RecipeInstruction.class)
	private List<RecipeInstruction> recipeInstruction;

	@Valid
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
	
}
