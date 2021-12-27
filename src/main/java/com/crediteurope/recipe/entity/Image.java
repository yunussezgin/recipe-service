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
@ApiModel(description = "Recipe image.")
public class Image extends BaseEntity {

	@Id
	@JsonProperty("id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the image entity.")
	private String id = null;

	@Size(max = 50)
	@Column(length = 50)
	@JsonProperty("name")
	@ApiModelProperty(value = "Name of the image.")
	private String name = null;

	@NotBlank
	@NotNull
	@Size(max = 255)
	@Column(length = 255)
	@JsonProperty("url")
	@ApiModelProperty(value = "Url of the image.")
	private String url = null;

}
