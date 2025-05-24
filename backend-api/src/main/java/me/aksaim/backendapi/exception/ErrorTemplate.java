package me.aksaim.backendapi.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorTemplate(
		LocalDateTime timestamp,
		int status,
		String error,
		String path,
		String message,
		Map<String, String> fieldErrors
) {
}