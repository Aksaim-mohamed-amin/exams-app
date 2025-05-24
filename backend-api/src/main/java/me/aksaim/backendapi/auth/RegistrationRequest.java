package me.aksaim.backendapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
		@NotBlank String firstName,
		@NotBlank String lastName,
		@NotBlank @Email String email,
		@NotBlank @Size(min = 8, message = "Password must be at least 8 characters long") String password) {
}