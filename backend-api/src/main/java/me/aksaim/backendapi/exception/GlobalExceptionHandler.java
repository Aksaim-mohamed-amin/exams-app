package me.aksaim.backendapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
	// Handel Http Message Not Readable Exception
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorTemplate> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException ex, HttpServletRequest request
	) {
		ErrorTemplate errorTemplate = new ErrorTemplate(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				request.getRequestURI(),
				"Malformed JSON request",
				null
		);

		return ResponseEntity.badRequest().body(errorTemplate);
	}

	// Handle Method Argument Not Valid Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorTemplate> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex, HttpServletRequest request
	) {
		ErrorTemplate errorTemplate = new ErrorTemplate(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				request.getRequestURI(),
				"Validation failed",
				ex.getFieldErrors().stream()
						.collect(Collectors.toMap(
								FieldError::getField,
								err -> Optional.ofNullable(err.getDefaultMessage()).orElse("Invalid value"),
								(existing, replacement) -> existing + ", " + replacement
						))
		);

		return ResponseEntity.badRequest().body(errorTemplate);
	}

	// Handle Illegal Argument Exception
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorTemplate> handleIllegalArgumentException(
			IllegalArgumentException ex, HttpServletRequest request
	) {
		ErrorTemplate errorTemplate = new ErrorTemplate(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				request.getRequestURI(),
				"Illegal argument",
				Map.of("error", ex.getMessage())
		);
		return ResponseEntity.badRequest().body(errorTemplate);
	}

	// Fallback for all other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorTemplate> handleGenericException(
			Exception ex, HttpServletRequest request
	) {
		ErrorTemplate errorTemplate = new ErrorTemplate(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				request.getRequestURI(),
				"Unexpected error",
				Map.of("error", ex.getMessage())
		);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorTemplate);
	}
}