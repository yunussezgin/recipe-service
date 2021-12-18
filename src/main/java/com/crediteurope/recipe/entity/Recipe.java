package com.crediteurope.recipe.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
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
@ApiModel(description = "A user defines the recipe.")
public class Recipe extends BaseEntity {

	@Id
	@JsonProperty("id")
	@ApiModelProperty(required = true, value = "Unique identifier of the recipe entity.")
	private String id = null;

	@NotBlank
	@NotNull
	@JsonProperty("name")
	@ApiModelProperty(required = true, value = "Name of the ingredient.")
	private String name = null;

	@NotBlank
	@NotNull
	@JsonProperty("description")
	@ApiModelProperty(required = true, value = "Description of the instruction.")
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
    private Integer servings = null;
    
	@NotNull
	@JsonProperty("vegetarianFlag")
	@ApiModelProperty(required = true, value = "The recipe is suitable for vegetarians.")
	private Boolean vegetarianFlag = null;

}
