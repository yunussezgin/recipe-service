package com.crediteurope.recipe.entity;

import java.time.OffsetDateTime;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseEntity {

	@ApiModelProperty(name = "createdDate")
	@CreatedDate
	private OffsetDateTime createdDate;

	@ApiModelProperty(name = "createdBy")
	@CreatedBy
	private String createdBy;
}
