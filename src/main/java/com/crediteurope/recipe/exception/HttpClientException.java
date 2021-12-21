package com.crediteurope.recipe.exception;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class HttpClientException extends RuntimeException {
	private String message;

	public HttpClientException(String message) {
		super(message);
		this.message = message;
	}

}
