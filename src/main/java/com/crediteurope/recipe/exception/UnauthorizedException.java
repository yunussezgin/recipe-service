package com.crediteurope.recipe.exception;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class UnauthorizedException extends RuntimeException {
	private String message;

	public UnauthorizedException(String message) {
		super(message);
		this.message = message;
	}

}
