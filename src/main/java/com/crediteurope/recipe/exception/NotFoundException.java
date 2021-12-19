package com.crediteurope.recipe.exception;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class NotFoundException extends Exception {
	private String message;

	public NotFoundException(String message) {
		super(message);
		this.message = message;
	}

}
