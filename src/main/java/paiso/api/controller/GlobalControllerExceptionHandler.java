package paiso.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import paiso.exception.BadRequestDataException;
import paiso.exception.UserNotFoundException;
import paiso.model.ErrorResponse;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(BadRequestDataException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse invalidCurrency(BadRequestDataException exception) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse invalidUser(UserNotFoundException exception) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());
	}
	
}
