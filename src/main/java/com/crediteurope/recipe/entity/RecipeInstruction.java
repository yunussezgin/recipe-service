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
@ApiModel(description = "A relation between recipe and instruction.")
public class RecipeInstruction extends BaseEntity {

	@Id
	@JsonProperty("id")
	@ApiModelProperty(required = true, value = "Unique identifier of the recipeinstruction entity.")
	private String id = null;

	@NotNull
	@JsonProperty("stepNumber")
	@ApiModelProperty(required = true, value = "Step number of instruction.")
	private Integer stepNumber = null;

}
