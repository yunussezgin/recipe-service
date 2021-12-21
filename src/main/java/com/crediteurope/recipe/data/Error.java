package com.crediteurope.recipe.data;

import org.springframework.validation.annotation.Validated;

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
@ApiModel(description = "Used when an API throws an Error (3xx, 4xx, 5xx)")
public class Error {

	@ApiModelProperty(required = true, value = "Error code.")
	@JsonProperty("code")
	private String code = null;

	@ApiModelProperty(required = true, value = "Error detail.")
	@JsonProperty("message")
	private String message = null;

}
