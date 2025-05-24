package me.aksaim.backendapi.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	// Handel bad credentials
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorTemplate> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
		ApiErrorTemplate errorTemplate = new ApiErrorTemplate(
				HttpStatus.UNAUTHORIZED.value(),
				request.getRequestURI(),
				request.getMethod(),
				"Bad credentials",
				Map.of("Error", ex.getMessage())
		);

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorTemplate);
	}

	// Handel Illegal Arguments
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorTemplate> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
		ApiErrorTemplate errorTemplate = new ApiErrorTemplate(
				HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI(),
				request.getMethod(),
				"Illegal Arguments",
				Map.of("Error", ex.getMessage())
		);
		return new ResponseEntity<>(errorTemplate, HttpStatus.BAD_REQUEST);
	}

	// Handel Not Valid Arguments
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorTemplate> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
		Map<String, String> messages = new HashMap<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			messages.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ApiErrorTemplate errorTemplate = new ApiErrorTemplate(
				HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI(),
				request.getMethod(),
				"Arguments not valid",
				messages
		);
		return new ResponseEntity<>(errorTemplate, HttpStatus.BAD_REQUEST);
	}

	// Handel unknown properties
	@ExceptionHandler(UnrecognizedPropertyException.class)
	public ResponseEntity<ApiErrorTemplate> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex, HttpServletRequest request) {
		ApiErrorTemplate errorTemplate = new ApiErrorTemplate(
				HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI(),
				request.getMethod(),
				"Unknown properties",
				Map.of("Unknown property", ex.getPropertyName())
		);
		return new ResponseEntity<>(errorTemplate, HttpStatus.BAD_REQUEST);
	}

	// Handle Not Found Exception
	@ExceptionHandler(ChangeSetPersister.NotFoundException.class)
	public ResponseEntity<ApiErrorTemplate> handleNotFoundException(ChangeSetPersister.NotFoundException ex, HttpServletRequest request) {
		ApiErrorTemplate errorTemplate = new ApiErrorTemplate(
				HttpStatus.NOT_FOUND.value(),
				request.getRequestURI(),
				request.getMethod(),
				"Requested resource not found",
				Map.of("Error", ex.getMessage())
		);
		return new ResponseEntity<>(errorTemplate, HttpStatus.NOT_FOUND);
	}

	// Handle BadRequestException
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorTemplate> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
		ApiErrorTemplate errorTemplate = new ApiErrorTemplate(
				HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI(),
				request.getMethod(),
				ex.getMessage(),
				null
		);
		return new ResponseEntity<>(errorTemplate, HttpStatus.BAD_REQUEST);
	}

	// Handle Generic Exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorTemplate> handleGenericException(Exception ex, HttpServletRequest request) {
		ApiErrorTemplate errorTemplate = new ApiErrorTemplate(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				request.getRequestURI(),
				"An unexpected error occurred",
				ex.getMessage(),
				null
		);
		return new ResponseEntity<>(errorTemplate, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}