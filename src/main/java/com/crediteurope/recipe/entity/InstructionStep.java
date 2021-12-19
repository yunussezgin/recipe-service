package com.crediteurope.recipe.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
@ApiModel(description = "The entity defines instruction steps to prepare recipe.")
public class InstructionStep extends BaseEntity {

	@Id
	@JsonProperty("id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@ApiModelProperty(required = true, value = "Unique identifier of the instruction step entity.")
	private String id = null;

	@NotNull
	@JsonProperty("stepNumber")
	@ApiModelProperty(required = true, value = "Step number of instruction.")
	private Integer stepNumber = null;

	@Valid
	@NotNull
	@ApiModelProperty(value = "Instruction reference.")
	@JoinColumn(name = "instruction_id", foreignKey = @ForeignKey(name = "fk_instruction_step_instruction"))
	@JsonProperty("instruction")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Instruction.class)
	private Instruction instruction = null;

}
