package com.afkl.cases.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandling.class);


	@ExceptionHandler(ResultNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleResultNotFoundException(ResultNotFoundException ex,
			WebRequest request) {
		logger.error("request not found "+ex.getLocalizedMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Result Not Found", details),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());

		return new ResponseEntity<>(
				new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", details),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
