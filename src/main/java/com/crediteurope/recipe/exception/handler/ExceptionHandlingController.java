package com.crediteurope.recipe.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import com.crediteurope.recipe.data.Error;
import com.crediteurope.recipe.exception.ApiException;
import com.crediteurope.recipe.exception.BadRequestException;
import com.crediteurope.recipe.exception.NotFoundException;
import com.crediteurope.recipe.exception.UnauthorizedException;
import com.crediteurope.recipe.util.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Error> handleNotFoundValidationException(NotFoundException e, HttpServletRequest request,
			HttpServletResponse response) {
		log.error("RecipeService exception occured -> NotFoundException: {}", e.getMessage());

		Error exceptionResponse = generateExceptionResponse(Constant.NOT_FOUND, e.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<Error> handleBadRequestException(BadRequestException e, HttpServletRequest request,
			HttpServletResponse response) {
		log.error("RecipeService exception occured -> BadRequestException: {}", e.getMessage());

		Error exceptionResponse = generateExceptionResponse(Constant.BAD_REQUEST_CODE, e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Error> handleArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest request, HttpServletResponse response) {
		log.error("RecipeService exception occured -> MethodArgumentNotValidException: {}", e.getMessage());

		Error exceptionResponse = generateExceptionResponse(Constant.BAD_REQUEST_CODE, e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler({ UnauthorizedException.class })
	public ResponseEntity<Error> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request,
			HttpServletResponse response) {
		log.error("RecipeService service exception occured -> UnauthorizedException: {}", e.getMessage());

		Error exceptionResponse = generateExceptionResponse(Constant.UNAUTHORIZED_CODE, e.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
	}

	@ExceptionHandler({ HttpClientErrorException.class })
	public ResponseEntity<Error> handleClientException(HttpClientErrorException e, HttpServletRequest request,
			HttpServletResponse response) {
		log.error("RecipeService exception occured -> HttpClientErrorException: {}", e.getMessage());

		Error exceptionResponse = generateExceptionResponse(Constant.CLIENT_ERROR_CODE, e.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler({ ApiException.class })
	public ResponseEntity<Error> handleApiException(ApiException e, HttpServletRequest request,
			HttpServletResponse response) {
		log.error("RecipeService exception occured -> ApiException: {}", e.getMessage());

		Error exceptionResponse = generateExceptionResponse(e.getCode(), e.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler({ RuntimeException.class, Exception.class })
	public ResponseEntity<Error> otherExceptions(Exception e, HttpServletRequest request,
			HttpServletResponse response) {
		log.error("RecipeService exception occured -> RuntimeException: {}",
				e.toString().concat(StringUtils.SPACE).concat(ExceptionUtils.getMessage(e)));

		Error exceptionResponse = generateExceptionResponse(Constant.INTERNAL_SERVER_ERROR_CODE,
				e.toString().concat(StringUtils.SPACE).concat(ExceptionUtils.getMessage(e)));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	private Error generateExceptionResponse(String code, String message) {
		Error response = new Error();
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

}
