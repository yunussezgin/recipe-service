package com.crediteurope.recipe.entity;

import java.time.OffsetDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@ApiModelProperty(name = "createdDate", hidden = true)
	@CreatedDate
	private OffsetDateTime createdDate;

	@ApiModelProperty(name = "updatedDate", hidden = true)
	@LastModifiedDate
	private OffsetDateTime updatedDate;

}
