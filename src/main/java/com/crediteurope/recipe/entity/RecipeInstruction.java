package com.crediteurope.recipe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@ApiModel(description = "The entity defines recipe steps to prepare recipe.")
public class RecipeInstruction extends BaseEntity {

	@Id
	@JsonIgnore
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the recipe direction entity.")
	private String id = null;

	@NotNull
	@JsonProperty("stepNumber")
	@ApiModelProperty(required = true, value = "Step number of instruction.")
	private Integer stepNumber = null;

	@NotBlank
	@NotNull
	@Size(max = 255)
	@Column(length = 255)
	@JsonProperty("description")
	@ApiModelProperty(required = true, value = "Description of the instruction.")
	private String description = null;

}
