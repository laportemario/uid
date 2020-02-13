package com.document.uid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.document.uid.model.response.error.UidErrorResponse;

import java.time.LocalDateTime;


@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UidException.class)
	public ResponseEntity<Object> handleUidExceptionException(
			UidException ex, WebRequest request) {
		
		UidErrorResponse errors = new UidErrorResponse();
		
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.CONFLICT.value());
		errors.setDetails(request.getDescription(false));

		return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
	}
}