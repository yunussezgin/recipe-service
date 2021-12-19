package com.crediteurope.recipe.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.crediteurope.recipe.entity.Category;
import com.crediteurope.recipe.entity.IngredientScale;
import com.crediteurope.recipe.entity.InstructionStep;
import com.crediteurope.recipe.entity.Users;
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

	@NotNull
	@JsonProperty("vegetarianFlag")
	@ApiModelProperty(required = true, value = "The recipe is suitable for vegetarians.")
	private Boolean vegetarianFlag = null;

	@Valid
	@NotNull
	@ApiModelProperty(value = "Category reference.")
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_category_recipe"))
	@JsonProperty("category")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Category.class)
	private Category category = null;

	@Valid
	@NotNull
	@ApiModelProperty(value = "User reference.")
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_recipe"))
	@JsonProperty("user")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Users.class)
	private Users user = null;

	@Valid
	@ApiModelProperty(value = "Instruction step reference.")
	@JoinColumn(name = "instructionStep_id", foreignKey = @ForeignKey(name = "fk_instruction_step_recipe"))
	@JsonProperty("instructionStep")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = InstructionStep.class)
	private List<InstructionStep> instructionStep = new ArrayList<>();

	@Valid
	@ApiModelProperty(value = "Ingredient scale reference.")
	@JoinColumn(name = "ingredientScale_id", foreignKey = @ForeignKey(name = "fk_ingredient_scale_recipe"))
	@JsonProperty("ingredientScale")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = IngredientScale.class)
	private List<IngredientScale> ingredientScale = new ArrayList<>();

}
