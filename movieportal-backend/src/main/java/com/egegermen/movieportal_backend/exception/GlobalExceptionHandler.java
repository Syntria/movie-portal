package com.egegermen.movieportal_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(EntityNotFoundException e) {
		return ResponseEntity.status(404).body(e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
		return ResponseEntity.status(403).body("Access denied: " + e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception e) {
		var stringWriter = new StringWriter();
		var printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return ResponseEntity.status(404)
				.body("<h1>Something went wrong!</h1>" + "<h2>Exception Message: " + e.getMessage()
						+ "</h2>" + "<h2>Stack Trace:</h2>" + "<pre>" + stringWriter + "</pre>");
	}

}
