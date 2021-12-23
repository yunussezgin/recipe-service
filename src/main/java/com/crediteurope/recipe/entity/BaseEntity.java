package com.crediteurope.recipe.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
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

	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@ApiModelProperty(name = "createdDate", hidden = true)
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate;

	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@ApiModelProperty(name = "updatedDate", hidden = true)
	@Column(name = "updated_date")
	@LastModifiedDate
	private LocalDateTime updatedDate;

}
