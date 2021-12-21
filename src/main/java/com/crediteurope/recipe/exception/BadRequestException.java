package com.crediteurope.recipe.exception;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class BadRequestException extends RuntimeException {
	private String message;

	public BadRequestException(String message) {
		super(message);
		this.message = message;
	}

}
