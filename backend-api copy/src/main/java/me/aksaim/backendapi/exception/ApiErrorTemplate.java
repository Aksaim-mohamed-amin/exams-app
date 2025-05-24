package me.aksaim.backendapi.exception;

import java.util.Map;

public record ApiErrorTemplate(
		int code,
		String path,
		String method,
		String message,
		Map<String, String> details) {
}