package com.crediteurope.recipe.exception;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class ApiException extends Exception {
	private String code;
	private String message;

	public ApiException(String message) {
		super(message);
		this.message = message;
	}

	public ApiException(String code, String message) {
		super(code.concat(StringUtils.SPACE).concat(message));
		this.code = code;
		this.message = message;
	}

}
