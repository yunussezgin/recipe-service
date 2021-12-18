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
@ApiModel(description = "Owner of the recipe.")
public class User extends BaseEntity {

	@Id
	@JsonProperty("id")
	@ApiModelProperty(required = true, value = "Unique identifier of the user entity.")
	private String id = null;

	@NotBlank
	@NotNull
	@JsonProperty("name")
	@ApiModelProperty(required = true, value = "Name of the user.")
	private String name = null;

}